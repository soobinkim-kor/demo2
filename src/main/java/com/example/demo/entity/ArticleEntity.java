package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Builder
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(value = AuditingEntityListener.class)
@Table(name = "ARTICLE_BASE")
public class ArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ARTICLE_ID", nullable = false)
    private Long articleId;

    @Column(name = "AUTHOR_NO", nullable = false)
    private Long authorNo;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "CONTENT", nullable = false)
    private String content;

    @Builder.Default
    @Column(name = "VIEW_COUNT")
    private int viewCount = 0;

    @Builder.Default
    @Column(name = "COMMENT_COUNT")
    private int commentCount = 0;

    @Builder.Default
    @Column(name = "LIKE_COUNT")
    private int likeCount = 0;

    @Builder.Default
    @Column(name = "IS_DELETED")
    private String isDeleted = "N";

    @Column(name = "CREATED_AT")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
