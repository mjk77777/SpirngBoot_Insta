# SpringBoot - 포토그램
------------------------------------------------------------
##### 자신의 일상을 공유할 수 있는 SNS입니다


## DB & 사용기술
![image](https://github.com/mjk77777/SpirngBoot_Insta/assets/111689386/465ded25-ae43-4a25-b81f-2b7d69e95a87) 


사용기술
---
    spring security, spring boot, JPA HIBERNATE , MySQL, JSP
    jQuery, Lombok
    Apache Tomcat, Apache Maven
---
## MySQL 세팅

1. MySQL 데이터베이스 및 사용자 생성

- create user 'cos'@'%' identified by 'cos1234';
- GRANT ALL PRIVILEGES ON *.* TO 'cos'@'%';
- create database photogram;
- use photogram;


## OAuth2 라이브러리를 이용한 구글 로그인
![image](https://github.com/mjk77777/SpirngBoot_Insta/assets/111689386/8b9ff84e-8718-46af-a604-fd16aec0c035)

    - SecurityConfig 클래스
![image](https://github.com/mjk77777/SpirngBoot_Insta/assets/111689386/02d78e4c-30cc-4fc7-84ce-3e89bdb83771)


## 회원 프로필 페이지
![image](https://github.com/mjk77777/SpirngBoot_Insta/assets/111689386/ae5faf57-4bb0-4546-b216-c8782dc090b2)

    - 구독정보 탭
   ![image](https://github.com/mjk77777/SpirngBoot_Insta/assets/111689386/746517e4-422e-4c8c-acf0-1f116f40ea45) 

    - 사진 업로드 (태그 등록 가능)
   ![image](https://github.com/mjk77777/SpirngBoot_Insta/assets/111689386/1abff143-b5a3-4ae3-af71-c834980dc4b4)



## 메인 페이지(피드) - 좋아요 + 스크랩 + 댓글기능
![image](https://github.com/mjk77777/SpirngBoot_Insta/assets/111689386/c1c2b6be-f983-4a7e-88fb-15a24019e9fe)

    + 스크랩 탭에서 내가 '스크랩' 한 게시물 리스트들을 확인할 수 있다!


## 인기 게시물 - '좋아요'순으로
![image](https://github.com/mjk77777/SpirngBoot_Insta/assets/111689386/eefed388-b692-4261-8879-75520096cb21)


## 친구찾기 
  - jQuery Autocomplete 이용해 DB에서 ajax 통신으로 가져온 결과들을 보여줌
![image](https://github.com/mjk77777/SpirngBoot_Insta/assets/111689386/323ef265-74e8-4460-9369-edc737ebb8f7)




