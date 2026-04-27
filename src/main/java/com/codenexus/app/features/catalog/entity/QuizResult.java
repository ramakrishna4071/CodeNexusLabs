package com.codenexus.app.features.catalog.entity;

import com.codenexus.app.config.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "quiz_results")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(callSuper = true)
public class QuizResult extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userEmail;
    private Integer score;
    private Integer totalQuestions;
    private Double percentage;
    private Long quizId;
}