package org.example.gomme.mapper;

import org.example.gomme.dto.AddGommeDTO;
import org.example.gomme.model.Gomme;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GommeMapper {
    private static final Pattern TIRE = Pattern.compile(
            "\\s*(\\d+/\\d+)\\s*R\\s*(\\d+)\\s+(\\d+)\\s*(A[1-8]|[B-HJ-NP-WYZ])\\s*",
            Pattern.CASE_INSENSITIVE);

    public static Gomme toEntity(AddGommeDTO gommeDTO) {
        Gomme gomme = new Gomme();
        gomme.setTarga(gommeDTO.getTarga());
        gomme.setCliente(gommeDTO.getCliente());
        gomme.setMisura(gommeDTO.getMisura());
        gomme.setCarico(gommeDTO.getCarico());
        gomme.setPosizione(gommeDTO.getPosizione());
        gomme.setSpeed(gommeDTO.getSpeed());
        gomme.setSeason(gommeDTO.getSeason());
        gomme.setVendita(gommeDTO.getVendita());
        gomme.setNoleggio(gommeDTO.getNoleggio());
        gomme.setEdificio(gommeDTO.getEdificio());
        gomme.setArea(gommeDTO.getArea());
        gomme.setFila(gommeDTO.getFila());
        gomme.setQuantity(gommeDTO.getQuantity());
        return gomme;
    }

    public static String[] splitMisura(String misuraIntera) {
        if (misuraIntera == null) {
            return null;
        }

        Matcher m = TIRE.matcher(misuraIntera);
        if (!m.matches()) {
            return null;
        }

        String[] misura = new String[3];
        misura[0] = m.group(1) + " R" + m.group(2);     // "225/55 R17"
        misura[1] = m.group(3);                         // "97"
        misura[2] = m.group(4).toUpperCase();           // "W"
        return misura;
    }

    public static String joinMisura(String misura, String carico, String speed) {
        return misura.trim() + " " + carico.trim() + speed.trim();
    }
}
