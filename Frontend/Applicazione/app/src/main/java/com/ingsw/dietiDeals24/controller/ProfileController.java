package com.ingsw.dietiDeals24.controller;

import android.content.res.Resources;

import androidx.lifecycle.MutableLiveData;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.formstate.BankAccountFormState;
import com.ingsw.dietiDeals24.controller.formstate.ExternalLinkFormState;
import com.ingsw.dietiDeals24.exceptions.ConnectionException;
import com.ingsw.dietiDeals24.model.BankAccount;
import com.ingsw.dietiDeals24.model.ExternalLink;
import com.ingsw.dietiDeals24.model.enumeration.Region;
import com.ingsw.dietiDeals24.model.User;
import com.ingsw.dietiDeals24.model.enumeration.Role;
import com.ingsw.dietiDeals24.network.RetroFitHolder;
import com.ingsw.dietiDeals24.network.TokenHolder;
import com.ingsw.dietiDeals24.network.dao.BankAccountDao;
import com.ingsw.dietiDeals24.network.dao.UserDao;
import com.ingsw.dietiDeals24.network.dao.ExternalLinkDao;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import retrofit2.Response;

public class ProfileController {
    private static MutableLiveData<ExternalLinkFormState> externalLinkFormState = new MutableLiveData<>();
    private static MutableLiveData<BankAccountFormState> bankAccountFormState = new MutableLiveData<>();
    private static ExternalLink selectedLink;

    public static MutableLiveData<ExternalLinkFormState> getExternalLinkFormState() {
        return externalLinkFormState;
    }

    public static MutableLiveData<BankAccountFormState> getBankAccountFormState() {
        return bankAccountFormState;
    }

    public static void externalLinkInputChanged(String title, String url, Resources resources) {
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

    public static void bankAccountInputChanged(String iban, String iva, Resources resources) {
        if (!isIbanFormatValid(iban)) {
            String ibanError = resources.getString(R.string.iban_format_invalid);
            bankAccountFormState.setValue(new BankAccountFormState(ibanError, null));
        } else if (!isIvaFormatValid(iva)) {
            String ivaError = resources.getString(R.string.iva_format_invalid);
            bankAccountFormState.setValue(new BankAccountFormState(null, ivaError));
        } else {
            bankAccountFormState.setValue(new BankAccountFormState(true));
        }
    }

    public static CompletableFuture<Void> addLink(String title, String url) throws ConnectionException {
        return CompletableFuture.runAsync(() -> {
            ExternalLink newLink = new ExternalLink(title, url);
            try {
                ExternalLinkDao externalLinkDao = RetroFitHolder.retrofit.create(ExternalLinkDao.class);
                Response<ExternalLink> response = externalLinkDao.addExternalLink(UserHolder.user.getEmail(), newLink, TokenHolder.getAuthToken()).execute();

                if (response.isSuccessful()) {
                    ExternalLink linkFromServer = new ExternalLink(response.body());
                    UserHolder.user.getExternalLinks().add(linkFromServer);
                } else {
                    throw new ConnectionException(response.message());
                }
            } catch (IOException e) {
                throw new ConnectionException("Errore di connessione");
            }
        });
    }


    public static CompletableFuture<Void> updateLink(String title, String url) throws ConnectionException {
        return CompletableFuture.runAsync(() -> {
            ExternalLink updatedLink = new ExternalLink(title, url);
            try {
                ExternalLinkDao externalLinkDao = RetroFitHolder.retrofit.create(ExternalLinkDao.class);
                Response<ExternalLink> response = externalLinkDao.updateExternalLink(selectedLink.getId(), updatedLink, TokenHolder.getAuthToken()).execute();

                if (response.isSuccessful()) {
                    ExternalLink linkFromServer = response.body();
                    UserHolder.user.getExternalLinks().get(UserHolder.user.getExternalLinks().indexOf(selectedLink)).setTitle(linkFromServer.getTitle());
                    UserHolder.user.getExternalLinks().get(UserHolder.user.getExternalLinks().indexOf(selectedLink)).setUrl(linkFromServer.getUrl());
                } else {
                    throw new ConnectionException(response.message());
                }
            } catch (IOException e) {
                throw new ConnectionException("Errore di connessione");
            }
        });
    }

    public static CompletableFuture<Void> deleteLink(ExternalLink externalLink) throws ConnectionException {
        return CompletableFuture.runAsync(() -> {
            try {
                ExternalLinkDao externalLinkDao = RetroFitHolder.retrofit.create(ExternalLinkDao.class);
                Response<Void> response = externalLinkDao.deleteExternalLink(externalLink.getId(), TokenHolder.getAuthToken()).execute();

                if (response.isSuccessful()) {
                    UserHolder.user.getExternalLinks().remove(externalLink);
                } else {
                    throw new ConnectionException(response.message());
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

    public static CompletableFuture<Void> updateBio(String bio) throws ConnectionException {
        return CompletableFuture.runAsync(() -> {
            try {
                UserDao userDao = RetroFitHolder.retrofit.create(UserDao.class);
                Response<User> response = userDao.updateBio(UserHolder.user.getEmail(), bio, TokenHolder.getAuthToken()).execute();

                if (response.isSuccessful()) {
                    if (response.body() == null)
                        throw new ConnectionException("Errore di connessione");
                    UserHolder.user = response.body();
                } else {
                    throw new ConnectionException(response.message());
                }
            } catch (IOException e) {
                throw new ConnectionException("Errore di connessione");
            }
        });
    }

    public static CompletableFuture<Void> updateRegion(Region region) throws ConnectionException {
        return CompletableFuture.runAsync(() -> {
            try {
                UserDao userDao = RetroFitHolder.retrofit.create(UserDao.class);
                Response<User> response = userDao.updateRegion(UserHolder.user.getEmail(), region, TokenHolder.getAuthToken()).execute();

                if (response.isSuccessful()) {
                    if (response.body() == null)
                        throw new ConnectionException("Errore di connessione");
                    UserHolder.user.setRegion(response.body().getRegion());
                } else {
                    throw new ConnectionException(response.message());
                }
            } catch (IOException e) {
                throw new ConnectionException("Errore di connessione");
            }
        });
    }

    public static CompletableFuture<Void> updateBankAccount(String iban, String iva) throws ConnectionException, IllegalStateException {
        return CompletableFuture.runAsync(() -> {
            try {
                if (UserHolder.isUserBuyer())
                    throw new IllegalStateException("L'utente non è un venditore");

                BankAccount updatedBankAccount = new BankAccount(iban, iva);
                BankAccountDao bankAccountDao = RetroFitHolder.retrofit.create(BankAccountDao.class);
                Response<BankAccount> response = bankAccountDao.updateBankAccount(
                        UserHolder.getSeller().getBankAccount().getId(), updatedBankAccount,
                        TokenHolder.getAuthToken()).execute();

                if (response.isSuccessful()) {
                    if (response.body() == null)
                        throw new ConnectionException("Errore di connessione");
                    UserHolder.getSeller().getBankAccount().setIban(response.body().getIban());
                    UserHolder.getSeller().getBankAccount().setIva(response.body().getIva());
                } else {
                    throw new ConnectionException(response.message());
                }
            } catch (IOException e) {
                throw new ConnectionException("Errore di connessione");
            }
        });
    }

    public static CompletableFuture<Boolean> hasBankAccount() throws ConnectionException {
        return CompletableFuture.supplyAsync(() -> {
            try {
                UserDao userDao = RetroFitHolder.retrofit.create(UserDao.class);
                Response<Boolean> response = userDao.hasBankAccount(UserHolder.user.getEmail(), TokenHolder.getAuthToken()).execute();

                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new ConnectionException(response.message());
                }
            } catch (IOException e) {
                throw new ConnectionException("Errore di connessione");
            }
        });
    }

    public static CompletableFuture<Void> addBankAccount(String iban, String iva) throws ConnectionException, ExecutionException, InterruptedException {
        return CompletableFuture.runAsync(() -> {
            try {
                if(UserHolder.isUserBuyer())
                    throw new IllegalStateException("L'utente deve essere un venditore per poter aggiungere un account bancario");

                BankAccount newBankAccount = new BankAccount(iban, iva);
                BankAccountDao bankAccountDao = RetroFitHolder.retrofit.create(BankAccountDao.class);
                Response<BankAccount> response = bankAccountDao.addBankAccount(UserHolder.user.getEmail(), newBankAccount, TokenHolder.getAuthToken()).execute();

                if (response.isSuccessful()) {
                    if (response.body() == null)
                        throw new ConnectionException("Errore di connessione");
                    UserHolder.getSeller().setBankAccount(response.body());
                } else {
                    throw new ConnectionException(response.errorBody().toString());
                }
            } catch (IOException e) {
                throw new ConnectionException("Errore di connessione");
            }
        });
    }

    public static CompletableFuture<Void> switchAccountType() throws ConnectionException {
        return CompletableFuture.runAsync(() -> {
            try {
                UserDao userDao = RetroFitHolder.retrofit.create(UserDao.class);
                Response<User> response;
                if (UserHolder.isUserBuyer())
                    response = userDao.updateRole(UserHolder.user.getEmail(), Role.SELLER, TokenHolder.getAuthToken()).execute();
                else
                    response = userDao.updateRole(UserHolder.user.getEmail(), Role.BUYER, TokenHolder.getAuthToken()).execute();

                if (response.isSuccessful()) {
                    if (response.body() == null)
                        throw new ConnectionException("Errore di connessione");
                    UserHolder.user = response.body();
                } else {
                    throw new ConnectionException(response.message());
                }
            } catch (IOException e) {
                throw new ConnectionException("Errore di connessione");
            }
        });
    }

    public static void logout() {
        UserHolder.user = null;
        //TokenHolder.getInstance().setToken(null);
    }

    public static boolean isUrlTitleValid(String title) {
        return title != null && title.trim().length() > 0;
    }

    public static boolean isUrlFormatValid(String url) {
        if (url == null)
            return false;
        return url.matches("(https?://www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)");
    }

    public static boolean isIbanFormatValid(String iban) {
        if (iban == null)
            return false;
        // IBAN ITALIANO: 2 lettere + 2 cifre + 1 lettera + 22 cifre
        // REGEX ITALIANA: ^[A-Z]{2}[0-9]{2}[A-Z][0-9]{22}$

        // IBAN GENERICO: 2 lettere + 13-30 cifre o lettere
        return iban.matches("^[A-Z]{2}(?:[ ]?[0-9A-Z]){13,30}$");
    }

    public static boolean isIvaFormatValid(String iva) {
        if (iva == null)
            return false;
        return iva.matches("^[0-9]{11}$");
    }
}
