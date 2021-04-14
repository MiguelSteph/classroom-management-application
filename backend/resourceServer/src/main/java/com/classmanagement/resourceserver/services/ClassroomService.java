package com.classmanagement.resourceserver.services;

import com.classmanagement.resourceserver.dtos.UserDto;

import java.util.List;

public interface ClassroomService {

    List<UserDto> classroomSupervisors(int id);

}
