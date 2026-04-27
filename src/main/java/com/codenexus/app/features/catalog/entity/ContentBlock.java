package com.codenexus.app.features.catalog.entity;

import com.codenexus.app.config.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "content_blocks")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ContentBlock extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private BlockType type; // TEXT, CODE, EXAMPLE

    @Column(columnDefinition = "TEXT")
    private String content;

    private Integer orderIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id")
    private Topic topic;

    public enum BlockType { TEXT, CODE, EXAMPLE }
}