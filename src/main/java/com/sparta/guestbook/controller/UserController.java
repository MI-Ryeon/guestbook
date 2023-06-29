package com.sparta.guestbook.controller;

import com.sparta.guestbook.dto.SignupRequestDto;
import com.sparta.guestbook.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/auth")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public void signup(SignupRequestDto requestDto) {
        userService.signup(requestDto);
    }

//    @PostMapping("/login")
//    public UserResponseDto login(SignupRequestDto requestDto) {
//        userService.login(requestDto);
//        return new UserResponseDto("로그인 성공", "200");
//    }
}
