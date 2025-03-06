package com.tube.tubeweb.controller;

import com.tube.tubeweb.model.Video;
import com.tube.tubeweb.service.VideoService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/video")
public class VideoController {

    private final VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @PostMapping("/upload")
    public String uploadVideo(@AuthenticationPrincipal OidcUser user, @RequestParam("file") MultipartFile file) throws IOException {
        videoService.uploadVideo(user.getFullName(), file);
        return "redirect:/success";
    }

    @GetMapping("/list")
    public String getVideoList(@AuthenticationPrincipal OidcUser user, Model model) {
        List<Video> videos = videoService.getVideosByUser(user.getFullName());
        model.addAttribute("videos", videos);
        return "success";
    }

    @GetMapping("/play/{id}")
    public ResponseEntity<byte[]> getVideo(@PathVariable Long id) {
        Optional<Video> videoOptional = videoService.getVideoById(id);

        if (videoOptional.isPresent()) {
            Video video = videoOptional.get();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", video.getContentType());
            return new ResponseEntity<>(video.getData(), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteVideo(@PathVariable Long id) {
        videoService.deleteVideo(id);
        return "redirect:/success";
    }
}
