package com.codenexus.app.features.catalog.repository;

import com.codenexus.app.features.catalog.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    
    // Optimized query to fetch full hierarchy in one go
    @Query("SELECT c FROM Course c LEFT JOIN FETCH c.topics t LEFT JOIN FETCH t.contentBlocks WHERE c.id = :id")
    Optional<Course> findFullCourseDetails(@Param("id") Long id);
}