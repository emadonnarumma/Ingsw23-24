package com.ingsw.dietiDeals24.ui.utility;

import com.ingsw.dietiDeals24.enumeration.Region;

import java.util.HashMap;
import java.util.Map;

public class RegionStringConverter {
    private static final Map<String, Region> regionMap;


    static {
        regionMap = new HashMap<>();
        initializeMap();
    }


    private static  void initializeMap() {
        regionMap.put("Abruzzo", Region.ABRUZZO);
        regionMap.put("Basilicata", Region.BASILICATA);
        regionMap.put("Calabria", Region.CALABRIA);
        regionMap.put("Campania", Region.CAMPANIA);
        regionMap.put("Emilia Romagna", Region.EMILIA_ROMAGNA);
        regionMap.put("Friuli Venezia Giulia", Region.FRIULI_VENEZIA_GIULIA);
        regionMap.put("Lazio", Region.LAZIO);
        regionMap.put("Liguria", Region.LIGURIA);
        regionMap.put("Lombardia", Region.LOMBARDIA);
        regionMap.put("Marche", Region.MARCHE);
        regionMap.put("Molise", Region.MOLISE);
        regionMap.put("Piemonte", Region.PIEMONTE);
        regionMap.put("Puglia", Region.PUGLIA);
        regionMap.put("Sardegna", Region.SARDEGNA);
        regionMap.put("Sicilia", Region.SICILIA);
        regionMap.put("Toscana", Region.TOSCANA);
        regionMap.put("Trentino Alto Adige", Region.TRENTINO_ALTO_ADIGE);
        regionMap.put("Umbria", Region.UMBRIA);
        regionMap.put("Valle d'Aosta", Region.VALLE_D_AOSTA);
        regionMap.put("Veneto", Region.VENETO);
    }


    public static Region convert(String regionString) {
        return regionMap.get(regionString);
    }
}
