# SpringBoot - 포토그램
------------------------------------------------------------
##### 자신의 일상을 공유할 수 있는 SNS입니다


## DB & 사용기술
![blog erd snapshot](https://github.com/mjk77777/SpirngBoot_Insta/assets/111689386/a8a49906-c1b8-4bde-aa5f-480b9dcd5b82)


사용기술
---
    spring security, spring boot, JPA HIBERNATE , MySQL
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
    - SecurityConfig 클래스
  ![image](https://github.com/mjk77777/SpirngBoot_Insta/assets/111689386/4f3d8347-81d5-42e4-8bfc-649151cba90f")


## 회원 프로필 페이지
![image](https://github.com/mjk77777/SpirngBoot_Insta/assets/111689386/fefd26da-cad6-4d26-ae85-3327798739d2)

    - 구독정보 탭
    ![image](https://github.com/mjk77777/SpirngBoot_Insta/assets/111689386/b21eb80c-5983-4339-b91e-0ade92c7c56b)

    - 사진 업로드 (태그 등록 가능)
   ![gg](https://github.com/mjk77777/SpirngBoot_Insta/assets/111689386/c6d875d1-0809-4d43-aa03-a29a7594fc7d) 




## 메인 페이지(피드) - 좋아요 + 스크랩 + 댓글기능
![image](https://github.com/mjk77777/SpirngBoot_Insta/assets/111689386/61b3c703-6b85-4c33-be49-f9e8ad996222)

    + 스크랩 탭에서 내가 '스크랩' 한 게시물 리스트들을 확인할 수 있다!


## 인기 게시물 - '좋아요'순으로
![image](https://github.com/mjk77777/SpirngBoot_Insta/assets/111689386/ed4c9959-1d1a-4ea0-b427-4a97a6dd57f6)


## 친구찾기 
  - jQuery Autocomplete 이용해 DB에서 ajax 통신으로 가져온 결과들을 보여줌
![image](https://github.com/mjk77777/SpirngBoot_Insta/assets/111689386/8e0244aa-043b-4581-ada2-351328546aa7)



