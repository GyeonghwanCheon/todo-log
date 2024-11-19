<p align="center">
<img src="https://cdn.imweb.me/thumbnail/20231120/9b1551ea109cf.png" width="40%" height="30%" title="px(픽셀) 크기 설정" alt="Calculator"></img>
</p>

# 📌 Todo-Log

---
>- ## ⚙ 구현 기능
>
> 
> 
> 
> 
> 
> 
---

## 👷‍♂️ API 명세서


User 등록



method : POST

URI : /users/signup

request : @RequestBody

    {
        "username": "user1",
        "password": "1234",
        "email": "aa@aa.com"
    }

response :

    //정상등록 예시 
    {   
        "id": 1,
        "username": "user1",
        "email": "aa@aa.com",
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

request :  -

response :

    //정상조회 예시 
    {   
        "username": "user1",
        "email": "aa@aa.com",
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

request :  -

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