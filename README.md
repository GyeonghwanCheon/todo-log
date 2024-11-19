<p align="center">
<img src="https://cdn.imweb.me/thumbnail/20231120/9b1551ea109cf.png" width="40%" height="30%" title="px(픽셀) 크기 설정" alt="Calculator"></img>
</p>

# 📌 Todo-Log

---
>- ## ⚙ 구현 기능
>1. 유저(user) CRUD 기능
>2. 피드(feed) CRUD 기능
>3. 댓글(comment) CRUD 기능
>4. 좋아요(like) 기능
>5. 유저 로그인 기능
>6. 친구 관리 기능
>7. 비밀번호 암호화 기능
>8. 피드 카테고리 기능
>9. 프로필 기능
> 

---

## 👷‍♂️ API 명세서


User 등록

method : POST

URI : /users/signup

request : @RequestBody

    {
        "nickname": "user1",
        "email": "aa@aa.com",
        "status_ms": "상태메세지",
        "mbti" : "infp",
        "password": "1234"
    }

response :

    //정상등록 예시 
    {   
        "id": 1,
        "nickname": "user1",
        "email": "aa@aa.com",
        "status_ms": "상태메세지",
        "mbti" : "infp",
        "password": "$2a$04$SAdzROzAX./dInQwejqhheaaA9G.uWzj2sv0S20TSWf87/nal/VAS"
    }

    //실패 예시
    {
        "password": "password 은 1~10 글자여야 합니다.",
        "email": "이메일 형식이 올바르지 않습니다.",
        "username": "username 은 1~5 글자여야 합니다."
    }

status code :

201(Created) : 정상등록

400(Bad_Request) : 실패

---

유저 조회

method : GET

URI : /users/{id}

request :  @RequestParam

response :

    //정상조회 예시 
    {   
        "nickname": "user1",
        "email": "aa@aa.com",
        "status_ms": "상태메세지",
        "mbti" : "infp",
        "password": "$2a$04$CJYd4Qr8n3/ol9KKLPTYyukHvCVEevOKpH.vrYI7R.IuiTAmZcMny"
    }

    //실패 예시
    {
        "timestamp": "2024-11-15T05:57:49.734068",
        "status": 404,
        "error": "NOT_FOUND",
        "code": "USER_NOT_FOUND",
        "message": "해당 id 로 인한 유저정보를 찾을 수 없습니다"
    }

status code :

200(OK) : 정상조회

404(NOT_FOUND) : 실패

---

유저 삭제


method : DELETE

URI : /users/{id}

request :  @RequestParam

response :

    //정상 삭제 예시
    1

    //실패 예시
    {
        "timestamp": "2024-11-15T06:11:38.465995",
        "status": 401,
        "error": "UNAUTHORIZED",
        "code": "UNAUTHORIZED_USER",
        "message": "권한이 없습니다. 해당유저만 가능합니다."
    }

status code :

200(OK) : 정상삭제

401(UNAUTHORIZED) : 실패

---


유저 로그인

method : POST

URI : /users/login

request :  -

response :

    //정상 로그인 예시
    {
        "email" : "aa@aa.com",
        "password" : "1234"
    }

    //실패 예시
    {
        "timestamp": "2024-11-15T11:43:23.341849",
        "status": 401,
        "error": "UNAUTHORIZED",
        "code": "UNAUTHORIZED_PASSWORD",
        "message": "password 가 일치하지 않습니다."
    }

status code :

200(OK) : 정상 로그인

401(UNAUTHORIZED) : 실패

---


유저 로그아웃

method : POST

URI : /users/logout

request :  -

response :

    //정상 session삭제 예시
    logout

    //실패 예시
    session not exist


status code :

200(OK) : 정상

---

피드(feed) 등록

method : POST

URI : /feeds

request : @RequestBody

    {
        "userid" : 1,
        "title" : "제목",
        "detail" : "내용"
    }

response : 

    //정상 등록 예시
    
    {
        "id": 1,
        "userid" : 1,
        "title": "제목",
        "detail": "내용"
    }

    //실패 예시
    {
        "detail": "detail 은 20글자 이내여야 합니다.",
        "title": "title 은 10글자 이내여야 합니다.",
    }

status code :

200(OK) : 정상

400(BAD_REQUEST) : 실패

----


피드(feed) 단건 조회

method : GET

URI : /feeds/{id}

request :   @RequestParam



response :

    //성공 예시
    
    {
        "id": 1,
        "userid" : 1,
        "title": "제목",
        "detail": "내용",
        "categoryname" : "test"
    }

    //실패 예시
    {
        "detail": "detail 은 20글자 이내여야 합니다.",
        "title": "title 은 10글자 이내여야 합니다.",
    }

status code :

200(OK) : 정상

400(BAD_REQUEST) : 요청 실패

404(NOT_FOUND) : id 조회 실패

---

피드(feed)  paging 조회

method : GET

URI : /feeds/pages

request : @RequestParam , 

@Pageable(size=페이지 사이즈 , page : 현재 페이지 번호(0부터 시작) , sort : 정렬 기준이 될 필드와 방향(ASC 또는 DESC)),

@RequestParams 없을시 @PageableDefault



response :

    //성공 예시

    [
        {
            "createdAt": "2024-11-15T12:59:58.938111",
            "modifiedAt": "2024-11-15T12:59:58.938111",
            "id": 6,
            "user": {
                "createdAt": "2024-11-15T12:50:01.623678",
                "modifiedAt": "2024-11-15T12:50:01.623678",
                "id": 1,
                "nickname": "aaa",
                "email": "aa@aa.com"
            },
            "title": "제목",
            "detail": "할일",
            "likecount" : 11,
            "categoryname" : "test"
        },

            '''

    ]

    //data 없을시 (빈배열 반환)
    
    []

status code :

200(OK) : 정상

400(BAD_REQUEST) : 요청 실패

404(NOT_FOUND) : id 조회 실패

---

피드(feed)  특정 유저 조회

method : GET

URI : /feeds/user/{user_id}

request : @RequestParam 

response :

    //성공 예시

    [
        {
            "createdAt": "2024-11-15T12:59:58.938111",
            "modifiedAt": "2024-11-15T12:59:58.938111",
            "id": 6,
            "userid": 1,
            "title": "제목",
            "detail": "할일",
            "likecount" : 11,
            "categoryname" : "test"
        },

            '''

    ]

    //data 없을시 (빈배열 반환)
    
    []

status code :

200(OK) : 정상

400(BAD_REQUEST) : 요청 실패

404(NOT_FOUND) : id 조회 실패

---

피드(feed) 수정

method : PUT

URI : /feeds/{id}

request : @RequestBody

    {
        "title" : "제목",
        "detail" : "내용",
        "categoryname" : "test"
    }

response :

    //정상 등록 예시
    
    {
        "id": 1,
        "title": "제목",
        "detail": "내용",
        "categoryname" : "test"
    }

    //실패 예시
    {
        "title": "title 은 10글자 이내여야 합니다.",
        "detail": "detail 은 20글자 이내여야 합니다."
    }

status code :

200(OK) : 정상

400(BAD_REQUEST) : 요청 실패
401(UNAUTHORIZED) : 권한 실패

---

피드(feed) 단건 삭제

method : GET

URI : /feeds/{id}

request :   @RequestParam



response :

    //성공 예시
    
    1

    //실패 예시
    {
        "timestamp": "2024-11-15T12:52:52.475839",
        "status": 404,
        "error": "NOT_FOUND",
        "code": "SCHEDULE_NOT_FOUND",
        "message": "해당 id 로 인한 피드정보를 찾을 수 없습니다"
    }

status code :

200(OK) : 정상

404(NOT_FOUND) : id 조회 실패

---


