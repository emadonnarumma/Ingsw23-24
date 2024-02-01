package com.ingsw.dietiDeals24.enumeration;

import java.io.Serializable;

public enum Category implements Serializable {
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
        int c = 9;
        switch (category) {
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
}
