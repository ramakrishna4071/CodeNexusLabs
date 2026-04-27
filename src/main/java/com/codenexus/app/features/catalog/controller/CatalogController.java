package com.codenexus.app.features.catalog.controller;

import com.codenexus.app.common.dto.ApiResponse;
import com.codenexus.app.features.catalog.entity.*;
import com.codenexus.app.features.catalog.repository.*;
import com.codenexus.app.features.catalog.service.CatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/v1/catalog")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CatalogController {

    private final CatalogService catalogService;
    private final TopicRepository topicRepository;
    private final ContentBlockRepository contentBlockRepository;
    private final CourseRepository courseRepository;

    // 1. CREATE COURSE (This is the one currently working for you)
    @PostMapping("/admin/courses")
    public ApiResponse<Course> createCourse(@RequestBody Map<String, Object> payload) {
        Course course = new Course();
        course.setTitle(payload.get("title").toString());
        course.setDescription(payload.get("description").toString());
        Long categoryId = Long.valueOf(payload.get("categoryId").toString());
        return ApiResponse.success(catalogService.createCourse(categoryId, course), "Course Saved");
    }

    // 2. ADD TOPIC TO COURSE (Use this to add chapters)
    @PostMapping("/admin/courses/{courseId}/topics")
    public ApiResponse<Topic> addTopic(@PathVariable Long courseId, @RequestBody Topic topic) {
        Course course = courseRepository.findById(courseId).orElseThrow();
        topic.setCourse(course);
        return ApiResponse.success(topicRepository.save(topic), "Topic Added");
    }

    // 3. ADD CONTENT TO TOPIC (Use this to add text/code)
    @PostMapping("/admin/topics/{topicId}/content")
    public ApiResponse<ContentBlock> addContent(@PathVariable Long topicId, @RequestBody ContentBlock block) {
        Topic topic = topicRepository.findById(topicId).orElseThrow();
        block.setTopic(topic);
        return ApiResponse.success(contentBlockRepository.save(block), "Content Added");
    }

    // 4. GET FULL COURSE (The Student View)
    @GetMapping("/courses/{id}")
    public ApiResponse<Object> getCourse(@PathVariable Long id) {
        return ApiResponse.success(catalogService.getFullCourse(id), "Fetched");
    }
}