package com.sparta.guestbook.controller;

import com.sparta.guestbook.dto.GuestbookRequestDto;
import com.sparta.guestbook.dto.GuestbookResponseDto;
import com.sparta.guestbook.entity.Guestbook;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class GuestbookController {
    private final Map<Long, Guestbook> guestbookList = new HashMap<>();

    @PostMapping("/post")
    public GuestbookResponseDto creatPost(@RequestBody GuestbookRequestDto requestDto) {
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

    @GetMapping("/posts")
    public List<GuestbookResponseDto> getPosts() {
        // Map To List
        List<GuestbookResponseDto> responseDtoList = guestbookList.values().stream()
                .map(GuestbookResponseDto::new).toList();
        return responseDtoList;
    }

    @GetMapping("/post/{id}")
    public GuestbookResponseDto getPost(@PathVariable Long id, @RequestBody GuestbookRequestDto requestDto){
        Guestbook guestbook = guestbookList.get(id);
        GuestbookResponseDto responseDto = new GuestbookResponseDto(guestbook);
        return responseDto;
    }

    @PutMapping("/post/{id}")
    public GuestbookRequestDto updateContents(@PathVariable Long id, @RequestBody GuestbookRequestDto requestDto) {
        // 해당 게시글이 데이터베이스에 존재하는지 확인
        if (guestbookList.containsKey(id)) {
            // 해당 게시글 가져오기
            Guestbook guestbook = guestbookList.get(id);
            if(requestDto.getPassword().equals(guestbookList.get(id).getPassword())) {
                // 해당 게시글 수정
                guestbook.update(requestDto);
                return requestDto;
            } else {
                throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
            }
        } else {
            throw new IllegalArgumentException("선택한 게시글은 존재하지 않습니다.");
        }
    }

    @DeleteMapping("/post/{id}")
    public String deleteContents(@PathVariable Long id, @RequestBody GuestbookRequestDto requestDto) {
        // 해당 게시글이 데이터베이스에 존재하는지 확인
        if (guestbookList.containsKey(id)) {
            if(requestDto.getPassword().equals(guestbookList.get(id).getPassword())) {
                // 해당 게시글 수정
                guestbookList.remove(id);
                return "success : true";
            } else {
                throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
            }
        } else {
            throw new IllegalArgumentException("선택한 게시글은 존재하지 않습니다.");
        }
    }
}
