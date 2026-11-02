export type Season = 'WINTER' | 'SUMMER' | 'ALL_SEASONS';

export type Edificio = 'MAGAZZINO' | 'OFFICINA';

export type Posizione = 'ANTERIORE' | 'POSTERIORE';

export type Speed =
  'A1' | 'A2' | 'A3' | 'A4' | 'A5' | 'A6' | 'A7' | 'A8' |
  'B' | 'C' | 'D' | 'E' | 'F' | 'G' | 'J' | 'K' | 'L' | 'M' | 'N' |
  'P' | 'Q' | 'R' | 'S' | 'T' | 'U' | 'H' | 'V' | 'W' | 'Y' | 'Z' | 'ZR';

export type Area =
  'UNO' | 'UNO_DIETRO' | 'UNO_DAVANTI' | 'DUE' |
  'TRE_DIETRO' | 'TRE_DAVANTI' | 'TRE_SOTTO' | 'TRE_SOPRA' |
  'QUATTRO_SOTTO' | 'QUATTRO_SOPRA' | 'CINQUE_SOTTO' | 'CINQUE_SOPRA' |
  'SEI_SOTTO' | 'SEI_SOPRA' | 'SETTE_SOTTO' | 'SETTE_SOPRA' |
  'OTTO_SOTTO' | 'OTTO_SOPRA' | 'CENTRO_SINISTRA' | 'CENTRO_DESTRA';

export const AREA_DESCRIZIONE: Record<Area, string> = {
  UNO: '1',
  UNO_DIETRO: '1 DIETRO',
  UNO_DAVANTI: '1 DAVANTI',
  DUE: '2',
  TRE_DIETRO: '3 DIETRO',
  TRE_DAVANTI: '3 DAVANTI',
  TRE_SOTTO: '3 SOTTO',
  TRE_SOPRA: '3 SOPRA',
  QUATTRO_SOTTO: '4 SOTTO',
  QUATTRO_SOPRA: '4 SOPRA',
  CINQUE_SOTTO: '5 SOTTO',
  CINQUE_SOPRA: '5 SOPRA',
  SEI_SOTTO: '6 SOTTO',
  SEI_SOPRA: '6 SOPRA',
  SETTE_SOTTO: '7 SOTTO',
  SETTE_SOPRA: '7 SOPRA',
  OTTO_SOTTO: '8 SOTTO',
  OTTO_SOPRA: '8 SOPRA',
  CENTRO_SINISTRA: 'CENTRO SINISTRA',
  CENTRO_DESTRA: 'CENTRO DESTRA'
};

export interface Gomma {
  id: number;
  targa: string;
  cliente: string;
  misura: string;
  carico: number | null;
  speed: Speed | null;
  posizione: Posizione | null;
  season: Season;
  vendita: boolean | null;
  noleggio: string | null;
  edificio: Edificio;
  area: Area;
  fila: number;
  quantity: number;
}
