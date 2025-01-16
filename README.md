# photogram - 인스타그램 클론 코딩
- jsp코드를 받아서 일부 수정하며, 백엔드 부분을 완성시켰습니다.

# 핵심 학습 내용
- 스프링부트, jpa ,시큐리티, oauth2, mariadb 설정
  - oauth2, 시큐리티 관련 PrincipalDetails, -DetailsService작성
- 클래스 설계, 엔티티 연관 관계 설정
- 컨트롤러의 메서드 및 쿼리 작성 - jsp 기반이지만 필요에 따라 비동기구현(RESTful)
- 리포지토리 쿼리 작성 - jpql, 네이티브 쿼리 
- 페이징 처리, 일대다 관계 해결 위해 batch_fetch_size 설정 이용
- 요청, 응답시 필요한 dto 작성 - 필요한 응답 요청만 받을 수 있도록 설계
- 파일 업로드(이미지)
- 예외 및 유효성 검사 처리
  - Bean Validation(+ 메세지 소스 이용)
  - 메서드별 예외 처리(ControllerAdvice , ExceptionHandler)
  - 커스텀 예외
  - script 코드 응답
- spring AOP 적용
- 제공된 jsp, js 일부 수정




---
# 기술 스택
- SpringBoot + gradle
- spring jpa data
- spring security
- MariaDB(MySQL)
- JSP
- oauth2
 
---
# 주요 컨트롤러
| SSR 컨트롤러    | 경로      | 설명                                      |
| --------------- | --------- | ----------------------------------------- |
| AuthController   | /auth    | 로그인, 회원가입 처리            |
| ImageController  | /image    | 이미지 업로드, 인기사진 처리                  |
| UserController  | /user    | 개인 프로필 페이지 관련 처리                          |
---
---
| api 컨트롤러    | 경로      | 설명                                      |
| --------------- | --------- | ----------------------------------------- |
| UserApiController  | /api/user    | 회원 정보 관리            |
| ImageApiController | /api/image    | 스토리 및 좋아요 기능 처리                  |
| SubscribeApiController   | /api/subscribe    | 구독 기능 처리                          |
| CommentApiController   | /api/commnet    | 댓글 기능 처리 |


---

# 요청 리스트
### ssr 메서드
| 설명                    | 메서드         | URL                  |
| ----------------------- | ----------------------------- | ------------------------|
|로그인페이지|get|/auth/signin|
|로그인|post|/auth/signin|
|로그아웃-시큐리티 기본|get|/logout|
|회원가입페이지|get|/auth/signup|
|회원가입 |post | /auth/signup|
|이미지 업로드페이지|get|/image/upload|
|이미지업로드|post|/image/upload|
|스토리 페이지(홈)|get|/, /image/story|
|인기게시글(탐색)|get|/image/popular|
|회원프로필|get|/user/{pageUserId}|
|업데이트창|get|/user/{id}/update|
-------------


### api 메서드
| 설명                    | 메서드         | URL                  |
| ----------------------- | ----------------------------- | ------------------------|
|회원수정|put|/api/user/{id}|
|구독자 리스트 가져오기|get|/api/user/{pageUserId}/subscriben|
|프로필 사진 변경|put|/api/user/{principallId}/profileImageUrl|
|스토리페이지가져오기(홈)|get|/api/image?page=${page}|
|좋아요 |post | /api/image/{imageId}/likes|
|좋아요 취소|delete|/api/image/{imageId}/likes|
|구독(팔로우)|post|/api/subscribe/{toUserId}|
|구독 취소(언팔로우)|delete|/api/subscribe/{toUserId}|
|댓글쓰기|post|/api/comment|
|댓글삭제|delete|/api/comment/{id}|

----

---


