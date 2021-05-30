package com.classmanagement.resourceserver.services;

import com.classmanagement.resourceserver.dtos.UserDto;
import com.classmanagement.resourceserver.entities.User;

import java.util.List;

public interface UserService {

    List<UserDto> getUsersByRole(String role);

    User addNewUser(UserDto userDto, String currentUsername);

    User enableUser(long userId, String currentUsername);

    User disableUser(long userId, String currentUsername);

    User updatePwd(String username, String oldPwd, String newPwd, String currentUsername);

}
