# 스프링부트 CRUD 회원 관리 시스템 만들기
<hr>

## 프로젝트 설명
- 스프링부트를 이용하여 만든 회원 관리 시스템입니다.
- 운동시설에서 회원 관리를 수기로 하시는 것을 보고 만들어 봤습니다.
- 회원정보를 입력하고 등록을하면 등급과 종목, 등급별 할인액이 적용되고, 자동으로 남은 일자와 만료기간등을 표시해줍니다.
- 회원관리를 하기 쉽도록 일괄 날짜 증가 및 감소의 기능이 있고, 
- 업로드, 읽기, 수정, 삭제의 기능을 기본으로두고 JPA를 이용해서 Repository를 만들었습니다.

### 사용기술
- 사용기술 : Spring boot, MariaDB

### MariaDB - Entity
- Id(PK), grade, name, type, day, endDay, last, money

### 프로젝트 정보 및 설명
- 1인 프로젝트로 진행하였습니다.
- 제작기간 : 2021-11-24 ~ 

### 업데이트 내용
- 스프링부트 CRUD 설계 및 Thymeleaf를 이용하여 간단한 프론트엔드 구현 (2021-11-24)
- 등록일과 잔여일 계산하여 남은 일자 표시 (2021-11-26)
- 부트스트랩을 이용한 프론트엔드 설계 (2021-11-28)

### 문제점
- 잔여일수를 현재 날짜 기준으로 줄여야한다.(미구현)
- 전화번호 및 주소 DB에 추가(미구현)
