package org.example.postservice.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.postservice.constants.PostMode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "posts", indexes = {
        @Index(name = "user_id_idx", columnList = "user_id")
})
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", columnDefinition = "BINARY(16)", nullable = false)
    private UUID userId;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "is_like")
    private Boolean isLike = true;

    @Column(name = "is_comment")
    private Boolean isComment = true;

    @Column(name = "is_share")
    private Boolean isShare = true;

    @Column(name = "is_saved")
    private Boolean isSaved = true;

    @Column
    private String modes = "PUBLIC";

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostImage> images;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate () {
        this.createdAt = this.updatedAt = LocalDateTime.now();
    }
    @PreUpdate
    public void onUpdate () {
        this.updatedAt = LocalDateTime.now();
    }
}
