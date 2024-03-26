package com.ingsw.backend.model;

import java.io.Serializable;
import com.ingsw.backend.enumeration.Role;
import lombok.Data;

@SuppressWarnings("serial")
@Data
public class UserId implements Serializable {
    private String email;
    private Role role;
}
