package com.classmanagement.resourceserver.rest;

import com.classmanagement.resourceserver.dtos.UserDto;
import com.classmanagement.resourceserver.entities.User;
import com.classmanagement.resourceserver.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
public class UserResource {

    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/users")
    public ResponseEntity<Object> createUser(Principal principal,
                                             @RequestBody UserDto userDto) {
        User createdUser = userService.addNewUser(userDto, getCurrentUserName((JwtAuthenticationToken)principal));
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(createdUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/users/enable")
    public void enableUser(Principal principal,
                           @RequestBody Long userId) {
        userService.enableUser(userId, getCurrentUserName((JwtAuthenticationToken)principal));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/users/disable")
    public void disableUser(Principal principal,
                           @RequestBody Long userId) {
        userService.disableUser(userId, getCurrentUserName((JwtAuthenticationToken)principal));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR', 'GROUPLEADER')")
    @PutMapping("/users/pwd")
    public void updatePwd(Principal principal,
                            @RequestBody Map<String, Object> map) {
        userService.updatePwd(String.valueOf(map.get("username")),
                String.valueOf(map.get("oldPwd")), String.valueOf(map.get("newPwd")),
                getCurrentUserName((JwtAuthenticationToken)principal));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users/role/{role}")
    public List<UserDto> getUsersByRole(@PathVariable String role) {
        return userService.getUsersByRole(role);
    }

    private String getCurrentUserName(JwtAuthenticationToken token) {
        return String.valueOf(token.getTokenAttributes().get("user_name"));
    }
}
