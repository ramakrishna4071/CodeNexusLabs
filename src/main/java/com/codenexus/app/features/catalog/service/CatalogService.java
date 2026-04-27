package com.codenexus.app.features.catalog.service;

import com.codenexus.app.features.catalog.dto.CourseHierarchyDTO;
import com.codenexus.app.features.catalog.entity.*;
import com.codenexus.app.features.catalog.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CatalogService {
    private final CourseRepository courseRepository;
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public CourseHierarchyDTO getFullCourse(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
        return CourseHierarchyDTO.builder()
                .id(course.getId()).title(course.getTitle()).description(course.getDescription())
                .topics(course.getTopics().stream().map(t -> 
                    CourseHierarchyDTO.TopicDTO.builder().id(t.getId()).title(t.getTitle())
                    .contentBlocks(t.getContentBlocks().stream().map(cb -> 
                        CourseHierarchyDTO.ContentDTO.builder().id(cb.getId()).type(cb.getType().name()).content(cb.getContent()).orderIndex(cb.getOrderIndex()).build()
                    ).collect(Collectors.toList())).build()
                ).collect(Collectors.toList())).build();
    }

    @Transactional
    public Course createCourse(Long categoryId, Course course) {
        Category cat = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category 1 not found"));
        course.setCategory(cat);
        course.setDeleted(false);
        return courseRepository.saveAndFlush(course);
    }
}