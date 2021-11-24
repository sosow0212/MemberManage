package com.example.membermanage.service;

import com.example.membermanage.entity.Member;
import com.example.membermanage.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    // 회원정보 작성 및 저장
    public void write(Member member) {
        memberRepository.save(member);
    }

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

}

