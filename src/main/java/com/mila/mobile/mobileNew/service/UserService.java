package com.mila.mobile.mobileNew.service;

import com.mila.mobile.mobileNew.shared.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserDto createUser(UserDto user);
}
