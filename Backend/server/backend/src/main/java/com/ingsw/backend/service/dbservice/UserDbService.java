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

            return Optional.of(userRepository.save(user));
        }

        return Optional.empty();
    }

    @Override
    public Optional<User> updateRole(String email, Role newRole) {

        //TODO: Un macello dio belva
        /*
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();

            if (existingUser.getRole() == newRole) {
                // The user is already of the desired role, so there's nothing to do.
                return Optional.of(existingUser);
            }

            User newUser;
            if (newRole == Role.SELLER) {
                newUser = new Seller();
            } else if (newRole == Role.BUYER) {
                newUser = new Buyer();
            } else {
                throw new IllegalArgumentException("Invalid role: " + newRole);
            }

            // Copy all relevant fields from the existing user to the new user.
            newUser.setEmail(existingUser.getEmail());
            newUser.setPassword(existingUser.getPassword());
            newUser.setName(existingUser.getName());
            newUser.setRegion(existingUser.getRegion());
            newUser.setBio(existingUser.getBio());
            newUser.setExternalLinks(existingUser.getExternalLinks());

            // Delete the existing user and save the new user.
            userRepository.delete(existingUser);
            userRepository.save(newUser);
            //Non funziona, non posso cancellare il buyer, perderei tutte le sue aste e offerte così, il buyer deve persistere anche quando passo a seller

            return Optional.of(newUser);
        }
        */

        return Optional.empty();
    }

    @Override
    public Boolean hasBankAccount(String email) {
        return userRepository.hasBankAccount(email);
    }

}
