package com.mila.mobile.mobileNew.service.impl;

import com.mila.mobile.mobileNew.UserRepository;
import com.mila.mobile.mobileNew.io.entity.UserEntity;
import com.mila.mobile.mobileNew.service.UserService;
import com.mila.mobile.mobileNew.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Override
    public UserDto createUser(UserDto user) {

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);

        userEntity.setEncryptedPassword("Test");
        userEntity.setUserId("TestUserID");


       UserEntity storedUserDetails = userRepository.save(userEntity);

       UserDto returnValue = new UserDto();
       BeanUtils.copyProperties(storedUserDetails, returnValue);

        return returnValue;
    }
}
