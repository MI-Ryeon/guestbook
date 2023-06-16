package com.sparta.guestbook.repository;

import com.sparta.guestbook.dto.GuestbookRequestDto;
import com.sparta.guestbook.dto.GuestbookResponseDto;
import com.sparta.guestbook.entity.Guestbook;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class GuestbookRepository {
    private final JdbcTemplate jdbcTemplate;
    public GuestbookRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Guestbook save(Guestbook guestbook) {
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

        return guestbook;
    }

    public List<GuestbookResponseDto> findAll() {
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

    public GuestbookResponseDto find(Long id) {
        String sql = "SELECT * FROM guestbook WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new RowMapper<GuestbookResponseDto>() {
            @Override
            public GuestbookResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                Long id = rs.getLong("id");
                String title = rs.getString("title");
                String username = rs.getString("username");
                String post = rs.getString("post");
                Long password = rs.getLong("password");
                return new GuestbookResponseDto(id, title, username, post, password);
            }
        }, id);
    }

    public void update(Long id, GuestbookRequestDto requestDto) {
        String sql = "UPDATE guestbook SET title = ?, username = ?, post = ? WHERE id = ?";
        jdbcTemplate.update(sql, requestDto.getTitle(), requestDto.getUsername(), requestDto.getPost(), id);
    }

    public void delete(Long id) {
        String sql = "DELETE FROM guestbook WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public Guestbook findById(Long id) {
        // DB 조회
        String sql = "SELECT * FROM guestbook WHERE id = ?";

        return jdbcTemplate.query(sql, resultSet -> {
            if (resultSet.next()) {
                Guestbook guestbook = new Guestbook();
                guestbook.setTitle(resultSet.getString("title"));
                guestbook.setUsername(resultSet.getString("username"));
                guestbook.setPost(resultSet.getString("post"));
                guestbook.setPassword(resultSet.getLong("password"));
                return guestbook;
            } else {
                return null;
            }
        }, id);
    }

    public Boolean checkPassword(Long id, GuestbookRequestDto requestDto) {
        if (findById(id).getPassword().equals(requestDto.getPassword()))
            return true;
        else
            return false;
    }
}
