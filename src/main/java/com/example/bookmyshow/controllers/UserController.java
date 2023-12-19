package com.example.bookmyshow.controllers;

import com.example.bookmyshow.dto.ResponseStatus;
import com.example.bookmyshow.dto.SignUpRequestDto;
import com.example.bookmyshow.dto.SignUpResponseDto;
import com.example.bookmyshow.models.User;
import com.example.bookmyshow.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }
    public SignUpResponseDto signUp(SignUpRequestDto signUpRequestDto){
        SignUpResponseDto responseDto = new SignUpResponseDto();
        try {
            User user = userService.signUp(signUpRequestDto.getEmail(),
                    signUpRequestDto.getPassword());
            responseDto.setResponseStatus(ResponseStatus.SUCCESSFUL);
            responseDto.setUserId(user.getId());
        } catch (Exception e){
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }
        return responseDto;
    }
}
