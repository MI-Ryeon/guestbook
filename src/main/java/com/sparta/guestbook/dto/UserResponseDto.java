package com.sparta.guestbook.dto;

import lombok.Getter;

@Getter
public class UserResponseDto {
    private String msg;
    private String statusCode;

    public UserResponseDto(String msg, String statusCode) {
        this.msg = msg;
        this.statusCode = statusCode;
    }
}
