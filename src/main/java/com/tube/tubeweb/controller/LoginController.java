package com.tube.tubeweb.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    private final OAuth2AuthorizedClientService authorizedClientService;

    public LoginController(OAuth2AuthorizedClientService authorizedClientService) {
        this.authorizedClientService = authorizedClientService;
    }

    @GetMapping("/")
    public String index() {
        return "index"; // index.html 반환
    }

    @GetMapping("/success")
    public String success(@AuthenticationPrincipal OidcUser user, OAuth2AuthenticationToken authentication, Model model) {
        // 사용자 정보 가져오기
        String username = user.getFullName();
        String email = user.getEmail();
        String accessToken = user.getIdToken().getTokenValue(); // 액세스 토큰 가져오기

        // Refresh Token 가져오기
        OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(
                authentication.getAuthorizedClientRegistrationId(),
                authentication.getName()
        );

        String refreshToken = (authorizedClient != null && authorizedClient.getRefreshToken() != null)
                ? authorizedClient.getRefreshToken().getTokenValue()
                : "Refresh Token 없음";

        // 뷰로 데이터 전달
        model.addAttribute("username", username);
        model.addAttribute("email", email);
        model.addAttribute("accessToken", accessToken);
        model.addAttribute("refreshToken", refreshToken);

        return "success"; // success.html 반환
    }
}
