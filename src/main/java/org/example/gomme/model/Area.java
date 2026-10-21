package org.example.gomme.model;

import lombok.Getter;

@Getter
public enum Area {
    UNO("1"),
    UNO_DIETRO("1 DIETRO"),
    UNO_DAVANTI("1 DAVANTI"),
    DUE("2"),
    TRE_DIETRO("3 DIETRO"),
    TRE_DAVANTI("3 DAVANTI"),
    TRE_SOTTO("3 SOTTO"),
    TRE_SOPRA("3 SOPRA"),
    QUATTRO_SOTTO("4 SOTTO"),
    QUATTRO_SOPRA("4 SOPRA"),
    CINQUE_SOTTO("5 SOTTO"),
    CINQUE_SOPRA("5 SOPRA"),
    SEI_SOTTO("6 SOTTO"),
    SEI_SOPRA("6 SOPRA"),
    SETTE_SOTTO("7 SOTTO"),
    SETTE_SOPRA("7 SOPRA"),
    OTTO_SOTTO("8 SOTTO"),
    OTTO_SOPRA("8 SOPRA"),
    CENTRO_SINISTRA("CENTRO SINISTRA"),
    CENTRO_DESTRA("CENTRO DESTRA");

    private String descrizione;

    Area(String s) {
       descrizione = s;
    }
}
