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

    @PutMapping("/contents/{id}")
    public Long updateContents(@PathVariable Long id, @RequestBody GuestbookRequestDto requestDto) {
        // 해당 게시글이 데이터베이스에 존재하는지 확인
        if (guestbookList.containsKey(id)) {
            // 해당 게시글 가져오기
            Guestbook guestbook = guestbookList.get(id);

            // 해당 게시글 수정
            guestbook.update(requestDto);
            return guestbook.getId();
        } else {
            throw new IllegalArgumentException("선택한 게시글은 존재하지 않습니다.");
        }
    }

    @DeleteMapping("/contents/{id}")
    public Long deleteContents(@PathVariable Long id) {
        // 해당 게시글이 데이터베이스에 존재하는지 확인
        if (guestbookList.containsKey(id)) {
            guestbookList.remove(id);
            return id;
        } else {
            throw new IllegalArgumentException("선택한 게시글은 존재하지 않습니다.");
        }
    }
}
