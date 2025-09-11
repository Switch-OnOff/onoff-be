package com.switchteam.onoff.domain.user.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.switchteam.onoff.domain.post.domain.Post;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA 기본 생성자
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;  // User PK (Auto Increment)

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 10)
    private String name;

    @Column(nullable = false, unique = true, length = 20)
    private String contact; // 연락처

    @Column(nullable = false)
    private String password; // 암호화 저장

    private LocalDateTime createdAt = LocalDateTime.now();

    // User : Post = 1 : N
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Post> posts = new ArrayList<>();
}