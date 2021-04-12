package com.classmanagement.authorizationserver.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private List<User> users = new ArrayList<>();

    @Override
    public String getAuthority() {
        return name;
    }
}


