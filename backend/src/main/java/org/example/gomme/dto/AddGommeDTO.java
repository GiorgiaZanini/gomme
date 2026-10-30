package org.example.gomme.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.gomme.model.Area;
import org.example.gomme.model.Edificio;
import org.example.gomme.model.Season;
import org.example.gomme.model.Speed;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public final class AddGommeDTO {

    @JsonProperty("targa")
    @NotBlank
    @Size(min = 7, max = 7)
    @Pattern(regexp = "^[A-Z]{2}\\d{3}[A-Z]{2}$")
    private String targa;

    @JsonProperty("cliente")
    @NotBlank
    private String cliente;

    @JsonProperty("misura")
    @NotBlank
    @Pattern(regexp = "^\\d+/\\d+R\\d+$")
    private String misura;

    @JsonProperty("carico")
    private Integer carico;

    @JsonProperty("speed")
    private Speed speed;

    @JsonProperty("season")
    @NotNull
    private Season season;

    @JsonProperty("vendita")
    private Boolean vendita;

    @JsonProperty("noleggio")
    private String noleggio;

    @JsonProperty("edificio")
    @NotNull
    private Edificio edificio;

    @JsonProperty("area")
    @NotNull
    private Area area;

    @JsonProperty("fila")
    @NotNull
    @PositiveOrZero
    private int fila;

    @JsonProperty("quantity")
    @NotNull
    @Positive
    private int quantity;
}
