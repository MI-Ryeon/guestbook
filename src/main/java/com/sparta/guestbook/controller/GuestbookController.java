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
//    private final Map<Long, Guestbook> guestbookList = new HashMap<>();
    private final JdbcTemplate jdbcTemplate;

    public GuestbookController(JdbcTemplate jdbcTemplate){
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

//    @GetMapping("/post/{id}")
//    public GuestbookResponseDto getPost(@PathVariable Long id, @RequestBody GuestbookRequestDto requestDto) {
//        Guestbook guestbook = guestbookList.get(id);
//        GuestbookResponseDto responseDto = new GuestbookResponseDto(guestbook);
//        return responseDto;
//    }

    @PutMapping("/post/{id}")
    public Long updatePosts(@PathVariable Long id, @RequestBody GuestbookRequestDto requestDto) {
        GuestbookService service = new GuestbookService(jdbcTemplate);
        return service.updatePosts(id, requestDto);
    }

    @DeleteMapping("/post/{id}")
    public Long deletePosts(@PathVariable Long id, @RequestBody GuestbookRequestDto requestDto) {
        GuestbookService service = new GuestbookService(jdbcTemplate);
        return service.deletePosts(id, requestDto);

//        if (guestbookList.containsKey(id)) {
//            if (requestDto.getPassword().equals(guestbookList.get(id).getPassword())) {
//                // 해당 게시글 수정
//                guestbookList.remove(id);
//                return "success : true";
//            } else {
//                throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
//            }
//        } else {
//            throw new IllegalArgumentException("선택한 게시글은 존재하지 않습니다.");
//        }
    }


}
