package com.ingsw.backend.controller;

import com.ingsw.backend.enumeration.Region;
import com.ingsw.backend.enumeration.Role;
import com.ingsw.backend.model.Buyer;
import com.ingsw.backend.model.Seller;
import com.ingsw.backend.model.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class UserControllerTest {

    Buyer testBuyer = new Buyer();
    Seller testSeller = new Seller();

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserController userController;

    @BeforeEach
    @Transactional
    void setUpBuyer() {
        testBuyer.setEmail("test_buyer@example.com");
        testBuyer.setName("test_buyer_name");
        testBuyer.setPassword("test_buyer_password");
        testBuyer.setBio("test_buyer_bio");
        testBuyer.setRole(Role.BUYER);
        testBuyer.setRegion(Region.NOT_SPECIFIED);
        entityManager.persist(testBuyer);
        entityManager.flush();
    }

    @BeforeEach
    @Transactional
    void setUpSeller() {
        testSeller.setEmail("test_seller@example.com");
        testSeller.setName("test_seller_name");
        testSeller.setPassword("test_seller_password");
        testSeller.setBio("test_seller_bio");
        testSeller.setRole(Role.SELLER);
        testSeller.setRegion(Region.NOT_SPECIFIED);
        entityManager.persist(testSeller);
        entityManager.flush();
    }

    @AfterEach
    @Transactional
    void tearDownBuyer() {
        entityManager.remove(testBuyer);
        entityManager.flush();
    }

    @AfterEach
    @Transactional
    void tearDownSeller() {
        entityManager.remove(testSeller);
        entityManager.flush();
    }

    /**
     * Those test methods use the `isExistingUser()` method from the `UserController` class to check if the user exists.
     * <p>
     * The method identifies the following equivalence classes for the input parameters:
     * <p>
     * 1. Valid email and role that correspond to an existing account in the system (valid equivalence class).
     * <br>
     * 2. Valid email but invalid or non-existent role (invalid equivalence class).
     * <br>
     * 3. Invalid or non-existent email but valid role (invalid equivalence class).
     * <br>
     * 4. Invalid or non-existent email and role (invalid equivalence class).
     */

    @Test
    @Transactional
    void testDoesAccountExist_ExistingUser() {
        Boolean accountExists = userController.doesAccountExist(testBuyer.getEmail(), testBuyer.getRole()).getBody();
        assertEquals(Boolean.TRUE, accountExists);
    }

    @Test
    @Transactional
    void testDoesAccountExist_NotExistingUser() {
        Boolean accountExists = userController.doesAccountExist("notExisting@example.com", Role.SELLER).getBody();
        assertEquals(Boolean.FALSE, accountExists);
    }

    @Test
    @Transactional
    void testDoesAccountExist_NullEmail() {
        Boolean accountExists = userController.doesAccountExist(null, Role.SELLER).getBody();
        assertEquals(Boolean.FALSE, accountExists);
    }

    @Test
    @Transactional
    void testDoesAccountExist_EmptyEmail() {
        Boolean accountExists = userController.doesAccountExist("", Role.BUYER).getBody();
        assertEquals(Boolean.FALSE, accountExists);
    }

    @Test
    @Transactional
    void testDoesAccountExist_NullRole() {
        Boolean accountExists = userController.doesAccountExist(testSeller.getEmail(), null).getBody();
        assertEquals(Boolean.FALSE, accountExists);
    }

    /**
     * Those test methods use the `updateRegion()` method from the `UserController` class to change user region.
     * <p>
     * The method identifies the following equivalence classes for the input parameters:
     * <p>
     * 1. Valid region, email and role that correspond to an existing account in the system (valid equivalence class).
     * <br>
     * 2. Valid email, role but null region (valid equivalence class).
     * <br>
     * 3. Valid email, null role and valid region (invalid equivalence class).
     * <br>
     * 4. Null email, valid role and region (invalid equivalence class).
     * <br>
     * 5. Empty email, valid role and region (invalid equivalence class).
     * <br>
     * 6. Invalid email, valid role and region (invalid equivalence class).
     */

    @Test
    @Transactional
    void testUpdateRegion_ValidEmail_ValidRole_ValidRegion() {
        Region newRegion = Region.CAMPANIA;
        User updatedUser = userController.updateRegion(testSeller.getEmail(), testSeller.getRole(), newRegion).getBody();
        assertEquals(newRegion, updatedUser.getRegion());
    }

    @Test
    @Transactional
    void testUpdateRegion_ValidEmail_ValidRole_NullRegion() {
        assertNull(userController.updateRegion(testSeller.getEmail(), testSeller.getRole(), null).getBody().getRegion());
    }

    @Test
    @Transactional
    void testUpdateRegion_ValidEmail_NullRole_ValidRegion() {
        assertNull(userController.updateRegion(testSeller.getEmail(), null, Region.CAMPANIA).getBody());
    }

    @Test
    @Transactional
    void testUpdateRegion_NullEmail_ValidRole_ValidRegion() {
        assertNull(userController.updateRegion(null, testSeller.getRole(), Region.CAMPANIA).getBody());
    }

    @Test
    @Transactional
    void testUpdateRegion_EmptyEmail_ValidRole_ValidRegion() {
        assertNull(userController.updateRegion("", testSeller.getRole(), Region.CAMPANIA).getBody());
    }

    @Test
    @Transactional
    void testUpdateRegion_InvalidEmail_ValidRole_ValidRegion() {
        assertNull(userController.updateRegion("invalid@example.com", testSeller.getRole(), Region.CAMPANIA).getBody());
    }





    @Test
    @Transactional
    void testUpdateBio_ValidEmail_ValidRole_ValidBio() {
        String newBio = "test_seller_new_bio";
        User updatedUser = userController.updateBio(testSeller.getEmail(), testSeller.getRole(), newBio).getBody();
        assertEquals(newBio, updatedUser.getBio());
    }

    @Test
    @Transactional
    void testUpdateBio_ValidEmail_ValidRole_EmptyBio() {
        User updatedUser = userController.updateBio(testSeller.getEmail(), testSeller.getRole(), "").getBody();
        assertEquals("", updatedUser.getBio());
    }

    @Test
    @Transactional
    void testUpdateBio_ValidEmail_ValidRole_NullBio() {
        User updatedUser = userController.updateBio(testSeller.getEmail(), testSeller.getRole(), null).getBody();
        assertEquals("", updatedUser.getBio());
    }

    @Test
    @Transactional
    void testUpdateBio_ValidEmail_NullRole_ValidBio() {
        String newBio = "test_seller_new_bio";
        User updatedUser = userController.updateBio(testSeller.getEmail(), null, newBio).getBody();
        assertNull(updatedUser);
    }



    @ParameterizedTest
    @Transactional
    @NullAndEmptySource
    @ValueSource(strings = {"invalid@example.com"})
    void testUpdateBio_InvalidEmail_ValidRole_ValidBio(String email) {
        String newBio = "test_seller_new_bio";
        User updatedUser = userController.updateBio(email, testSeller.getRole(), newBio).getBody();
        assertNull(updatedUser);
    }
}