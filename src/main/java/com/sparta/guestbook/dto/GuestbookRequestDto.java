package com.sparta.guestbook.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GuestbookRequestDto {
    private String title;
    private String username;
    private String contents;
    private Long password;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdDatetime = LocalDateTime.now();
}
