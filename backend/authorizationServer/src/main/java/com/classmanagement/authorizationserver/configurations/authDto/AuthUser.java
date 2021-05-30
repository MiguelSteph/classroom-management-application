package com.classmanagement.authorizationserver.configurations.authDto;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Value
@EqualsAndHashCode(callSuper = false)
public class AuthUser extends User {

    private String fullname;
    private boolean isDefaultPwd;

    public AuthUser(String username, String password, boolean enabled,
                    boolean accountNonExpired, boolean credentialsNonExpired,
                    boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities,
                    boolean isDefaultPwd, String fullname) {
        super(username, password, enabled, accountNonExpired,
                credentialsNonExpired, accountNonLocked, authorities);
        this.isDefaultPwd = isDefaultPwd;
        this.fullname = fullname;
    }
}
