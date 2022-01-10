package com.example.membermanage.controller;

import com.example.membermanage.config.auth.PrincipalDetails;
import com.example.membermanage.entity.Member;
import com.example.membermanage.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public String memberList(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {

        // 회원 리스트 모두 불러오기
        List<Member> allMembers = memberService.memberList();

        // 멤버 잔여일수 구하기
        List<Member> members = memberService.getDay(allMembers);

        model.addAttribute("list", memberService.memberList());
        model.addAttribute("signinUser", principalDetails.getUser());
        System.out.println(principalDetails.getUser().getName());
        return "memberlist";
    }



    // 회원정보 등록
    @GetMapping("/member/write")
    public String memberWriteForm() {

        return "memberwrite";
    }



    // 회원정보 등록 처리
    @PostMapping("/member/writing")
    public String memberWriting(Member beforeMember) {

        // ** 계산 영역 추후에 수정 **
        Member member = memberService.setBeforeWriting(beforeMember);

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
        // 매개변수로 들어온 member는 수정된 내용이 담긴 member
        // 밑에 Member update 변수는 수정되기 전 기존 멤버이다.
        // update(기존멤버)에 매개변수로 온 member를 덮어씌우기 위해서 하나 더 만들었음

        Member update = memberService.memberView(id); // 아이디로 기존멤버(수정되기 전) 찾고
        Member after = memberService.setBeforeUpdating(member, update); //


        memberService.write(after);
        return "redirect:/member/list";
    }



    // 멤버 기간 연장 GetMapping
    @GetMapping("/member/plus")
    public String memberPlus() {
        return "plus";
    }


    // 멤버 기간 연장 PostMapping
    @PostMapping("/member/plusPro")
    public String memberPlusPro(int plusDate) {
        List<Member> members = memberService.memberList(); // 전체 멤버를 받아옴
        memberService.plusDay(members, plusDate); // 전체 멤버의 잔여일수를, plusDate만큼 증가시킴
        return "redirect:/member/list";
    }
}
