package com.ingsw.dietiDeals24.model.enumeration;

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

    @Override
    public String toString() {
        switch (this) {
            case CAMPANIA:
                return "Campania";

            case LAZIO:
                return "Lazio";

            case LOMBARDIA:
                return "Lombardia";

            case VENETO:
                return "Veneto";

            case EMILIA_ROMAGNA:
                return "Emilia Romagna";

            case PIEMONTE:
                return "Piemonte";

            case PUGLIA:
                return "Puglia";

            case TOSCANA:
                return "Toscana";

            case CALABRIA:
                return "Calabria";

            case SARDEGNA:
                return "Sardegna";

            case SICILIA:
                return "Sicilia";

            case MARCHE:
                return "Marche";

            case ABRUZZO:
                return "Abruzzo";

            case FRIULI_VENEZIA_GIULIA:
                return "Friuli Venezia Giulia";

            case UMBRIA:
                return "Umbria";

            case BASILICATA:
                return "Basilicata";

            case MOLISE:
                return "Molise";

            case VALLE_D_AOSTA:
                return "Valle d'Aosta";

            case TRENTINO_ALTO_ADIGE:
                return "Trentino Alto Adige";

            case LIGURIA:
                return "Liguria";

            default:
                return null;
        }
    }

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
