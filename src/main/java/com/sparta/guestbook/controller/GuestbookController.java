package com.sparta.guestbook.controller;

import com.sparta.guestbook.dto.GuestbookRequestDto;
import com.sparta.guestbook.dto.GuestbookResponseDto;
import com.sparta.guestbook.service.GuestbookService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GuestbookController {
    private final JdbcTemplate jdbcTemplate;

    public GuestbookController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/post")
    public GuestbookResponseDto creatPost(@RequestBody GuestbookRequestDto requestDto) {
        GuestbookService service = new GuestbookService(jdbcTemplate);
        return service.createPost(requestDto);
    }

    @GetMapping("/posts")
    public List<GuestbookResponseDto> getPosts() {
        GuestbookService service = new GuestbookService(jdbcTemplate);
        return service.getPosts();

    }

    @GetMapping("/post/{id}")
    public GuestbookResponseDto getPost(@PathVariable Long id, @RequestBody GuestbookRequestDto requestDto) {
        GuestbookService service = new GuestbookService(jdbcTemplate);
        return service.getPost(id);
    }

    @PutMapping("/post/{id}")
    public Long updatePosts(@PathVariable Long id, @RequestBody GuestbookRequestDto requestDto) {
        GuestbookService service = new GuestbookService(jdbcTemplate);
        return service.updatePosts(id, requestDto);
    }

    @DeleteMapping("/post/{id}")
    public Long deletePosts(@PathVariable Long id, @RequestBody GuestbookRequestDto requestDto) {
        GuestbookService service = new GuestbookService(jdbcTemplate);
        return service.deletePosts(id, requestDto);
    }
}
