package com.ingsw.dietiDeals24.model.enumeration;

import java.io.Serializable;

public enum Category implements Serializable {
    NO_FILTERS,
    ARCHAEOLOGY_AND_NATURAL_HISTORY,
    ART,
    ASIAN_AND_TRIABAL_ART,
    VINTAGE_CARS_VINTAGE_MOTORCYCLES_AND_AUTOMOBILIA,
    MILITARY_MEMORABILIA_AND_HISTORICAL_DOCUMENTS,
    INTERIOR_DECORATIONS,
    TOYS_AND_MODELS,
    JEWELRY_AND_PRECIOUS_STONES,
    ENTERTAINMENT_CARDS_AND_VIDEOGAMES,
    BOOKS_AND_COMICS,
    FASHION,
    COINS_AND_STAMPS,
    MUSIC_AND_CAMERAS,
    WRISTWATCHES,
    SPORTS,
    WINES_AND_SPIRITS,
    SERVICES;

    public static Category fromItalianString(String category) {
        switch (category) {
            case "Nessun filtro":
                return NO_FILTERS;

            case "Archeologia e storia naturale":
                return ARCHAEOLOGY_AND_NATURAL_HISTORY;

            case "Arte":
                return ART;

            case "Arte asiatica e tribale":
                return ASIAN_AND_TRIABAL_ART;

            case "Auto d'epoca, moto d'epoca e automobilia":
                return VINTAGE_CARS_VINTAGE_MOTORCYCLES_AND_AUTOMOBILIA;

            case "Collezionismo militare e documenti storici":
                return MILITARY_MEMORABILIA_AND_HISTORICAL_DOCUMENTS;

            case "Decorazioni d'interni":
                return INTERIOR_DECORATIONS;

            case "Giochi e modellismo":
                return TOYS_AND_MODELS;

            case "Gioielli e pietre preziose":
                return JEWELRY_AND_PRECIOUS_STONES;

            case "Carte da collezione e videogiochi":
                return ENTERTAINMENT_CARDS_AND_VIDEOGAMES;

            case "Libri e fumetti":
                return BOOKS_AND_COMICS;

            case "Moda":
                return FASHION;

            case "Monete e francobolli":
                return COINS_AND_STAMPS;

            case "Musica e fotocamere":
                return MUSIC_AND_CAMERAS;

            case "Orologi da polso":
                return WRISTWATCHES;

            case "Sport":
                return SPORTS;

            case "Vini e liquori":
                return WINES_AND_SPIRITS;

            case "Servizi":
                return SERVICES;

            default:
                return null;
        }
    }

    public static String toItalianString(Category category) {
        switch (category) {
            case NO_FILTERS:
                return "Nessun filtro";

            case ARCHAEOLOGY_AND_NATURAL_HISTORY:
                return "Archeologia e storia naturale";

            case ART:
                return "Arte";

            case ASIAN_AND_TRIABAL_ART:
                return "Arte asiatica e tribale";

            case VINTAGE_CARS_VINTAGE_MOTORCYCLES_AND_AUTOMOBILIA:
                return "Auto d'epoca, moto d'epoca e automobilia";

            case MILITARY_MEMORABILIA_AND_HISTORICAL_DOCUMENTS:
                return "Collezionismo militare e documenti storici";

            case INTERIOR_DECORATIONS:
                return "Decorazioni d'interni";

            case TOYS_AND_MODELS:
                return "Giochi e modellismo";

            case JEWELRY_AND_PRECIOUS_STONES:
                return "Gioielli e pietre preziose";

            case ENTERTAINMENT_CARDS_AND_VIDEOGAMES:
                return "Carte da collezione e videogiochi";

            case BOOKS_AND_COMICS:
                return "Libri e fumetti";

            case FASHION:
                return "Moda";

            case COINS_AND_STAMPS:
                return "Monete e francobolli";

            case MUSIC_AND_CAMERAS:
                return "Musica e fotocamere";

            case WRISTWATCHES:
                return "Orologi da polso";

            case SPORTS:
                return "Sport";

            case WINES_AND_SPIRITS:
                return "Vini e liquori";

            case SERVICES:
                return "Servizi";

            default:
                return null;
        }
    }
}
