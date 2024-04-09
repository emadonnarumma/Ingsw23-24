package com.ingsw.dietiDeals24.controller;

import static com.ingsw.dietiDeals24.controller.UserHolder.user;

import android.content.res.Resources;

import androidx.lifecycle.MutableLiveData;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.formstate.BankAccountFormState;
import com.ingsw.dietiDeals24.controller.formstate.ExternalLinkFormState;
import com.ingsw.dietiDeals24.exceptions.AuthenticationException;
import com.ingsw.dietiDeals24.exceptions.BankAccountNotFoundException;
import com.ingsw.dietiDeals24.exceptions.ConnectionException;
import com.ingsw.dietiDeals24.exceptions.UrlLinkNotFoundException;
import com.ingsw.dietiDeals24.model.BankAccount;
import com.ingsw.dietiDeals24.model.Buyer;
import com.ingsw.dietiDeals24.model.ExternalLink;
import com.ingsw.dietiDeals24.model.RegistrationRequest;
import com.ingsw.dietiDeals24.model.Seller;
import com.ingsw.dietiDeals24.model.User;
import com.ingsw.dietiDeals24.model.enumeration.Region;
import com.ingsw.dietiDeals24.model.enumeration.Role;
import com.ingsw.dietiDeals24.network.RetroFitHolder;
import com.ingsw.dietiDeals24.network.TokenHolder;
import com.ingsw.dietiDeals24.network.dao.BankAccountDao;
import com.ingsw.dietiDeals24.network.dao.ExternalLinkDao;
import com.ingsw.dietiDeals24.network.dao.RegistrationDao;
import com.ingsw.dietiDeals24.network.dao.UserDao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import retrofit2.Response;

public class ProfileController implements RetroFitHolder {
    private static MutableLiveData<ExternalLinkFormState> externalLinkFormState = new MutableLiveData<>();
    private static MutableLiveData<BankAccountFormState> bankAccountFormState = new MutableLiveData<>();
    private static ExternalLink selectedLink;

    public static boolean hasSellerAccount = false;
    public static boolean wasUserAlreadyRetrieved = false;

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
        // IBAN ITALIANO: 2 lettere + 2 cifre + 1 lettera + 22 cifre con eventuali spazi
        return iban.matches("^IT\\s?[0-9]{2}\\s?[A-Z]\\s?(?:\\s?[0-9]){10}(?:\\s?[A-Z0-9]){12}$");
        // REGEX ITALIANA: ^[A-Z]{2}[0-9]{2}[A-Z][0-9]{22}$

        // IBAN GENERICO: 2 lettere + 13-30 cifre o lettere
        // REGEX GENERICA: ^[A-Z]{2}(?:[ ]?[0-9A-Z]){13,30}$
    }

    public static boolean isIvaFormatValid(String iva) {
        if (iva == null)
            return false;
        return iva.matches("^[0-9]{11}$");
    }

    public static CompletableFuture<Void> addLink(String title, String url) {
        return CompletableFuture.runAsync(() -> {
            ExternalLink newLink = new ExternalLink(title, url);
            try {
                ExternalLinkDao externalLinkDao = retrofit.create(ExternalLinkDao.class);
                Response<ExternalLink> response = externalLinkDao.addExternalLink(user.getEmail(), user.getRole(), newLink, TokenHolder.getAuthToken()).execute();

                if (response.isSuccessful()) {
                    ExternalLink linkFromServer = new ExternalLink(response.body());
                    user.getExternalLinks().add(linkFromServer);
                } else {
                    if (response.code()==403)
                        throw new UrlLinkNotFoundException();
                    throw new ConnectionException("Errore di connessione");
                }
            } catch (IOException e) {
                throw new ConnectionException("Errore di connessione");
            }
        });
    }

    public static CompletableFuture<Void> retrieveUser() {
        return CompletableFuture.runAsync(() -> {
            try {
                hasSellerAccount = hasSellerAccount().get();
                wasUserAlreadyRetrieved = true;
            } catch (ExecutionException e) {
                throw new ConnectionException("Errore di connessione");
            } catch (InterruptedException e) {
                throw new ConnectionException("Operazione interrotta, riprovare");
            }
        });
    }

    public static CompletableFuture<Void> updateLink(String title, String url) {
        return CompletableFuture.runAsync(() -> {
            ExternalLink updatedLink = new ExternalLink(title, url);
            try {
                ExternalLinkDao externalLinkDao = retrofit.create(ExternalLinkDao.class);
                Response<ExternalLink> response = externalLinkDao.updateExternalLink(selectedLink.getId(), updatedLink, TokenHolder.getAuthToken()).execute();

                if (response.isSuccessful()) {
                    ExternalLink linkFromServer = response.body();
                    user.getExternalLinks().get(user.getExternalLinks().indexOf(selectedLink)).setTitle(linkFromServer.getTitle());
                    user.getExternalLinks().get(user.getExternalLinks().indexOf(selectedLink)).setUrl(linkFromServer.getUrl());
                } else {
                    if (response.code()==403)
                        throw new UrlLinkNotFoundException();
                    throw new ConnectionException("Errore di connessione");
                }
            } catch (IOException e) {
                throw new ConnectionException("Errore di connessione");
            }
        });
    }

    public static CompletableFuture<Void> deleteLink(ExternalLink externalLink) {
        return CompletableFuture.runAsync(() -> {
            try {
                ExternalLinkDao externalLinkDao = retrofit.create(ExternalLinkDao.class);
                Response<Void> response = externalLinkDao.deleteExternalLink(externalLink.getId(), TokenHolder.getAuthToken()).execute();

                if (response.isSuccessful()) {
                    user.getExternalLinks().remove(externalLink);
                } else {
                    throw new ConnectionException("Errore di connessione");
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

    public static CompletableFuture<Void> updateBio(String bio) {
        return CompletableFuture.runAsync(() -> {
            try {
                UserDao userDao = retrofit.create(UserDao.class);
                Response<User> response = userDao.updateBio(user.getEmail(), user.getRole(), bio, TokenHolder.getAuthToken()).execute();

                if (response.isSuccessful()) {
                    if (response.body() == null)
                        throw new ConnectionException("Errore di connessione");
                    user.setBio(response.body().getBio());
                } else {
                    throw new ConnectionException("Errore di connessione");
                }
            } catch (IOException e) {
                throw new ConnectionException("Errore di connessione");
            }
        });
    }

    public static CompletableFuture<Void> updateRegion(Region region) {
        return CompletableFuture.runAsync(() -> {
            try {
                UserDao userDao = retrofit.create(UserDao.class);
                Response<User> response = userDao.updateRegion(user.getEmail(), user.getRole(), region, TokenHolder.getAuthToken()).execute();

                if (response.isSuccessful()) {
                    if (response.body() == null)
                        throw new ConnectionException("Errore di connessione");
                    user.setRegion(response.body().getRegion());
                } else {
                    throw new ConnectionException("Errore di connessione");
                }
            } catch (IOException e) {
                throw new ConnectionException("Errore di connessione");
            }
        });
    }

    public static CompletableFuture<Void> updateBankAccount(String iban, String iva) throws IllegalStateException {
        return CompletableFuture.runAsync(() -> {
            try {
                if (UserHolder.isUserBuyer())
                    throw new IllegalStateException("L'utente non è un venditore");

                String trimmedIban = iban.trim();
                BankAccount updatedBankAccount = new BankAccount(trimmedIban, iva);
                BankAccountDao bankAccountDao = retrofit.create(BankAccountDao.class);
                Response<BankAccount> response = bankAccountDao.updateBankAccount(
                        UserHolder.getSeller().getBankAccount().getId(), updatedBankAccount,
                        TokenHolder.getAuthToken()).execute();

                if (response.isSuccessful()) {
                    if (response.body() == null)
                        throw new ConnectionException("Errore di connessione");
                    UserHolder.getSeller().getBankAccount().setIban(response.body().getIban());
                    UserHolder.getSeller().getBankAccount().setIva(response.body().getIva());
                } else {
                    if (response.code() == 403)
                        throw new BankAccountNotFoundException();
                    throw new ConnectionException("Errore di connessione");
                }
            } catch (IOException e) {
                throw new ConnectionException("Errore di connessione");
            }
        });
    }

    public static CompletableFuture<Boolean> hasSellerAccount() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                UserDao userDao = retrofit.create(UserDao.class);
                Response<Boolean> response = userDao.doesAccountExist(user.getEmail(), Role.SELLER, TokenHolder.getAuthToken()).execute();

                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new ConnectionException("Errore di connessione");
                }
            } catch (IOException e) {
                throw new ConnectionException("Errore di connessione");
            }
        });
    }

    public static CompletableFuture<Void> addBankAccount(String iban, String iva) {
        return CompletableFuture.runAsync(() -> {
            try {
                if(UserHolder.isUserBuyer())
                    throw new IllegalStateException("L'utente deve essere un venditore per poter aggiungere un account bancario");

                String noSpacesIban = iban.replaceAll("\\s", "");
                BankAccount newBankAccount = new BankAccount(noSpacesIban, iva);
                BankAccountDao bankAccountDao = retrofit.create(BankAccountDao.class);
                Response<BankAccount> response = bankAccountDao.addBankAccount(user.getEmail(), newBankAccount, TokenHolder.getAuthToken()).execute();

                if (response.isSuccessful()) {
                    if (response.body() == null)
                        throw new ConnectionException("Errore di connessione");
                    UserHolder.getSeller().setBankAccount(response.body());
                } else {
                    if(response.code()==403)
                        throw new BankAccountNotFoundException();
                    throw new ConnectionException("Errore di connessione");
                }
            } catch (IOException e) {
                throw new ConnectionException("Errore di connessione");
            }
        });
    }

    public static CompletableFuture<Void> addSellerAccount() {
        RegistrationDao registrationDao = retrofit.create(RegistrationDao.class);
        UserDao currentUserDao = retrofit.create(UserDao.class);
        RegistrationRequest registrationRequest =  new RegistrationRequest(Role.SELLER, user.getName(), user.getEmail(), user.getPassword(), null, user.getRegion());
        return CompletableFuture.runAsync(() -> {
            try {
                Response<TokenHolder> response = registrationDao.register(registrationRequest).execute();

                if (response.isSuccessful() && response.body() != null) {

                    TokenHolder tokenHolder = TokenHolder.getInstance();
                    tokenHolder.setToken(response.body().getToken());
                    hasSellerAccount = true;

                    String email = user.getEmail();
                    user = currentUserDao.getSellerByEmail(email, TokenHolder.getAuthToken()).execute().body();

                } else if (response.code() == 403) {
                    throw new AuthenticationException("Account già esistente");
                }

            } catch (IOException e) {
                throw new ConnectionException("Errore di connessione");
            }
        });
    }

    public static CompletableFuture<Void> switchAccountType() {
        return CompletableFuture.runAsync(() -> {
            try {
                UserDao userDao = retrofit.create(UserDao.class);

                if(UserHolder.isUserBuyer()) {
                    Response<Seller> response = userDao.getSellerByEmail(user.getEmail(), TokenHolder.getAuthToken()).execute();
                    if (response.isSuccessful()) {
                        if (response.body() == null)
                            throw new ConnectionException("Errore di connessione");
                        user = response.body();
                    } else {
                        throw new ConnectionException("Errore di connessione");
                    }
                } else {
                    Response<Buyer> response = userDao.getBuyerByEmail(user.getEmail(), TokenHolder.getAuthToken()).execute();
                    if (response.isSuccessful()) {
                        if (response.body() == null)
                            throw new ConnectionException("Errore di connessione");
                        user = response.body();
                    } else {
                        throw new ConnectionException("Errore di connessione");
                    }
                }
            } catch (IOException e) {
                throw new ConnectionException("Errore di connessione");
            }
        });
    }

    public static void logout() {
        user = null;
        hasSellerAccount = false;
        wasUserAlreadyRetrieved = false;
        MyAuctionsController.setUpdatedAll(false);
        MyBidsController.setUpdatedAll(false);
        NotificationController.setNotifications(new ArrayList<>());
        SearchAuctionsController.setUpdatedAll(false);
        TokenHolder.getInstance().setToken("");
    }
}
