package com.sparta.guestbook.service;

import com.sparta.guestbook.dto.GuestbookRequestDto;
import com.sparta.guestbook.dto.GuestbookResponseDto;
import com.sparta.guestbook.entity.Guestbook;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GuestbookService {
    private final Map<Long, Guestbook> guestbookList = new HashMap<>();

    public GuestbookResponseDto createPost(GuestbookRequestDto requestDto) {
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

    public List<GuestbookResponseDto> getPosts() {
        // Map To List
        List<GuestbookResponseDto> responseDtoList = guestbookList.values().stream()
                .map(GuestbookResponseDto::new).toList();
        return responseDtoList;
    }


    public GuestbookResponseDto getPost(Long id, GuestbookRequestDto requestDto) {
        Guestbook guestbook = guestbookList.get(id);
        GuestbookResponseDto responseDto = new GuestbookResponseDto(guestbook);
        return responseDto;
    }

    public GuestbookRequestDto updatePost(Long id, GuestbookRequestDto requestDto) {
        // 해당 게시글이 데이터베이스에 존재하는지 확인
        if (guestbookList.containsKey(id)) {
            // 해당 게시글 가져오기
            Guestbook guestbook = guestbookList.get(id);
            if (checkPassword(id, requestDto)) {
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

    public String deletePost(Long id, GuestbookRequestDto requestDto) {
        // 해당 게시글이 데이터베이스에 존재하는지 확인
        if (guestbookList.containsKey(id)) {
            if (checkPassword(id, requestDto)) {
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

    public Boolean checkPassword(Long id, GuestbookRequestDto requestDto) {
        if (requestDto.getPassword().equals(guestbookList.get(id).getPassword()))
            return true;
        else
            return false;
    }
}
