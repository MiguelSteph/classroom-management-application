package com.classmanagement.resourceserver.services.implementations;

import com.classmanagement.resourceserver.dtos.UserDto;
import com.classmanagement.resourceserver.entities.Role;
import com.classmanagement.resourceserver.entities.User;
import com.classmanagement.resourceserver.exceptions.UserCausedBackendException;
import com.classmanagement.resourceserver.repositories.RoleRepository;
import com.classmanagement.resourceserver.repositories.UserRepository;
import com.classmanagement.resourceserver.services.UserService;
import com.classmanagement.resourceserver.util.mappers.DtoMapperUtil;
import lombok.AllArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;
import org.passay.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserDto> getUsersByRole(String role) {
        Role roleInstance = roleRepository.findByName(role);
        if (roleInstance == null) return Collections.emptyList();
        return userRepository.findAllByRole(roleInstance)
                .stream()
                .map(DtoMapperUtil::convertToUserDto).collect(Collectors.toList());
    }

    @Override
    public User updatePwd(String username, String oldPwd, String newPwd, String currentUsername) {
        User currentLoggedUser = userRepository.findByEmail(currentUsername);
        User user = userRepository.findByEmail(username);
        if (!currentLoggedUser.getId().equals(user.getId())) {
            throw new UserCausedBackendException("Unexpected exception happens. Please try again");
        }

        if (!passwordEncoder.matches(oldPwd, user.getPassword())) {
            throw new UserCausedBackendException("Provided password is incorrect");
        }
        checkPasswordPolicy(newPwd);
        user.setPassword(passwordEncoder.encode(newPwd));
        user.setDefaultPwd(false);
        return userRepository.save(user);
    }

    private void checkPasswordPolicy(String password) {
        List<Rule> passwordRules = new ArrayList<>();
        passwordRules.add(new LengthRule(10, 128));
        CharacterCharacteristicsRule passwordChars = new CharacterCharacteristicsRule(4,
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                new CharacterRule(EnglishCharacterData.Digit, 1),
                new CharacterRule(EnglishCharacterData.Special, 1));
        passwordRules.add(passwordChars);
        PasswordValidator passwordValidator = new PasswordValidator(passwordRules);
        PasswordData passwordData = new PasswordData(password);
        RuleResult ruleResult = passwordValidator.validate(passwordData);
        if (!ruleResult.isValid()) {
            throw new UserCausedBackendException("Provided password does not follow the password rules.");
        }
    }

    @Override
    public User disableUser(long userId, String currentUsername) {
        User currentLoggedUser = userRepository.findByEmail(currentUsername);
        User user = userRepository.getOne(userId);
        if (!currentLoggedUser.getRole().getName().contains(Role.ADMIN_ROLE)) {
            throw new UserCausedBackendException("Only Admin users are allowed to disable user");
        }
        user.setEnabled(false);
        return userRepository.save(user);
    }

    @Override
    public User enableUser(long userId, String currentUsername) {
        User currentLoggedUser = userRepository.findByEmail(currentUsername);
        User user = userRepository.getOne(userId);
        if (!currentLoggedUser.getRole().getName().contains(Role.ADMIN_ROLE)) {
            throw new UserCausedBackendException("Only Admin users are allowed to enable user");
        }
        user.setEnabled(true);
        return userRepository.save(user);
    }

    @Override
    public User addNewUser(UserDto userDto, String currentUsername) {
        Role role = checkingForAddNewUser(userDto);
        User currentLoggedUser = userRepository.findByEmail(currentUsername);

        if (!currentLoggedUser.getRole().getName().contains(Role.ADMIN_ROLE)) {
            throw new UserCausedBackendException("Only Admin users are allowed to create new user");
        }
        checkPasswordPolicy(userDto.getPassword());

        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setDefaultPwd(true);
        user.setEmailVerified(false);
        user.setEnabled(true);
        user.setRole(role);
        user.setCreatedBy(currentLoggedUser);
        return userRepository.save(user);
    }

    private Role checkingForAddNewUser(UserDto userDto) {
        checkIfName(userDto.getFirstName());
        checkIfName(userDto.getLastName());
        checkEmail(userDto.getEmail());
        return checkRole(userDto.getRole());
    }

    private Role checkRole(String role) {
        if (role == null || role.isEmpty()) {
            throw new UserCausedBackendException("Please provide a user role");
        }
        Role roleInstance = roleRepository.findByName(role);
        if (roleInstance == null) {
            throw new UserCausedBackendException("Incorrect user role provided");
        }
        return roleInstance;
    }

    private void checkEmail(String email) {
        if (email == null || email.isEmpty() || !EmailValidator.getInstance().isValid(email)) {
            throw new UserCausedBackendException("Invalid Email");
        }
        User user = userRepository.findByEmail(email);
        if (user != null) {
            throw new UserCausedBackendException("Provided email already exists.");
        }
    }

    private void checkIfName(String name) {
        if (name == null || name.length() <= 2) {
            throw new UserCausedBackendException("Firstname and lastname should have at least two characters");
        }
    }
}
