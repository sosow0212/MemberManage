package com.example.membermanage.controller;

import com.example.membermanage.entity.Member;
import com.example.membermanage.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;

    // 멤버 정보 모두 불러오기
    @GetMapping("/member/list")
    public String memberList(Model model) {
        model.addAttribute("list", memberService.memberList());
        return "memberlist";
    }

    // 회원정보 등록
    @GetMapping("/member/write")
    public String memberWriteForm() {
        return "memberwrite";
    }

    // 회원정보 등록 처리
    @PostMapping("/member/writing")
    public String memberWriting(Member member, Model model) {
        memberService.write(member);
        return "redirect:/member/list";
    }
}
