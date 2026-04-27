package com.codenexus.app.features.catalog.repository;

import com.codenexus.app.features.catalog.entity.ContentBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ContentBlockRepository extends JpaRepository<ContentBlock, Long> {
    // Find content blocks for a topic ordered by their sequence
    List<ContentBlock> findByTopicIdOrderByOrderIndexAsc(Long topicId);
}