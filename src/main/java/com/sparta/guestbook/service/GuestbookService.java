package com.sparta.guestbook.service;

import com.sparta.guestbook.dto.GuestbookRequestDto;
import com.sparta.guestbook.dto.GuestbookResponseDto;
import com.sparta.guestbook.entity.Guestbook;
import com.sparta.guestbook.repository.GuestbookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GuestbookService {
    private final GuestbookRepository repository;

    public GuestbookService(GuestbookRepository repository) {
        this.repository = repository;
    }

    public GuestbookResponseDto createPost(GuestbookRequestDto requestDto) {
        // RequestDtd -> Entity
        Guestbook guestbook = new Guestbook(requestDto);

        // DB 저장
        Guestbook saveBook = repository.save(guestbook);

        // Entity -> ResponseDto
        GuestbookResponseDto responseDto = new GuestbookResponseDto(guestbook);

        return responseDto;
    }

    public List<GuestbookResponseDto> getPosts() {
        // DB 조회
        return repository.findAll().stream().map(GuestbookResponseDto::new).toList();
    }

    public GuestbookResponseDto getPost(Long id) {
        Guestbook guestbook = findPost(id);
        return new GuestbookResponseDto(guestbook);
    }

    @Transactional
    public GuestbookResponseDto updatePosts(Long id, GuestbookRequestDto requestDto) {
        // 해당 게시글이 데이터베이스에 존재하는지 확인
        Guestbook guestbook = findPost(id);
        guestbook.checkPassword(requestDto.getPassword());

        guestbook.setTitle(requestDto.getTitle());
        guestbook.setUsername(requestDto.getUsername());
        guestbook.setPost(requestDto.getPost());

        return new GuestbookResponseDto(guestbook);
    }

    public void deletePosts(Long id, String inputPassword) {
        // 해당 게시글이 데이터베이스에 존재하는지 확인
        Guestbook guestbook = findPost(id);
        guestbook.checkPassword(inputPassword);
        repository.delete(guestbook);
    }

    private Guestbook findPost(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 메모는 존재하지 않습니다.")); // Optional
    }
}
