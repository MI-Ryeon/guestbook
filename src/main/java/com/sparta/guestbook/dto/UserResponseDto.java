package com.sparta.guestbook.dto;

public class UserResponseDto {
    private String msg;
    private String statusCode;

    public UserResponseDto(String msg, String statusCode) {
        this.msg = msg;
        this.statusCode = statusCode;
    }
}
