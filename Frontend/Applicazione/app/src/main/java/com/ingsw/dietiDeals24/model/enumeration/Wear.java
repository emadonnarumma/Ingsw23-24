package com.ingsw.dietiDeals24.model.enumeration;

public enum Wear {
    NEW,
    VERY_GOOD_CONDITION,
    GOOD_CONDITION,
    BAD_CONDITION,
    NOT_SPECIFIED;

    public static Wear fromItalianString(String wear) {
        switch (wear) {
            case "Nuovo":
                return NEW;

            case "Ottime condizioni":
                return VERY_GOOD_CONDITION;

            case "Buone condizioni":
                return GOOD_CONDITION;

            case "Pessime condizioni":
                return BAD_CONDITION;

            case "Non specificato":
                return NOT_SPECIFIED;

            default:
                return null;
        }
    }

    public static String toItalianString(Wear wear) {
        switch (wear) {
            case NEW:
                return "Nuovo";
            case VERY_GOOD_CONDITION:
                return "Ottime condizioni";
            case GOOD_CONDITION:
                return "Buone condizioni";
            case BAD_CONDITION:
                return "Pessime condizioni";
            case NOT_SPECIFIED:
                return "Non specificato";
            default:
                return null;
        }
    }
}
