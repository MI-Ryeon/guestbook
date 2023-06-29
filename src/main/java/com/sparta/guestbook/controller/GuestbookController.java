package com.sparta.guestbook.controller;

import com.sparta.guestbook.dto.GuestbookRequestDto;
import com.sparta.guestbook.dto.GuestbookResponseDto;
import com.sparta.guestbook.service.GuestbookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GuestbookController {
    private final GuestbookService service;

    public GuestbookController(GuestbookService service) {
        this.service = service;
    }

    @PostMapping("/post")
    public GuestbookResponseDto creatPost(@RequestBody GuestbookRequestDto requestDto) {
        return service.createPost(requestDto);
    }

    @GetMapping("/posts")
    public List<GuestbookResponseDto> getPosts() {
        return service.getPosts();

    }

    @GetMapping("/post/{id}")
    public GuestbookResponseDto getPost(@PathVariable Long id) {
        return service.getPost(id);
    }

    @PutMapping("/post/{id}")
    public GuestbookResponseDto updatePosts(@PathVariable Long id, @RequestBody GuestbookRequestDto requestDto) {
        return service.updatePosts(id, requestDto);
    }

    @DeleteMapping("/post/{id}")
    public GuestbookResponseDto deletePosts(@PathVariable Long id, @RequestBody GuestbookRequestDto requestDto) {
        service.deletePosts(id, requestDto.getPassword());
        return new GuestbookResponseDto(true);
    }
}
