package com.mila.mobile.mobileNew.service.impl;

import com.mila.mobile.mobileNew.UserRepository;
import com.mila.mobile.mobileNew.io.entity.UserEntity;
import com.mila.mobile.mobileNew.service.UserService;
import com.mila.mobile.mobileNew.shared.Utils;
import com.mila.mobile.mobileNew.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    Utils utils;
    @Override
    public UserDto createUser(UserDto user) {

        // -- check if email already exists before saving --
        if(userRepository.findByEmail(user.getEmail()) != null) throw new RuntimeException("User record already exists");

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);

        String publicUserId = utils.generateUserId(30);
        userEntity.setEncryptedPassword(publicUserId);
        userEntity.setUserId("TestUserID");


       UserEntity storedUserDetails = userRepository.save(userEntity);

       UserDto returnValue = new UserDto();
       BeanUtils.copyProperties(storedUserDetails, returnValue);

        return returnValue;
    }
}
