package com.codenexus.app.features.catalog.dto;

import lombok.*;
import java.util.List;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class CourseHierarchyDTO {
    private Long id;
    private String title;
    private String description;
    private List<TopicDTO> topics;

    @Data @Builder @NoArgsConstructor @AllArgsConstructor
    public static class TopicDTO {
        private Long id;
        private String title;
        private List<ContentDTO> contentBlocks;
    }

    @Data @Builder @NoArgsConstructor @AllArgsConstructor
    public static class ContentDTO {
        private Long id;
        private String type;
        private String content;
        private Integer orderIndex;
    }
}