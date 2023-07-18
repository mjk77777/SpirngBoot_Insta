# SpringBoot - 포토그램
------------------------------------------------------------
##### 자신의 일상을 공유하는 SNS입니다


## DB & 사용기술
![image](https://user-images.githubusercontent.com/55027765/101130369-b2c81600-3646-11eb-908a-574f1a3c43c3.png)
![image](https://user-images.githubusercontent.com/55027765/101130487-e86cff00-3646-11eb-8788-96dafd645e0d.png)


## MySQL 세팅

1. MySQL 데이터베이스 및 사용자 생성

- create user 'cos'@'%' identified by 'cos1234';
- GRANT ALL PRIVILEGES ON *.* TO 'cos'@'%';
- create database photogram;
- use photogram;


## 맞팔 쿼리, 좋아요 카운트 쿼리

1. 좋아요 수 쿼리 (스칼라 서브쿼리)<br/>
![blog](https://postfiles.pstatic.net/MjAyMDA4MjRfMTYw/MDAxNTk4MjM5NzUwMjMy.VZH7JMI_P8AwMhJCSXxHfFSQq8uaJ7w6ufEjsvlae44g.mJoyoc69PAY-kHK5jeQW2JtrpOUA6i_qQFGcpqeHNNAg.PNG.getinthere/Screenshot_49.png?type=w773)

```sql
select
i.id,
i.caption,
(select count(*) from likes l where l.imageId = i.id) as 좋아요
from image i;
```

2. 맞팔 유무 쿼리 (Left outer Join 과 스칼라 서브쿼리)<br/>
![blog](https://postfiles.pstatic.net/MjAyMDA4MjRfMjAy/MDAxNTk4MjM3ODE4MjUw.pDKhnS9IE1usJqVXVVo9iNJOo5FPbC7YDOLBP4IwCQIg.3tTT-qYv5b27K9AMP-dZP1YauCvD-7MJLm_j6FvIvJkg.PNG.getinthere/Screenshot_48.png?type=w773)

```sql
select f1.id, f1.fromUserId, f1.toUserId, f1.createDate,
if(f2.fromUserId is null, false, true) "matpal"
from follow f1 left outer join follow f2
on f1.fromUserId = f2.toUserId and f1.toUserId = f2.fromUserId
order by f1.id;


select f1.id, f1.fromUserId, f1.toUserId,
f1.createDate,
(
select 1 from follow f2
where f1.fromUserId = f2.toUserId
and f1.toUserId = f2.fromUserId
) "matpal"
from follow f1;
```

## 메인 페이지(피드) - 좋아요 + 스크랩 + 댓글기능
![image](https://user-images.githubusercontent.com/55027765/101133580-57008b80-364c-11eb-8bdd-917532e5e0b3.png)

    스크랩 탭에서 내가 '스크랩' 한 게시물 리스트들을 확인할 수 있다!


## 인기 게시물 - '좋아요' 순대
![image](https://user-images.githubusercontent.com/55027765/101133295-caee6400-364b-11eb-8463-7360c90f32a7.png)



## 회원 프로필 페이지
![image](https://user-images.githubusercontent.com/55027765/101133678-8e6f3800-364c-11eb-8769-c7bba4c863c6.png)



## 친구찾기 - JQuery Autocomplete
![image](https://user-images.githubusercontent.com/55027765/101133893-e9089400-364c-11eb-8829-187bea7f4481.png)


