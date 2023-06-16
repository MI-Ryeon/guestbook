package com.sparta.guestbook.service;

import com.sparta.guestbook.dto.GuestbookRequestDto;
import com.sparta.guestbook.dto.GuestbookResponseDto;
import com.sparta.guestbook.entity.Guestbook;
import com.sparta.guestbook.repository.GuestbookRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestbookService {
    private final JdbcTemplate jdbcTemplate;

    public GuestbookService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public GuestbookResponseDto createPost(GuestbookRequestDto requestDto) {
        // RequestDtd -> Entity
        Guestbook guestbook = new Guestbook(requestDto);

        // DB 저장
        GuestbookRepository repository = new GuestbookRepository(jdbcTemplate);
        Guestbook saveBook = repository.save(guestbook);

        // Entity -> ResponseDto
        GuestbookResponseDto responseDto = new GuestbookResponseDto(guestbook);

        return responseDto;
    }

    public List<GuestbookResponseDto> getPosts() {
        // DB 조회
        GuestbookRepository repository = new GuestbookRepository(jdbcTemplate);
        return repository.findAll();
    }

    public GuestbookResponseDto getPost(Long id) {
        GuestbookRepository repository = new GuestbookRepository(jdbcTemplate);
        return repository.find(id);
    }

    public Long updatePosts(Long id, GuestbookRequestDto requestDto) {
        GuestbookRepository repository = new GuestbookRepository(jdbcTemplate);
        // 해당 게시글이 데이터베이스에 존재하는지 확인
        Guestbook guestbook = repository.findById(id);
        if (guestbook != null) {
            if (repository.checkPassword(id, requestDto)) { // 비밀번호 확인
                // post 내용 수정
                repository.update(id, requestDto);
                return id;
            } else {
                throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
            }
        } else {
            throw new IllegalArgumentException("선택한 게시글은 존재하지 않습니다.");
        }
    }

    public Long deletePosts(Long id, GuestbookRequestDto requestDto) {
        GuestbookRepository repository = new GuestbookRepository(jdbcTemplate);
        // 해당 게시글이 데이터베이스에 존재하는지 확인
        Guestbook guestbook = repository.findById(id);
        if (guestbook != null) {
            if (repository.checkPassword(id, requestDto)) { // 비밀번호 확인
                // 해당 게시글 삭제
                repository.delete(id);
                return id;
            } else {
                throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
            }
        } else {
            throw new IllegalArgumentException("선택한 게시글은 존재하지 않습니다.");
        }
    }
}
