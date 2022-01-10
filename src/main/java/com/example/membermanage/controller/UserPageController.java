package com.example.membermanage.controller;

import com.example.membermanage.config.auth.PrincipalDetails;
import com.example.membermanage.entity.User;
import com.example.membermanage.service.UserPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class UserPageController {

    private final UserPageService userPageService;

    @GetMapping("/user/{id}")
    public String userProfile(@PathVariable("id") Integer id, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {

        System.out.println(principalDetails.getUser().getRole());

        if (principalDetails.getUser().getRole() == "ROLE_ADMIN") {
            // 등급이 admin 이면 어드민 페이지로 이동
            User newUser = userPageService.findById(principalDetails.getUser().getId());
            model.addAttribute("user", newUser);
            return "adminpage";
        }

        // 등급이 user 면 로그인한 유저의 프로필로 이동
        User newUser = userPageService.findById(principalDetails.getUser().getId());
        model.addAttribute("user", newUser);
        return "userpage";
    }
}
