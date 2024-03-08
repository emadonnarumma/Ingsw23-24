package com.ingsw.dietiDeals24.controller;

import android.content.res.Resources;

import androidx.lifecycle.MutableLiveData;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.formstate.LoginFormState;
import com.ingsw.dietiDeals24.model.User;
import com.ingsw.dietiDeals24.model.enumeration.Role;
import com.ingsw.dietiDeals24.exceptions.AuthenticationException;
import com.ingsw.dietiDeals24.exceptions.ConnectionException;
import com.ingsw.dietiDeals24.network.RetroFitHolder;
import com.ingsw.dietiDeals24.network.dao.CurrentUserDao;
import com.ingsw.dietiDeals24.network.dao.LoginDao;
import com.ingsw.dietiDeals24.network.TokenHolder;
import com.ingsw.dietiDeals24.model.LogInRequest;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Response;

public class LogInController implements RetroFitHolder {
    private static MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();

    public static MutableLiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    public static void loginInputChanged(String email, String password, Resources resources) {
        if (!isEmailFormatValid(email)) {
            loginFormState.setValue(new LoginFormState(resources.getString(R.string.email_format_invalid), null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, resources.getString(R.string.password_format_invalid)));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    private LogInController() {
    }

    public static CompletableFuture<Boolean> login(String email, String password) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                LoginDao loginDao = retrofit.create(LoginDao.class);
                CurrentUserDao currentUserDao = retrofit.create(CurrentUserDao.class);

                LogInRequest request = new LogInRequest(email, password);
                Response<TokenHolder> response = loginDao.login(request).execute();

                if (response.isSuccessful() && response.body() != null) {
                    TokenHolder tokenHolder = TokenHolder.getInstance();
                    tokenHolder.setToken(response.body().getToken());
                    Role role = currentUserDao.getRole(email, TokenHolder.getAuthToken()).execute().body();
                    if (role == Role.BUYER) {
                        UserHolder.user = new User(currentUserDao.getBuyerByEmail(email, TokenHolder.getAuthToken()).execute().body());
                    } else if (role == Role.SELLER) {
                        UserHolder.user = new User(currentUserDao.getSellerByEmail(email, TokenHolder.getAuthToken()).execute().body());
                    }
                    return true;
                } else if (response.code() == 403) {
                    throw new AuthenticationException("Credenziali errate");
                }

            } catch (IOException e) {
                throw new ConnectionException("Errore di connessione");
            }
            return false;
        });
    }

    public static boolean isEmailFormatValid(String email) {
        if (email == null) {
            return false;
        }

        final String regular_expression = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regular_expression);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isPasswordValid(String password) {
        return password != null && password.length() > 5;
    }
}