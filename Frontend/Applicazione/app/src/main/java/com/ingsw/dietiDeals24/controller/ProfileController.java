package com.ingsw.dietiDeals24.controller;

import android.content.res.Resources;

import androidx.lifecycle.MutableLiveData;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.formstate.ExternalLinkFormState;
import com.ingsw.dietiDeals24.exceptions.ConnectionException;
import com.ingsw.dietiDeals24.model.BankAccount;
import com.ingsw.dietiDeals24.model.ExternalLink;
import com.ingsw.dietiDeals24.model.Seller;
import com.ingsw.dietiDeals24.model.enumeration.Region;
import com.ingsw.dietiDeals24.model.User;
import com.ingsw.dietiDeals24.network.RetroFitHolder;
import com.ingsw.dietiDeals24.network.TokenHolder;
import com.ingsw.dietiDeals24.network.dao.BankAccountDao;
import com.ingsw.dietiDeals24.network.dao.EditProfileDao;
import com.ingsw.dietiDeals24.network.dao.ExternalLinkDao;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import retrofit2.Response;

public class ProfileController {
    private static MutableLiveData<ExternalLinkFormState> externalLinkFormState = new MutableLiveData<>();
    private static ExternalLink selectedLink;

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

    public static CompletableFuture<Boolean> addLink(String title, String url) {
        ExternalLink newLink = new ExternalLink(title, url);
        return CompletableFuture.supplyAsync(() -> {
            try {
                ExternalLinkDao externalLinkDao = RetroFitHolder.retrofit.create(ExternalLinkDao.class);
                Response<ExternalLink> response = externalLinkDao.addExternalLink(UserHolder.user.getEmail(), newLink, TokenHolder.getAuthToken()).execute();

                if (response.isSuccessful()) {
                    UserHolder.user.getExternalLinks().add(response.body());
                    return true;
                } else {
                    return false;
                }
            } catch (IOException e) {
                throw new ConnectionException("Errore di connessione");
            }
        });
    }

    public static CompletableFuture<Boolean> updateLink(String title, String url) {
        ExternalLink updatedLink = new ExternalLink(title, url);
        return CompletableFuture.supplyAsync(() -> {
            try {
                ExternalLinkDao externalLinkDao = RetroFitHolder.retrofit.create(ExternalLinkDao.class);
                Response<ExternalLink> response = externalLinkDao.updateExternalLink(selectedLink.getId(), updatedLink, TokenHolder.getAuthToken()).execute();

                if (response.isSuccessful()) {
                    ExternalLink linkFromServer = response.body();
                    UserHolder.user.getExternalLinks().get(UserHolder.user.getExternalLinks().indexOf(selectedLink)).setTitle(linkFromServer.getTitle());
                    UserHolder.user.getExternalLinks().get(UserHolder.user.getExternalLinks().indexOf(selectedLink)).setUrl(linkFromServer.getUrl());
                    return true;
                } else {
                    return false;
                }
            } catch (IOException e) {
                throw new ConnectionException("Errore di connessione");
            }
        });
    }

    public static CompletableFuture<Boolean> deleteLink(ExternalLink externalLink) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                ExternalLinkDao externalLinkDao = RetroFitHolder.retrofit.create(ExternalLinkDao.class);
                Response<Void> response = externalLinkDao.deleteExternalLink(externalLink.getId(), TokenHolder.getAuthToken()).execute();

                if (response.isSuccessful()) {
                    UserHolder.user.getExternalLinks().remove(externalLink);
                    return true;
                } else {
                    return false;
                }
            } catch (IOException e) {
                throw new ConnectionException("Errore di connessione");
            }
        });
    }

    public static void setSelectedLink(ExternalLink selectedLink) {
        ProfileController.selectedLink = selectedLink;
    }

    public static ExternalLink getSelectedLink() {
        return selectedLink;
    }

    public static CompletableFuture<Boolean> updateBio(String bio) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                EditProfileDao editProfileDao = RetroFitHolder.retrofit.create(EditProfileDao.class);
                Response<User> response = editProfileDao.updateBio(UserHolder.user.getEmail(), bio, TokenHolder.getAuthToken()).execute();

                if (response.isSuccessful()) {
                    if(response.body()==null)
                        return false;
                    UserHolder.user.setBio(response.body().getBio());
                    return true;
                } else {
                    return false;
                }
            } catch (IOException e) {
                throw new ConnectionException("Errore di connessione");
            }
        });
    }

    public static CompletableFuture<Boolean> updateRegion(Region region) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                EditProfileDao editProfileDao = RetroFitHolder.retrofit.create(EditProfileDao.class);
                Response<User> response = editProfileDao.updateRegion(UserHolder.user.getEmail(), region, TokenHolder.getAuthToken()).execute();

                if (response.isSuccessful()) {
                    if(response.body()==null)
                        return false;
                    UserHolder.user.setRegion(response.body().getRegion());
                    return true;
                } else {
                    return false;
                }
            } catch (IOException e) {
                throw new ConnectionException("Errore di connessione");
            }
        });
    }

    public static CompletableFuture<Boolean> updateBankAccount(String iban, String iva) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                if(UserHolder.isUserBuyer())
                    return false;

                BankAccount updatedBankAccount = new BankAccount(iban, iva);
                BankAccountDao bankAccountDao = RetroFitHolder.retrofit.create(BankAccountDao.class);
                Response<BankAccount> response = bankAccountDao.updateBankAccount(
                        UserHolder.getSeller().getBankAccount().getId(), updatedBankAccount,
                        TokenHolder.getAuthToken()).execute();

                if (response.isSuccessful()) {
                    if(response.body()==null)
                        return false;
                    UserHolder.getSeller().getBankAccount().setIban(response.body().getIban());
                    UserHolder.getSeller().getBankAccount().setIva(response.body().getIva());
                    return true;
                } else {
                    return false;
                }
            } catch (IOException e) {
                throw new ConnectionException("Errore di connessione");
            }
        });
    }

    public static void logout() {
        UserHolder.user = null;
    }

    public static void switchAccountType(String iban, String iva) {

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
