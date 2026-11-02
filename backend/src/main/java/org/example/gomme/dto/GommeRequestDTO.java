package org.example.gomme.dto;

import org.example.gomme.model.*;

public record GommeRequestDTO(
        String targa,
        String cliente,
        String misura,
        Integer carico,
        Speed speed,
        Posizione posizione,
        Season season,
        Boolean vendita,
        String noleggio,
        Edificio edificio,
        Area area,
        Integer fila,
        Integer quantity
) {
}
