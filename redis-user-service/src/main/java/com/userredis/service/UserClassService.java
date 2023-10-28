package com.userredis.service;

import com.userredis.dto.UserClassDto;

import java.util.List;

public interface UserClassService {

    UserClassDto createUser(UserClassDto userClassDto);

    UserClassDto getUserById(int userId);

    List<UserClassDto> getAllUser();

    UserClassDto updateUser(int userId, UserClassDto userClassDto);

    String deleteUser(int userId);

}
