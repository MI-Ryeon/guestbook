package com.sparta.guestbook.controller;

import com.sparta.guestbook.dto.GuestbookRequestDto;
import com.sparta.guestbook.dto.GuestbookResponseDto;
import com.sparta.guestbook.entity.Guestbook;
import com.sparta.guestbook.service.GuestbookService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class GuestbookController {
    private final Map<Long, Guestbook> guestbookList = new HashMap<>();

    @PostMapping("/post") // 게시글 작성
    public GuestbookResponseDto creatPost(@RequestBody GuestbookRequestDto requestDto) {
        GuestbookService service = new GuestbookService();
        return service.createPost(requestDto);
    }

    @GetMapping("/posts") // 게시글 전체 조회
    public List<GuestbookResponseDto> getPosts() {
        GuestbookService service = new GuestbookService();
        return service.getPosts();
    }

    @GetMapping("/post/{id}") // 게시글 1개 조회
    public GuestbookResponseDto getPost(@PathVariable Long id, @RequestBody GuestbookRequestDto requestDto) {
        GuestbookService service = new GuestbookService();
        return service.getPost(id, requestDto);
    }

    @PutMapping("/post/{id}") // 선택한 게시글 수정
    public GuestbookRequestDto updatePost(@PathVariable Long id, @RequestBody GuestbookRequestDto requestDto) {
        GuestbookService service = new GuestbookService();
        return service.updatePost(id, requestDto);
    }

    @DeleteMapping("/post/{id}") // 선택한 게시글 삭제
    public String deletePost(@PathVariable Long id, @RequestBody GuestbookRequestDto requestDto) {
        GuestbookService service = new GuestbookService();
        return service.deletePost(id, requestDto);
    }
}
