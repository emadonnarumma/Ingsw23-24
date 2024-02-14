package com.ingsw.dietiDeals24.controller.editProfile;

import android.content.res.Resources;

import androidx.lifecycle.MutableLiveData;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.UserHolder;
import com.ingsw.dietiDeals24.model.enumeration.Region;
import com.ingsw.dietiDeals24.model.User;

public class ProfileController {
    private static MutableLiveData<ExternalLinkFormState> externalLinkFormState = new MutableLiveData<>();

    public static MutableLiveData<ExternalLinkFormState> getExternalLinkFormState() {
        return externalLinkFormState;
    }

    public static void dataChanged(String title, String url, Resources resources) {
        if (!isUrlTitleValid(title)) {
            String titleError = resources.getString(R.string.url_title_format_invalid);
            externalLinkFormState.setValue(new ExternalLinkFormState(titleError, null));
        } else if (!isUrlFormatValid(url)) {
            String urlError = resources.getString(R.string.url_format_invalid);
            externalLinkFormState.setValue(new ExternalLinkFormState(null, urlError));
        } else {
            externalLinkFormState.setValue(new ExternalLinkFormState(true));
        }
    }

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

    public static boolean isUrlTitleValid(String title) {
        return title != null && title.trim().length() > 0;
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
