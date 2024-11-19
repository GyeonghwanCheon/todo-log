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
