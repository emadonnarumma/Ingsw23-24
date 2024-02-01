package com.ingsw.dietiDeals24.enumeration;

public enum Region {
    CAMPANIA,
    LAZIO,
    LOMBARDIA,
    VENETO,
    EMILIA_ROMAGNA,
    PIEMONTE,
    PUGLIA,
    TOSCANA,
    CALABRIA,
    SARDEGNA,
    SICILIA,
    MARCHE,
    ABRUZZO,
    FRIULI_VENEZIA_GIULIA,
    UMBRIA,
    BASILICATA,
    MOLISE,
    VALLE_D_AOSTA,
    TRENTINO_ALTO_ADIGE,
    LIGURIA;

    public static Region fromItalianString(String region) {
        switch (region) {
            case "Campania":
                return CAMPANIA;

            case "Lazio":
                return LAZIO;

            case "Lombardia":
                return LOMBARDIA;

            case "Veneto":
                return VENETO;

            case "Emilia Romagna":
                return EMILIA_ROMAGNA;

            case "Piemonte":
                return PIEMONTE;

            case "Puglia":
                return PUGLIA;

            case "Toscana":
                return TOSCANA;

            case "Calabria":
                return CALABRIA;

            case "Sardegna":
                return SARDEGNA;

            case "Sicilia":
                return SICILIA;

            case "Marche":
                return MARCHE;

            case "Abruzzo":
                return ABRUZZO;

            case "Friuli Venezia Giulia":
                return FRIULI_VENEZIA_GIULIA;

            case "Umbria":
                return UMBRIA;

            case "Basilicata":
                return BASILICATA;

            case "Molise":
                return MOLISE;

            case "Valle d'Aosta":
                return VALLE_D_AOSTA;

            case "Trentino Alto Adige":
                return TRENTINO_ALTO_ADIGE;

            case "Liguria":
                return LIGURIA;

            default:
                return null;
        }
    }
}
