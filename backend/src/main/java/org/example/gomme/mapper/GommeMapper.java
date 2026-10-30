package org.example.gomme.mapper;

import org.example.gomme.dto.AddGommeDTO;
import org.example.gomme.model.Gomme;

public class GommeMapper {
    public static Gomme toEntity(AddGommeDTO gommeDTO) {
        Gomme gomme = new Gomme();
        gomme.setTarga(gommeDTO.getTarga());
        gomme.setCliente(gommeDTO.getCliente());
        gomme.setMisura(gommeDTO.getMisura());
        gomme.setCarico(gommeDTO.getCarico());
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
}
