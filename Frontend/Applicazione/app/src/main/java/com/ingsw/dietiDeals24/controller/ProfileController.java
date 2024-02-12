package com.ingsw.dietiDeals24.controller;

import com.ingsw.dietiDeals24.model.enumeration.Region;
import com.ingsw.dietiDeals24.model.User;

public class ProfileController {

    public static void addLink(String title, String url) {
        //TODO
    }

    public static void updateLink(String title, String url) {
        //TODO
    }

    public static void deleteLink(String title, String url) {
        //TODO
    }

    public static void updateBio(String bio) {
        //TODO
    }

    public static void updateRegion(Region region) {
        //TODO
    }

    public static void updateBankAccount(String iban, String iva) {
        //TODO
    }

    public static void logout() {
        UserHolder.user = null;
    }

    public static void switchAccountType() {
        //TODO
    }

    public static boolean isUrlFormatValid(String url) {
        if (url == null)
            return false;
        return url.matches("[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)");
    }

    public static boolean isIbanFormatValid(String iban) {
        if (iban == null)
            return false;
        return iban.matches("^[A-Z]{2}[0-9]{2}(?:[ ]?[0-9]{4}){4}(?:[ ]?[0-9]{1,2})?$");
    }

    public static boolean isIvaFormatValid(String iva) {
        if (iva == null)
            return false;
        return iva.matches("^[0-9]{11}$");
    }

    public static User getUser() {
        return UserHolder.user;
    }
}
