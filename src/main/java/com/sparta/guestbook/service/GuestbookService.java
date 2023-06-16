package com.sparta.guestbook.service;

import com.sparta.guestbook.dto.GuestbookRequestDto;
import com.sparta.guestbook.dto.GuestbookResponseDto;
import com.sparta.guestbook.entity.Guestbook;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO guestbook (title, username, post, password) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(con -> {
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


//    public GuestbookResponseDto getPost(Long id, GuestbookRequestDto requestDto) {
//        Guestbook guestbook = guestbookList.get(id);
//        GuestbookResponseDto responseDto = new GuestbookResponseDto(guestbook);
//        return responseDto;
//    }

    public Long updatePosts(Long id, GuestbookRequestDto requestDto) {
        // 해당 게시글이 데이터베이스에 존재하는지 확인
        Guestbook guestbook = findById(id);
        if (guestbook != null) {
            // post 내용 수정
            String sql = "UPDATE guestbook SET title = ?, username = ?, post = ? WHERE id = ?";
            jdbcTemplate.update(sql, requestDto.getTitle(), requestDto.getUsername(), requestDto.getPost(), id);

            return id;
        } else {
            throw new IllegalArgumentException("선택한 게시글은 존재하지 않습니다.");
        }
//        // 해당 게시글이 데이터베이스에 존재하는지 확인
//        if (guestbookList.containsKey(id)) {
//            // 해당 게시글 가져오기
//            Guestbook guestbook = guestbookList.get(id);
//            if (checkPassword(id, requestDto)) {
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

    public Long deletePosts(Long id, GuestbookRequestDto requestDto) {
        // 해당 게시글이 데이터베이스에 존재하는지 확인
        Guestbook guestbook = findById(id);
        if (guestbook != null) {
            String sql = "DELETE FROM guestbook WHERE id = ?";
            jdbcTemplate.update(sql, id);

            return id;
        } else {
            throw new IllegalArgumentException("선택한 게시글은 존재하지 않습니다.");
        }
//        // 해당 게시글이 데이터베이스에 존재하는지 확인
//        if (guestbookList.containsKey(id)) {
//            if (checkPassword(id, requestDteo)) {
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

//    public Boolean checkPassword(Long id, GuestbookRequestDto requestDto) {
//        if (requestDto.getPassword().equals(guestbookList.get(id).getPassword()))
//            return true;
//        else
//            return false;
//    }
    private Guestbook findById(Long id) {
        // DB 조회
        String sql = "SELECT * FROM guestbook WHERE id = ?";

        return jdbcTemplate.query(sql, resultSet -> {
            if (resultSet.next()) {
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
