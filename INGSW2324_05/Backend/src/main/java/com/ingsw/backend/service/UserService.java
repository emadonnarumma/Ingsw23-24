package com.ingsw.backend.service;

import java.util.Optional;

import com.ingsw.backend.enumeration.Region;
import com.ingsw.backend.enumeration.Role;
import com.ingsw.backend.model.Buyer;
import com.ingsw.backend.model.Seller;
import com.ingsw.backend.model.User;

public interface UserService {
    Optional<User> getUser(String email, Role role);
    Optional<Seller> getSeller(String email);
    Optional<Buyer> getBuyer(String email);

    Boolean isEmailAlreadyUsed(String email);

    Optional<User> updateRegion(String email, Role role, Region newRegion);

    Optional<User> updateBio(String email, Role role, String newBio);

    Boolean doesAccountExist(String email, Role role);
}
