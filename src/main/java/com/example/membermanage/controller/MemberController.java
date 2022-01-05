package com.example.membermanage.controller;

import com.example.membermanage.entity.Member;
import com.example.membermanage.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

        // 회원 리스트 모두 불러오기
        List<Member> allMembers = memberService.memberList();

        // 멤버 잔여일수 구하기
        List<Member> members = memberService.getDay(allMembers);


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
    public String memberWriting(Member beforeMember, Model model) {

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
        Member update = memberService.memberView(id); // 아이디로 멤버(수정된) 찾고

        Member after = memberService.setBeforeUpdating(member, update);


        memberService.write(after);
        return "redirect:/member/list";
    }



    // 멤버 기간 연장
    @GetMapping("/member/plus")
    public String memberPlus(Model model) {
        return "plus";
    }

    @PostMapping("/member/plusPro")
    public String memberPlusPro(int plusDate) {
        List<Member> members = memberService.memberList();

        for(Member member: members) {
            String endDay = member.getEnd(); // 멤버의 종료일 구하기
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            Calendar cal = Calendar.getInstance();
            Date regDate = null;
            try {
                regDate = format.parse(endDay);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            cal.setTime(regDate);

            cal.add(Calendar.DATE, plusDate);
            String dateValue = format.format(cal.getTime());

            member.setEnd(dateValue);
            memberService.write(member);


            // 오류발생 데이터는 정상적이지만
            // setEnd(dateValue) 를 하면 업데이트가 안됨!
            // 오류 해결 -> memberService.write(member) 을 해줘야 저장됨
        }
        return "redirect:/member/list";
    }
}
