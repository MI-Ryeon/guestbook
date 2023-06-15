package com.sparta.guestbook.controller;

import com.sparta.guestbook.dto.GuestbookRequestDto;
import com.sparta.guestbook.dto.GuestbookResponseDto;
import com.sparta.guestbook.entity.Guestbook;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/guestbook")
public class GuestbookController {
    private final Map<Long, Guestbook> guestbookList = new HashMap<>();

    @PostMapping("/contents")
    public GuestbookResponseDto creatContent(@RequestBody GuestbookRequestDto requestDto) {
        // RequestDtd -> Entity
        Guestbook guestbook = new Guestbook(requestDto);

        // Guestbook Max ID check
        Long maxId = guestbookList.size() > 0 ? Collections.max(guestbookList.keySet()) + 1 : 1;
        guestbook.setId(maxId);

        // DB 저장
        guestbookList.put(guestbook.getId(), guestbook);

        // Entity -> ResponseDto
        GuestbookResponseDto responseDto = new GuestbookResponseDto(guestbook);

        return responseDto;
    }

    @GetMapping("/contents")
    public List<GuestbookResponseDto> getContents() {
        // Map To List
        List<GuestbookResponseDto> responseDtoList = guestbookList.values().stream()
                .map(GuestbookResponseDto::new).toList();
        return responseDtoList;
    }
}
