package com.mila.mobile.mobileNew.controller;

import com.mila.mobile.mobileNew.model.request.UserDetailsRequestModel;
import com.mila.mobile.mobileNew.model.response.UserRest;
import com.mila.mobile.mobileNew.service.UserService;
import com.mila.mobile.mobileNew.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users") // http://localhost:8080/users
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public String getUser(){
        return "get user was called";
    }

    @PostMapping
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails){

        UserRest returnValue = new UserRest();

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails, userDto);

        UserDto createdUser = userService.createUser(userDto);
        BeanUtils.copyProperties(createdUser, returnValue);

        return returnValue;

    }

    @PutMapping
    public String updateUser(){
        return "update User was called";
    }

    @DeleteMapping
    public String deleteUser(){
        return "delete user was called";
    }
}
