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
        return userRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUser(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<Seller> getSeller(String email) {
        User user = userRepository.findByEmail(email).get();
        if (user.getRole() == Role.SELLER) {
            return Optional.of((Seller) user);
        }

        return Optional.empty();
    }

    @Override
    public Optional<Buyer> getBuyer(String email) {
        User user = userRepository.findByEmail(email).get();
        if (user.getRole() == Role.BUYER) {
            return Optional.of((Buyer) user);
        }

        return Optional.empty();
    }

    @Override
    public Boolean isEmailAlreadyUsed(String email) {

        return userRepository.existsByEmail(email);
    }

    @Override
    public Optional<User> updateRegion(String email, Region newRegion) {

        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent()) {

            User user = optionalUser.get();

            user.setRegion(newRegion);

            userRepository.save(user);
        }

        return optionalUser;
    }

    @Override
    public Optional<User> updateBio(String email, String newBio) {

        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent()) {

            User user = optionalUser.get();

            user.setBio(newBio);

            userRepository.save(user);
        }

        return optionalUser;
    }

    @Override
    public Optional<User> updateRole(String email, Role newRole) {

            Optional<User> optionalUser = userRepository.findByEmail(email);

            if (optionalUser.isPresent()) {

                User user = optionalUser.get();

                user.setRole(newRole);

                userRepository.save(user);
            }

            return optionalUser;
    }

    @Override
    public Boolean hasBankAccount(String email) {
        return userRepository.hasBankAccount(email);
    }

}
