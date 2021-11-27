package com.example.membermanage.controller;

import com.example.membermanage.entity.Member;
import com.example.membermanage.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

        // 신청일자 셋팅
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateString = format.format(date);
        member.setDay(dateString); // 신청일자 셋팅


        // 종료일자 셋팅
        Calendar cal = Calendar.getInstance(); // 날짜 계산을 위해 Calendar 추상클래스 선언 및 getInstance() 메서드 사용
        cal.setTime(date);
        cal.add(Calendar.DATE, member.getLast());
        String dateString2 = format.format(cal.getTime());
        member.setEnd(dateString2);


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


//        // 신청일자 설정
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//        String dateString = format.format(date);
//        update.setDay(dateString);
//        // 신청일자 셋팅 완료

        // 신청일자는 수정하면 안됨 (수정하면 수정한 날짜로 변경되기 때문)


        // 종료일자 설정
        Calendar cal = Calendar.getInstance(); // 날짜 계산을 위해 Calendar 추상클래스 선언 및 getInstance() 메서드 사용
        cal.setTime(date);
        cal.add(Calendar.DATE, member.getLast());
        String dateString2 = format.format(cal.getTime());
        update.setEnd(dateString2);
        // 종료일자 셋팅 완료

        memberService.write(update);
        return "redirect:/member/list";

    }
}
