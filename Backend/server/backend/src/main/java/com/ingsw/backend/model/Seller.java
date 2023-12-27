package com.ingsw.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="sellers")
public class Seller extends User {
}
