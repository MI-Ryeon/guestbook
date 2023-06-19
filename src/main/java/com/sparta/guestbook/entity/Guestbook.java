package com.sparta.guestbook.entity;

import com.sparta.guestbook.dto.GuestbookRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "guestbook")
@NoArgsConstructor
public class Guestbook extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, length = 500)
    private String post;

    @Column(nullable = false)
    private String password;

    public Guestbook(GuestbookRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.username = requestDto.getUsername();
        this.post = requestDto.getPost();
        this.password = requestDto.getPassword();
    }

    public void checkPassword(String inputPassword) {
        if (!password.equals(inputPassword)) {
            throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
        }
    }
}
