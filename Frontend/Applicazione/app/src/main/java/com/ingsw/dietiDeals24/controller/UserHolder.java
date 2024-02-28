package com.ingsw.dietiDeals24.controller;

import com.ingsw.dietiDeals24.model.User;
import com.ingsw.dietiDeals24.model.Seller;
import com.ingsw.dietiDeals24.model.Buyer;

public class UserHolder {
    public static User user;

    public static Seller getSeller() {
        return (Seller) user;
    }

    public static Buyer getBuyer() {
        return (Buyer) user;
    }

    public static boolean isUserSeller() {
        return user instanceof Seller;
    }

    public static boolean isUserBuyer() {
        return user instanceof Buyer;
    }
}
