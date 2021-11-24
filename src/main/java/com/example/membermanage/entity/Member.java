package com.example.membermanage.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String grade; // 회원 등급

    private String name; // 이름

    private String type; // 등록 종목

    private int day; // 등록 일 수 (ex: 90일)

    private int last; // 잔여일수

    private String startDay; // 가입일자

    private String endDay; // 종료일자
}
