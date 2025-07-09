package org.example.postservice.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.postservice.constants.PostMode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
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

    @Column(name = "user_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID userId;

    @Column(columnDefinition = "TEXT")
    private String content;

    private Boolean isLike = true;
    private Boolean isComment = true;
    private Boolean isShare = true;
    private Boolean isSaved = true;

    @Column(length = 20)
    private String modes = "PUBLIC";

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
