package com.tube.tubeweb.repository;

import com.tube.tubeweb.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VideoRepository extends JpaRepository<Video, Long> {
    List<Video> findByUsername(String username); // 특정 사용자의 동영상 목록 조회
}
