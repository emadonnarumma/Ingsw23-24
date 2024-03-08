package com.ingsw.dietiDeals24.controller;

import android.content.res.Resources;

import androidx.lifecycle.MutableLiveData;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.formstate.RegistrationFormState;
import com.ingsw.dietiDeals24.model.enumeration.Role;
import com.ingsw.dietiDeals24.exceptions.AuthenticationException;
import com.ingsw.dietiDeals24.exceptions.ConnectionException;
import com.ingsw.dietiDeals24.network.RetroFitHolder;
import com.ingsw.dietiDeals24.network.TokenHolder;
import com.ingsw.dietiDeals24.model.User;
import com.ingsw.dietiDeals24.network.dao.CurrentUserDao;
import com.ingsw.dietiDeals24.network.dao.RegistrationDao;
import com.ingsw.dietiDeals24.model.RegistrationRequest;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Response;

public class RegistrationController implements RetroFitHolder {
    private static MutableLiveData<RegistrationFormState> registrationFormState = new MutableLiveData<>();
    public static User user = new User();

    private RegistrationController() {}

    public static MutableLiveData<RegistrationFormState> getRegistrationFormState() {
        return registrationFormState;
    }

    public static void registrationInputChanged(String username, String email, String password, String repeatPassword, Resources resources) {
        if (!isUsernameFormatValid(username)) {
            registrationFormState.setValue(new RegistrationFormState(resources.getString(R.string.username_format_invalid), null, null, null));
        } else if (!isEmailFormatValid(email)) {
            registrationFormState.setValue(new RegistrationFormState(null, resources.getString(R.string.email_format_invalid), null, null));
        } else if (!isPasswordValid(password)) {
            registrationFormState.setValue(new RegistrationFormState(null, null, resources.getString(R.string.password_format_invalid), null));
        } else if (!isRepeatPasswordValid(password, repeatPassword)) {
            registrationFormState.setValue(new RegistrationFormState(null, null, null, resources.getString(R.string.passwords_mismatch)));
        } else {
            registrationFormState.setValue(new RegistrationFormState(true));
        }
    }

    public static CompletableFuture<Boolean> register() {
        RegistrationDao registrationDao = retrofit.create(RegistrationDao.class);
        CurrentUserDao currentUserDao = retrofit.create(CurrentUserDao.class);
        RegistrationRequest registrationRequest =  new RegistrationRequest(user.getName(), user.getEmail(), user.getPassword(), user.getBio(), user.getRegion());
        return CompletableFuture.supplyAsync(() -> {
            try {
                Response<TokenHolder> response = registrationDao.register(registrationRequest).execute();

                if (response.isSuccessful() && response.body() != null) {

                    TokenHolder tokenHolder = TokenHolder.getInstance();


                    tokenHolder.setToken(response.body().getToken());

                    String email = user.getEmail();
                    Role role = currentUserDao.getRole(email, TokenHolder.getAuthToken()).execute().body();
                    if (role == Role.BUYER) {
                        UserHolder.user = currentUserDao.getBuyerByEmail(email, TokenHolder.getAuthToken()).execute().body();
                    } else if (role == Role.SELLER) {
                        UserHolder.user = currentUserDao.getSellerByEmail(email, TokenHolder.getAuthToken()).execute().body();
                    }

                    return true;
                } else if (response.code() == 403) {
                    throw new AuthenticationException("L'email è già in uso");
                }

            } catch (IOException e) {
                throw new ConnectionException("Errore di connessione");
            }
            return false;
        });
    }

    public static CompletableFuture<Boolean> emailAlreadyExists(String email) {
        RegistrationDao registrationDao = retrofit.create(RegistrationDao.class);
        return CompletableFuture.supplyAsync(() -> {
            try {
                Response<Boolean> response = registrationDao.emailAlreadyExists(email).execute();

                if (response.isSuccessful()) {
                    return response.body();

                } else {
                    throw new RuntimeException("Errore inaspettato");
                }

            } catch (IOException e) {
                throw new ConnectionException("Errore di connessione");
            }
        });
    }

    private static boolean isUsernameFormatValid(String username) {
        if (username == null)
            return false;

        final String regular_expression = "^[A-Za-z][\\w!#$%&'*+/=?`{|}~^-]{0,29}$";
        Pattern pattern = Pattern.compile(regular_expression);
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }

    private static boolean isEmailFormatValid(String email) {
        if (email == null || email.length()>320) {
            return false;
        }

        final String regular_expression = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regular_expression);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private static boolean isPasswordValid(String password) {
        return password != null && password.length() > 5;
    }

    private static boolean isRepeatPasswordValid(String password, String repeat_password) {
        return repeat_password.equals(password);
    }
}