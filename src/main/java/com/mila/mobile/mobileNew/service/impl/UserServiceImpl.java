package com.mila.mobile.mobileNew.service.impl;

import com.mila.mobile.mobileNew.UserRepository;
import com.mila.mobile.mobileNew.io.entity.UserEntity;
import com.mila.mobile.mobileNew.service.UserService;
import com.mila.mobile.mobileNew.shared.Utils;
import com.mila.mobile.mobileNew.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    Utils utils;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public UserDto createUser(UserDto user) {

        // -- check if email already exists before saving --
        if(userRepository.findByEmail(user.getEmail()) != null) throw new RuntimeException("User record already exists");

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);

        String publicUserId = utils.generateUserId(30);
        userEntity.setUserId(publicUserId);
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));


       UserEntity storedUserDetails = userRepository.save(userEntity);

       UserDto returnValue = new UserDto();
       BeanUtils.copyProperties(storedUserDetails, returnValue);

        return returnValue;
    }

    @Override
    public UserDto getUser(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) throw new UsernameNotFoundException(email);

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(userEntity, returnValue);

        return returnValue;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(username);

        if(userEntity == null) throw new UsernameNotFoundException(username);


        return new User(username, userEntity.getEncryptedPassword(), new ArrayList<>());
    }
}
