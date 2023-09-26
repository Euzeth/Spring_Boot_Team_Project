09PROJECTPLAN
=

## ▶️ 개발 동기

##### 조원들이 각자 사용하는 음원 스트리밍 사이트의 장점을 통합하고 단점을 보완해서 구현해보기로 함.

<br/>

## ▶️ 개발 목표

##### 음원 오픈책 기능을 가진 스트리밍 사이트 구현
<br/>

## ▶️ 개발 일정
#### 2023-09-26 ~ 2023-09-27(02Day) : 요구사항분석 / 유스케이스 
#### 2023-09-28 ~ 2023-10-02(05Day) : 스타일가이드 / 스토리보드 / ERD / ClassDiagram / Sequence Diagram
#### 2023-10-03 ~ 2023-10-04(02Day) : 개발환경 구축(Github / Git / STS / Mysql / AWS ...)
#### 2023-10-04 ~ 2023-10-19(2Weeks) : 기능 구현


<br/>

## ▶️ 구성인원 

##### 윤치연(조장)  : Backend (웹기획, API문서정리, BackEnd 멤버 crud, 기능 구현 통합
##### 김예솔(조원1) : Backend (결제 crud, 게시판 crud), frontend(사이트 구성)
##### 최정기(조원2) : Backend (음악스트리밍 crud)
##### 이헌지(조원3) : Frontend(음악 페이지 구현 및 기능)
<br/>

## ▶️ 개발 환경(플랫폼)

##### OS : WINDOW Server 2022 base
##### CPU SPEC : I7 Intel 
##### RAM SPEC : 16GB SAMSUNG DDR4
##### DISK SPEC : 100GB SSD 

<br/>

## ▶️ IDE 종류

##### IntelliJ IDEA 2023-06
<br/>

## ▶️ Software 목록

##### IDE : IntelliJ IDEA 2023.2
##### SpringBoot 2.7.15
##### maven version 3.1.2
##### Git 2.40.1.windows.1
##### Mysql Server 8.0.33
##### Mysql Workbench 8.0.33
##### ...
<br/>

## ▶️ DevOps 

##### Amazon Web Service EC2(Deploy Server)
##### Amazon Web Service RDS(Remote Datebase Server)
##### Git & Github
##### Docker(Server Image)
##### Swagger(API Document)
##### Adobe XD
<br/>



## ▶️ 사용(or 예정) API

##### 카카오 결제시스템 API
##### 다음 주소 API API
##### OAuth2 로그인 API

<br/>

## ▶️ 기술스택

![JAVA](https://img.shields.io/badge/html5-%23E34F26.svg?style=for-the-badge&logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/css3-%231572B6.svg?style=for-the-badge&logo=css3&logoColor=white)
![JavaScript](https://img.shields.io/badge/javascript-%23323330.svg?style=for-the-badge&logo=javascript&logoColor=%23F7DF1E)
![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)
![AWS](https://img.shields.io/badge/AWS-%23FF9900.svg?style=for-the-badge&logo=amazon-aws&logoColor=white)


[참고 배지 싸이트] <br/>
https://badgen.net/ <br/>
https://shields.io/


<br/>

## ▶️ END POINT 

|END POINT|METHOD|DESCRIPTION|
|------|---|---|
|/board/list|GET|자유게시판 모든 게시물 목록 표시|
|/board/post|POST|자유게시판 게시물 첨부하기|
|/board/read|GET|자유게시판 게시물 1건 보기|
<br/>







