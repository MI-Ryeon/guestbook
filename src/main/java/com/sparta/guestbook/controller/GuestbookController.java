package com.sparta.guestbook.controller;

import com.sparta.guestbook.dto.GuestbookRequestDto;
import com.sparta.guestbook.dto.GuestbookResponseDto;
import com.sparta.guestbook.entity.Guestbook;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        // RequestDtd -> Entity
        Guestbook guestbook = new Guestbook(requestDto);

//        // Guestbook Max ID check
//        Long maxId = guestbookList.size() > 0 ? Collections.max(guestbookList.keySet()) + 1 : 1;
//        guestbook.setId(maxId);

        // DB 저장
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO guestbook (title, username, post, password) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update( con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, guestbook.getTitle());
            preparedStatement.setString(2, guestbook.getUsername());
            preparedStatement.setString(3, guestbook.getPost());
            preparedStatement.setLong(4, guestbook.getPassword());
            return preparedStatement;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        guestbook.setId(id);

        // Entity -> ResponseDto
        GuestbookResponseDto responseDto = new GuestbookResponseDto(guestbook);

        return responseDto;
    }

    @GetMapping("/posts")
    public List<GuestbookResponseDto> getPosts() {
        // DB 조회
        String sql = "SELECT * FROM guestbook";

        return jdbcTemplate.query(sql, new RowMapper<GuestbookResponseDto>() {
            @Override
            public GuestbookResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                // SQL의 결과로 받아온 guestbook 데이터들을 guestbookResponseDto 타입으로 변환
                Long id = rs.getLong("id");
                String title = rs.getString("title");
                String username = rs.getString("username");
                String post = rs.getString("post");
                Long password = rs.getLong("password");
                return new GuestbookResponseDto(id, title, username, post, password);
            }
        });
    }

//    @GetMapping("/post/{id}")
//    public GuestbookResponseDto getPost(@PathVariable Long id, @RequestBody GuestbookRequestDto requestDto) {
//        Guestbook guestbook = guestbookList.get(id);
//        GuestbookResponseDto responseDto = new GuestbookResponseDto(guestbook);
//        return responseDto;
//    }

    @PutMapping("/post/{id}")
    public Long updateContents(@PathVariable Long id, @RequestBody GuestbookRequestDto requestDto) {
        // 해당 게시글이 데이터베이스에 존재하는지 확인
        Guestbook guestbook = findById(id);
        if(guestbook != null){
            // post 내용 수정
            String sql = "UPDATE guestbook SET title = ?, username = ?, post = ? WHERE id = ?";
            jdbcTemplate.update(sql, requestDto.getTitle(), requestDto.getUsername(), requestDto.getPost(), id);

            return id;
        } else {
            throw new IllegalArgumentException("선택한 게시글은 존재하지 않습니다.");
        }
//        if (guestbookList.containsKey(id)) {
//            // 해당 게시글 가져오기
//            Guestbook guestbook = guestbookList.get(id);
//            if (requestDto.getPassword().equals(guestbookList.get(id).getPassword())) {
//                // 해당 게시글 수정
//                guestbook.update(requestDto);
//                return requestDto;
//            } else {
//                throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
//            }
//        } else {
//            throw new IllegalArgumentException("선택한 게시글은 존재하지 않습니다.");
//        }
    }

    @DeleteMapping("/post/{id}")
    public Long deleteContents(@PathVariable Long id, @RequestBody GuestbookRequestDto requestDto) {
        // 해당 게시글이 데이터베이스에 존재하는지 확인
        Guestbook guestbook = findById(id);
        if(guestbook != null){
            String sql = "DELETE FROM guestbook WHERE id = ?";
            jdbcTemplate.update(sql, id);

            return id;
        } else {
            throw new IllegalArgumentException("선택한 게시글은 존재하지 않습니다.");
        }
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

    private Guestbook findById(Long id){
        // DB 조회
        String sql = "SELECT * FROM guestbook WHERE id = ?";

        return jdbcTemplate.query(sql, resultSet -> {
            if(resultSet.next()){
                Guestbook guestbook = new Guestbook();
                guestbook.setTitle(resultSet.getString("title"));
                guestbook.setUsername(resultSet.getString("username"));
                guestbook.setPost(resultSet.getString("post"));
                return guestbook;
            } else {
                return null;
            }
        }, id);
    }
}
