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
import java.util.Calendar;
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


    ///////////////////////////////////////////////
    // 기능 구현 함수들 //

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
        for (Member member : members) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            String endDate = member.getEnd();
            String today = todayString;

            try {
                Date endDateDate = dateFormat.parse(endDate);
                Date todayDate = dateFormat.parse(today);

                long diffDay = (endDateDate.getTime() - todayDate.getTime()) / (24 * 60 * 60 * 1000);
                int lastDay = (int) diffDay;
                if (lastDay < 0) {
                    lastDay = 0; // 잔여일수가 0일 미만이라면 -x 일로 표기되는게 아니라 0일로 표기된다.
                }

                member.setLast(lastDay); // 잔여일수 삽입
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        // 잔여일수 0일 남으면 자동 회원 삭제
        for (Member member : members) {
            if (member.getLast() == 0) {
                memberRepository.deleteById(member.getId());
            }
        }

        return members;
    }


    public Member setBeforeWriting(Member member) {
        // 신청일자 셋팅
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = format.format(date);
        member.setDay(dateString); // 신청일자 셋팅


        // 종료일자 셋팅
        Calendar cal = Calendar.getInstance(); // 날짜 계산을 위해 Calendar 추상클래스 선언 및 getInstance() 메서드 사용
        cal.setTime(date);
        cal.add(Calendar.DATE, member.getLast());
        String dateString2 = format.format(cal.getTime());
        member.setEnd(dateString2);


        ////// 할인율 설정
        /**
         * 할인율 셋팅
         * 브론즈    0%
         * 실버     5%
         * 골드     10%
         * 플래티넘  15%
         * 다이아   20%
         */

        String memberGrade = member.getGrade();  // 멤버의 등급을 받아옴
        double discountPriceDouble = 0; // 멤버로 할인된 가격

        switch (memberGrade) {
            case "브론즈":
                discountPriceDouble = member.getMoney();
                break;
            case "실버":
                discountPriceDouble = member.getMoney() * 0.95;
                break;
            case "골드":
                discountPriceDouble = member.getMoney() * 0.9;
                break;
            case "플래티넘":
                discountPriceDouble = member.getMoney() * 0.85;
                break;
            case "다이아":
                discountPriceDouble = member.getMoney() * 0.8;
                break;
        }

        int discountPrice = (int) discountPriceDouble;
        member.setMoney(discountPrice);

        return member;
    }




    public Member setBeforeUpdating(Member member, Member update) {
        /**
         * 할인율 셋팅
         * 브론즈    0%
         * 실버     5%
         * 골드     10%
         * 플래티넘  15%
         * 다이아   20%
         */

        String memberGrade = member.getGrade();  // 멤버의 등급을 받아옴
        double discountPriceDouble = 0; // 멤버로 할인된 가격
        switch (memberGrade) {
            case "브론즈" :
                discountPriceDouble = member.getMoney();
                break;
            case "실버" :
                discountPriceDouble = member.getMoney() * 0.95;
                break;
            case "골드" :
                discountPriceDouble = member.getMoney() * 0.9;
                break;
            case "플래티넘" :
                discountPriceDouble = member.getMoney() * 0.85;
                break;
            case "다이아" :
                discountPriceDouble = member.getMoney() * 0.8;
                break;
        }
        int discountPrice = (int)discountPriceDouble;

        update.setGrade(member.getGrade());
        update.setName(member.getName());
        update.setType(member.getType());
        update.setMoney(discountPrice);
        update.setAddress(member.getAddress());
        update.setPhone(member.getPhone());


//        // 신청일자 설정
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
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

        return update;
    }



    public void plusDay(List<Member> members, int plusDate) {
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
            memberRepository.save(member);


            // 오류발생 데이터는 정상적이지만
            // setEnd(dateValue) 를 하면 업데이트가 안됨!
            // 오류 해결 -> memberService.write(member) 을 해줘야 저장됨
        }
    }

}

