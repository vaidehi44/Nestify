package com.nestify.service;


import com.nestify.dto.ProfileUpdateRequestDto;
import com.nestify.dto.UserDto;
import com.nestify.entity.User;

public interface UserService {

    User getUserById(Long id);

    void updateProfile(ProfileUpdateRequestDto profileUpdateRequestDto);

    UserDto getMyProfile();

}
