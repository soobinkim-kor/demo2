package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners( value = AuditingEntityListener.class)
@Table(name = "COMMUNITY_POST")
public class ArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_ID", nullable = false)
    private Long articleId;

    @Column(name = "AUTHOR_NO", nullable = false)
    private Long authorNo;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "CONTENT", nullable = false)
    private String content;

    @Column(name = "VIEW_COUNT")
    private int viewCount;

    @Column(name = "COMMENT_COUNT")
    private int commentCount;

    @Column(name = "LIKE_COUNT")
    private int likeCount;

    @Column(name = "IS_DELETED")
    private String isDeleted;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;
}
