package org.example.gomme.model.DTO;

import org.example.gomme.model.Area;
import org.example.gomme.model.Edificio;
import org.example.gomme.model.Season;
import org.example.gomme.model.Speed;

public record GommeRequestDTO(
        String targa,
        String cliente,
        String misura,
        int carico,
        Speed speed,
        Season season,
        boolean vendita,
        String noleggio,
        Edificio edificio,
        Area area,
        int fila,
        int quantity
) {
}
