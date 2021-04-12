package com.classmanagement.authorizationserver.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;

    private String lastName;

    @Email
    private String email;

    private String password;

    private boolean isDefaultPwd;

    private boolean isEnabled;

    private boolean isEmailVerified;

    @ManyToOne
    private User createdBy;

    @ManyToOne
    private Role role;
}


