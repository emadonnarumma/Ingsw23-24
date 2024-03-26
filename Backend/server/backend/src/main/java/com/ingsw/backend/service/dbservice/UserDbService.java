package com.ingsw.backend.service.dbservice;

import java.util.Optional;

import com.ingsw.backend.enumeration.Role;
import com.ingsw.backend.model.Buyer;
import com.ingsw.backend.model.Seller;
import org.springframework.stereotype.Service;

import com.ingsw.backend.enumeration.Region;
import com.ingsw.backend.model.User;
import com.ingsw.backend.repository.UserRepository;
import com.ingsw.backend.service.UserService;

@Service("mainUserService")
public class UserDbService implements UserService {

    private final UserRepository userRepository;


    public UserDbService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> getUser(String email, String password) {
        return userRepository.findByEmailAndRoleAndPassword(email, Role.BUYER, password);
    }

    @Override
    public Optional<User> getUser(String email, Role role) {
        return userRepository.findByEmailAndRole(email, role);
    }

    @Override
    public Optional<Seller> getSeller(String email) {
        User user = userRepository.findByEmailAndRole(email, Role.SELLER).get();
        return Optional.of((Seller) user);
    }

    @Override
    public Optional<Buyer> getBuyer(String email) {
        User user = userRepository.findByEmailAndRole(email, Role.BUYER).get();
        return Optional.of((Buyer) user);
    }

    @Override
    public Boolean isEmailAlreadyUsed(String email) {

        return userRepository.existsByEmail(email);
    }

    @Override
    public Optional<User> updateRegion(String email, Role role, Region newRegion) {

        Optional<User> optionalUser = userRepository.findByEmailAndRole(email, role);

        if (optionalUser.isPresent()) {

            User user = optionalUser.get();

            user.setRegion(newRegion);

            userRepository.save(user);
        }

        return optionalUser;
    }

    @Override
    public Optional<User> updateBio(String email, Role role, String newBio) {

        Optional<User> optionalUser = userRepository.findByEmailAndRole(email, role);

        if (optionalUser.isPresent()) {

            User user = optionalUser.get();
            user.setBio(newBio);

            return Optional.of(userRepository.save(user));
        }

        return Optional.empty();
    }

    @Override
    public Boolean doesAccountExist(String email, Role role) {
        return userRepository.existsByEmailAndRole(email, role);
    }

}
