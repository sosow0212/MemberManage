package com.example.membermanage.controller;

import com.example.membermanage.entity.Member;
import com.example.membermanage.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

        // ** 계산 영역 추후에 수정 **
        // 신청일자는 임의로 설정 - proto1
        member.setDay("2021-01-01");
        // 잔여일자도 임의로 설정 - proto1
        member.setLast(30);

        memberService.write(member);
        return "redirect:/member/list";
    }


    // 회원 상세 페이지
    @GetMapping("member/view") // localhost:8080/member/view?id=1
    public String boardView(Model model, Integer id) {
        model.addAttribute("member", memberService.memberView(id));
        return "memberview";
    }


    // 특정 게시물 삭제
    @GetMapping("/member/delete")
    public String memberDelete(Integer id) {
        memberService.memberDelete(id);
        return "redirect:/member/list";
    }


    // 게시글 수정 페이지
    @GetMapping("/member/modify/{id}")
    public String memberModify(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("member", memberService.memberView(id));
        return "membermodify";
    }


    // 게시글 수정 처리
    @PostMapping("/member/update/{id}")
    public String memberUpdate(@PathVariable("id") Integer id, Member member) {
        Member update = memberService.memberView(id);

        update.setGrade(member.getGrade());
        update.setName(member.getName());
        update.setType(member.getType());
        update.setMoney(member.getMoney());

        // 계산작업 - 추후에 진행
        String newDay = "2021-12-25"; // 임의로 신청일자 설정
        int newLast = 100; // 임의로 잔여일수 설정

        update.setDay(newDay);
        update.setLast(newLast);

        memberService.write(update);
        return "redirect:/member/list";

    }
}
