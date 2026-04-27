package com.codenexus.app.features.catalog.entity;

import com.codenexus.app.config.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Question extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String questionText;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String correctOption;
    
    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;
}