package com.example.membermanage.service;

import com.example.membermanage.entity.Member;
import com.example.membermanage.repository.MemberRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;


    // 회원정보 작성 및 저장
    public void write(Member member) {

        memberRepository.save(member);
    }

    // CRUD
    // -> Create = save()
    // -> Read = findAll(), findById()




    // 회원 리스트 모두 불러오기
    public List<Member> memberList() {
        return memberRepository.findAll();
    }


    // 특정 회원 정보 불러오기
    public Member memberView(Integer id) {

        return memberRepository.findById(id).get();
    }


    // 특정 게시글 삭제
    public void memberDelete(Integer id) {

        memberRepository.deleteById(id);
    }



    // 멤버 잔여일수 구하기
    public List<Member> getDay(List<Member> members) {

        /**
         * 잔여일수 구하는 로직
         * 잔여일수는 마감일자 - 오늘일자 이다.
         * 즉 신청일자가 2021-11-27일이고 마감일자가 2021-11-29 이라면
         * 잔여일수는 2일이다.
         */
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate now = LocalDate.now();
        String todayString = now.format(formatter); // 오늘 날짜 String화

        // 끝나는 날 - 오늘의 날짜 == 구하고자 하는 남은날짜
        for(Member member: members) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            String endDate = member.getEnd();
            String today = todayString;

            try {
                Date endDateDate = dateFormat.parse(endDate);
                Date todayDate = dateFormat.parse(today);

                long diffDay = (endDateDate.getTime() - todayDate.getTime()) / (24*60*60*1000);
                int lastDay = (int)diffDay;
                if(lastDay < 0) {
                    lastDay = 0; // 잔여일수가 0일 미만이라면 -x 일로 표기되는게 아니라 0일로 표기된다.
                }

                member.setLast(lastDay); // 잔여일수 삽입
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        // 잔여일수 0일 남으면 자동 회원 삭제
        for(Member member: members) {
            if(member.getLast() == 0) {
                memberRepository.deleteById(member.getId());
            }
        }

        return members;
    }



}

