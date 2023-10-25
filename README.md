09PROJECTPLAN
=

## ▶️ 개발 동기

##### 조원들이 각자 사용하는 음원 스트리밍 사이트의 장점을 통합하고 단점을 보완해서 구현해보기로 함.

<br/>

## ▶️ 개발 목표

##### 음원 오픈챗 기능을 가진 스트리밍 사이트 구현
<br/>

## ▶️ 개발 일정
#### 2023-09-26 ~ 2023-09-27(02Day) : 요구사항분석 / 유스케이스 
#### 2023-09-28 ~ 2023-10-02(05Day) : 스타일가이드 / 스토리보드 / ERD / ClassDiagram 
#### 2023-10-03 ~ 2023-10-04(02Day) : 개발환경 구축(Github / Git / STS / Mysql / AWS ...)
#### 2023-10-04 ~ 2023-10-27(3Weeks) : 기능 구현


<br/>

## ▶️ 구성인원 

##### 윤치연(조장)  : 웹기획, API문서정리, BackEnd(멤버관련 crud 페이지 구현, 기능 구현 통합)
##### 김예솔(조원1) : BackEnd (결제관련 crud 페이지 구현, 게시판관련 crud 페이지 구현), frontEnd(사이트 구성)
##### 최정기(조원2) : BackEnd(음악스트리밍 관련 crud 페이지 구현)
##### 이헌지(조원3) : FrontEnd(음악 페이지 구현)
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

##### IDE : IntelliJ IDEA 2023.2.2
##### SpringBoot 2.7.15
##### Gradle
##### Git 2.40.1.windows.1
##### Mysql Server 8.0.33
##### Mysql Workbench 8.0.33
##### JPA
##### 
<br/>

## ▶️ DevOps 

##### Amazon Web Service EC2(Deploy Server)
##### Amazon Web Service RDS(Remote Datebase Server)
##### Git & Github
##### Docker(Server Image)
##### Adobe XD
<br/>



## ▶️ 사용(or 예정) API

##### 카카오 결제시스템 API
##### 다음 주소 API API
##### OAuth2 로그인 API

<br/>

## ▶️ 기술스택
##### FRONT END
![JAVA](https://img.shields.io/badge/html5-%23E34F26.svg?style=for-the-badge&logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/css3-%231572B6.svg?style=for-the-badge&logo=css3&logoColor=white)
![JavaScript](https://img.shields.io/badge/javascript-%23323330.svg?style=for-the-badge&logo=javascript&logoColor=%23F7DF1E)
##### BACK END
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)
##### DEV TOOLS
![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)
![AWS](https://img.shields.io/badge/AWS-%23FF9900.svg?style=for-the-badge&logo=amazon-aws&logoColor=white)
![SPRING BOOT](https://img.shields.io/badge/SPRING%20BOOT-%236DB33F.svg?style=for-the-badge&logo=spring-boot&logoColor=green)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJ%20IDEA-%23000000.svg?style=for-the-badge&logoSvg=(https://simpleicons.org/icons/intellijidea.svg))
![GitHub](https://img.shields.io/badge/GitHub-%23181717.svg?style=for-the-badge&logo=github)

[참고 배지 싸이트] <br/>
https://badgen.net/ <br/>
https://shields.io/


<br/>

## ▶️ END POINT 

|END POINT|METHOD|DESCRIPTION|
|------|---|---|

메인관련 end point <br/>
| /index     |  get  |  메인 페이지          | <br/>
| /indexlog  |  get  |  로그인 후 메인 페이지 | <br/>

노래관련 end point <br/>
| /song    |  get  |  노래 재생 기능            | <br/>
| /search  |  get  |  노래 검색 페이지          | <br/>
| /top100  |  get  |  인기순위 100곡 소개 페이지 | <br/>

멤버관련 end point <br/>
| /member/join    |  post  |  회원가입 페이지      | <br/>
| /member/login   |  get   |  로그인 페이지        | <br/>
| /member/user    |  get   |  유저 정보 페이지     | <br/>
| /member/member  |  get   |  유저 관리 페이지     | <br/>
| /member/update  |  post  |  유저 정보 수정페이지 | <br/>

멤버십관련 end point <br/>
| /membershipU            |  get   |  유저|멤버십가입 페이지|   | <br/>
| /membershipM            |  get   |  멤버|멤버십관리 페이지|   | <br/>
| /membership_selectId    |  get   |  개별 유저 ID로 조회      | <br/>
| /membership_selectCode  |  get   |  개별 유저 CODE로 조회    | <br/>
| /membership_selectDate  |  get   |  개별 유저 종료일자로 조회 | <br/>
| /membership_delete      |  post  |  개별 유저 삭제           | <br/>
| /membership/request1    |  get   |  멤버십 정기결제 1        | <br/>
| /membership/request2    |  get   |  멤버십 정기결제 2        | <br/>
| /membership/success1    |  get   |  멤버십 정기결제 1 성공    | <br/>
| /membership/success2    |  get   |  멤버십 정기결제 2 성공    | <br/>
| /membership/cancel      |  get   |  멤버십 결제 취소         | <br/>
| /membership/fail        |  get   |  멤버십 결제 실패         | <br/>

공지사항관련 end point <br/>
| /notice/list    |  get   |  공지사항 모든 게시물 목록 표시 | <br/>
| /notice/post    |  get   |  공지사항 게시물 작성          | <br/>
| /notice/read    |  get   |  공지사항 게시물 1건 읽기      | <br/>
| /notice/update  |  post  |  공시사항 게시물 수정          | <br/>
 
qna관련 end point <br/>
| /qna/list    |  get   |  QnA 모든 게시물 목록 표시 | <br/>
| /qna/post    |  get   |  QnA 게시물 작성          | <br/>
| /qna/read    |  get   |  QnA 게시물 1건 읽기      | <br/>
| /qna/update  |  post  |  QnA 게시물 수정          | <br/>
<br/> 







