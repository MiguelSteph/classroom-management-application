package com.classmanagement.resourceserver.configurations;

import static com.classmanagement.resourceserver.entities.Role.ADMIN_ROLE;
import static com.classmanagement.resourceserver.entities.Role.SUPERVISOR_ROLE;
import static com.classmanagement.resourceserver.entities.Role.GROUP_LEADER_ROLE;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

@Configuration
public class ResourceServerConfiguration extends WebSecurityConfigurerAdapter {

    private static final String JWT_ROLE_NAME = "authorities";
    private static final String ROLE_PREFIX = "";

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.cors().and().authorizeRequests()
                .mvcMatchers(HttpMethod.GET, "/buildings/*")
                .hasAnyRole(ADMIN_ROLE, SUPERVISOR_ROLE, GROUP_LEADER_ROLE)
                .mvcMatchers(HttpMethod.GET, "/sites"
                        , "/classrooms/{id}/supervisors"
                        , "/classrooms/{id}/availableTimeRanges")
                    .hasAnyRole(ADMIN_ROLE, SUPERVISOR_ROLE, GROUP_LEADER_ROLE)
                .mvcMatchers(HttpMethod.PUT, "/classrooms/{id}/shrinkAvailability")
                    .hasRole(SUPERVISOR_ROLE)
                .mvcMatchers(HttpMethod.POST, "/bookingRequests")
                    .hasAnyRole(ADMIN_ROLE, GROUP_LEADER_ROLE)
                .mvcMatchers(HttpMethod.GET, "/bookingRequests/*")
                .hasAnyRole(ADMIN_ROLE, SUPERVISOR_ROLE, GROUP_LEADER_ROLE)
                .anyRequest().authenticated().and().oauth2ResourceServer().jwt().jwtAuthenticationConverter(jwtAuthenticationConverter());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS);
    }

    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName(JWT_ROLE_NAME);
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix(ROLE_PREFIX);

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }
}
