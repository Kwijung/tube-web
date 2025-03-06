package com.tube.tubeweb.service;

import com.tube.tubeweb.model.Video;
import com.tube.tubeweb.repository.VideoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class VideoService {

    private final VideoRepository videoRepository;

    public VideoService(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    public void uploadVideo(String username, MultipartFile file) throws IOException {
        Video video = new Video();
        video.setUsername(username);
        video.setFilename(file.getOriginalFilename());
        video.setContentType(file.getContentType());
        video.setData(file.getBytes());
        videoRepository.save(video);
    }

    public List<Video> getVideosByUser(String username) {
        return videoRepository.findByUsername(username);
    }

    // ✅ 새로운 메서드 추가 (ID로 동영상 조회)
    public Optional<Video> getVideoById(Long id) {
        return videoRepository.findById(id);
    }

    public void deleteVideo(Long videoId) {
        videoRepository.deleteById(videoId);
    }
}
