"""
Importa l'archivio gomme dal vecchio Excel (foglio "Lista") nel backend,
mandando una POST /api/gomme per ogni riga.

Uso:
    python importa_excel.py database_v04_OLD.xls --prova   # non manda niente, mostra cosa farebbe
    python importa_excel.py database_v04_OLD.xls           # manda le gomme al backend

Dipendenze:  pip install xlrd requests

Comando da terminale da lanciare nella cartella stessa:
    sudo apt install python3.12-venv    // da lanciare solo la prima volta
    rm -rf .venv                        // da lanciare solo la prima volta
    python3 -m venv .venv               // da lanciare solo la prima volta
    source .venv/bin/activate
    python importa_excel.py /percorso/database_v04_OLD.xls --prova      // test se funziona
    python importa_excel.py /percorso/database_v04_OLD.xls
"""

import argparse
import json
import re
import sys

import xlrd

URL_DEFAULT = "http://localhost:8080/api/gomme"

# Colonna Z dell'Excel (numero del foglio) -> (edificio, area)
ZONE = {
    1: ("MAGAZZINO", "UNO_DIETRO"),
    2: ("MAGAZZINO", "DUE"),
    3: ("MAGAZZINO", "TRE_DIETRO"),
    4: ("MAGAZZINO", "CENTRO_DESTRA"),
    5: ("MAGAZZINO", "CENTRO_SINISTRA"),
    6: ("MAGAZZINO", "UNO_DAVANTI"),
    7: ("MAGAZZINO", "TRE_DAVANTI"),
    8: ("OFFICINA", "UNO"),
    9: ("OFFICINA", "DUE"),
    10: ("OFFICINA", "TRE_SOTTO"),
    11: ("OFFICINA", "TRE_SOPRA"),
    12: ("OFFICINA", "QUATTRO_SOTTO"),
    13: ("OFFICINA", "QUATTRO_SOPRA"),
    14: ("OFFICINA", "CINQUE_SOTTO"),
    15: ("OFFICINA", "CINQUE_SOPRA"),
    16: ("OFFICINA", "SEI_SOTTO"),
    17: ("OFFICINA", "SEI_SOPRA"),
    18: ("OFFICINA", "SETTE_SOTTO"),
    19: ("OFFICINA", "SETTE_SOPRA"),
    20: ("OFFICINA", "OTTO_SOTTO"),
    21: ("OFFICINA", "OTTO_SOPRA"),
}

# Colonna Tipo -> stagione (vuoto o altro -> estiva)
STAGIONI = {"I": "WINTER", "E": "SUMMER"}
STAGIONE_DEFAULT = "SUMMER"

# Società di noleggio da staccare dal nome del cliente
NOLEGGI = ["LEASYS", "ARVAL", "ALD", "UNIPOLRENTAL", "LEASEPLAN", "AYVENS", "DRIVE"]

# Velocità ammesse dall'enum Speed del backend
VELOCITA = {
    "A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "B", "C", "D", "E", "F", "G",
    "J", "K", "L", "M", "N", "P", "Q", "R", "S", "T", "U", "H", "V", "W", "Y", "Z", "ZR",
}

RE_TARGA = re.compile(r"^[A-Z]{2}\d{3}[A-Z]{2}$")
# "205/55 R16", "215/75 R16C", "185/65 R15 88H", "225/45R17"
RE_MISURA = re.compile(r"^(\d+)/(\d+)\s*R\s*(\d+)\s*(\d{2,3})?\s*([A-Z]{1,2})?$")
# "Ant 225/45 R18 ; Post 245/40 R18", "ANT. 255/45 R20 - POST. 285/40 R20"
RE_ANT_POST = re.compile(r"^ANT\.?\s*(\d+/\d+\s*R\s*\d+)\s*[;-]\s*POST\.?\s*(\d+/\d+\s*R\s*\d+)$")


class RigaScartata(Exception):
    pass


def testo(valore):
    """Cella -> stringa pulita (vuota se la cella è vuota)."""
    if valore is None:
        return ""
    if isinstance(valore, float) and valore.is_integer():
        valore = int(valore)
    return str(valore).strip()


def leggi_targa(valore):
    targa = testo(valore).upper().replace(" ", "")
    if not RE_TARGA.match(targa):
        raise RigaScartata(f"targa non valida: {testo(valore)!r}")
    return targa


def leggi_misura(valore):
    """Restituisce (misura, misura_posteriore, carico, velocita); gli ultimi tre possono essere None."""
    grezza = testo(valore).upper()

    ant_post = RE_ANT_POST.match(grezza)
    if ant_post:
        anteriore, posteriore = (formatta_misura(g) for g in ant_post.groups())
        return anteriore, posteriore, None, None

    m = RE_MISURA.match(grezza)
    if not m:
        raise RigaScartata(f"misura non riconosciuta: {testo(valore)!r}")
    larghezza, spalla, cerchio, carico, velocita = m.groups()
    if velocita is not None and velocita not in VELOCITA:
        raise RigaScartata(f"codice velocità sconosciuto: {velocita!r}")
    misura = f"{larghezza}/{spalla} R{cerchio}"
    return misura, None, (int(carico) if carico else None), velocita


def formatta_misura(grezza):
    """'225/45R18' o '225/45 R18' -> '225/45 R18' (il formato che accetta il backend)."""
    larghezza, spalla, cerchio = re.match(r"(\d+)/(\d+)\s*R\s*(\d+)", grezza).groups()
    return f"{larghezza}/{spalla} R{cerchio}"


def separa_noleggio(nome):
    """'POSTE ITALIANE - LEASYS' -> ('POSTE ITALIANE', 'LEASYS')."""
    nome = " ".join(nome.split())
    if not nome:
        raise RigaScartata("nome cliente mancante")
    for societa in NOLEGGI:
        if re.search(rf"\b{societa}\b", nome):
            resto = re.sub(rf"\b{societa}\b", "", nome)
            resto = re.sub(r"\s*-\s*\)", ")", resto)      # "(ZANINI ANT.- )" -> "(ZANINI ANT.)"
            resto = re.sub(r"\(\s*-\s*", "(", resto)
            resto = re.sub(r"\s*-\s*-\s*", " - ", resto)  # trattini rimasti doppi
            resto = " ".join(resto.split()).strip(" -")
            return (resto or societa), societa
    return nome, None


def leggi_righe(percorso):
    foglio = xlrd.open_workbook(percorso).sheet_by_name("Lista")
    intestazioni = [testo(c) for c in foglio.row_values(0)]
    col = {nome: i for i, nome in enumerate(intestazioni) if nome}
    for r in range(1, foglio.nrows):
        valori = foglio.row_values(r)
        if not testo(valori[col["Targa"]]):
            continue  # riga vuota
        yield r + 1, {nome: valori[i] for nome, i in col.items()}  # r + 1 = numero riga in Excel


def converti(riga):
    """Riga dell'Excel -> lista di JSON per AddGommeDTO (due se le misure ant/post sono diverse)."""
    targa = leggi_targa(riga["Targa"])
    cliente, noleggio = separa_noleggio(testo(riga["Nome"]))
    misura, misura_posteriore, carico, velocita = leggi_misura(riga["Misura"])
    quantita = int(riga["Quantità"])

    zona = int(riga["Z"])
    if zona not in ZONE:
        raise RigaScartata(f"zona Z sconosciuta: {zona}")
    edificio, area = ZONE[zona]

    gomma = {
        "targa": targa,
        "cliente": cliente,
        "misura": misura,
        "season": STAGIONI.get(testo(riga["Tipo"]).upper(), STAGIONE_DEFAULT),
        "edificio": edificio,
        "area": area,
        "fila": int(riga["C"]),
        "quantity": quantita,
    }
    if carico is not None:
        gomma["carico"] = carico
    if velocita is not None:
        gomma["speed"] = velocita
    if noleggio is not None:
        gomma["noleggio"] = noleggio

    if misura_posteriore is None:
        return [gomma]

    # Misure diverse: due righe, la quantità divisa a metà tra anteriore e posteriore
    if quantita % 2 != 0:
        raise RigaScartata(f"quantità dispari ({quantita}), non so dividerla tra ant e post")
    anteriore = {**gomma, "posizione": "ANTERIORE", "quantity": quantita // 2}
    posteriore = {**gomma, "posizione": "POSTERIORE", "misura": misura_posteriore, "quantity": quantita // 2}
    return [anteriore, posteriore]


def main():
    parser = argparse.ArgumentParser(description="Importa le gomme dall'Excel nel backend.")
    parser.add_argument("excel", help="file .xls con il foglio 'Lista'")
    parser.add_argument("--url", default=URL_DEFAULT, help=f"indirizzo dell'API (default {URL_DEFAULT})")
    parser.add_argument("--prova", action="store_true", help="non manda niente: salva anteprima.json e mostra il riepilogo")
    args = parser.parse_args()

    da_mandare, scartate, righe_lette = [], [], 0
    for numero, riga in leggi_righe(args.excel):
        righe_lette += 1
        try:
            for gomma in converti(riga):
                da_mandare.append((numero, gomma))
        except RigaScartata as motivo:
            scartate.append((numero, testo(riga.get("Targa")), str(motivo)))

    print(f"Righe lette dall'Excel: {righe_lette}")
    print(f"Righe scartate: {len(scartate)}")
    print(f"Gomme da importare: {len(da_mandare)} (le misure ant/post diventano due gomme)")
    for numero, targa, motivo in scartate:
        print(f"  riga {numero:>3}  {targa:<16} {motivo}")

    if args.prova:
        with open("anteprima.json", "w", encoding="utf-8") as f:
            json.dump([g for _, g in da_mandare], f, ensure_ascii=False, indent=2)
        print("\nModalità prova: niente è stato mandato. Controlla anteprima.json.")
        return

    import requests

    create, rifiutate = 0, []
    for numero, gomma in da_mandare:
        try:
            risposta = requests.post(args.url, json=gomma, timeout=10)
        except requests.ConnectionError:
            sys.exit(f"\nNon riesco a collegarmi a {args.url}: il backend è acceso?")
        if risposta.status_code == 201:
            create += 1
        else:
            rifiutate.append((numero, gomma["targa"], risposta.status_code, risposta.text[:200]))

    print(f"\nCreate: {create}")
    print(f"Rifiutate dal backend: {len(rifiutate)}")
    for numero, targa, stato, corpo in rifiutate:
        print(f"  riga {numero:>3}  {targa:<8} {stato}  {corpo}")


if __name__ == "__main__":
    main()
