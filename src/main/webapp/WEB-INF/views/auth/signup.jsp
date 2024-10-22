<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Photogram</title>
    <link rel="stylesheet" href="/css/style.css">
    <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css"
          integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous"/>
</head>

<body>
<div class="container">
    <main class="loginMain">
        <!--회원가입섹션-->
        <section class="login">
            <article class="login__form__container">

                <!--회원가입 폼-->
                <div class="login__form">
                    <!--로고-->
                    <h1><img src="/images/logo.jpg" alt=""></h1>
                    <!--로고end-->

                    <!--회원가입 인풋-->
                    <form:form
                            modelAttribute="signupDto"
                            class="login__input" action="/auth/signup" method="post">


                        <form:input path="username" type="text" name="username" placeholder="아이디" required="required"
                                    maxlength="30"/>
                        <form:errors path="username" class="form_errors" />

                        <form:input path="password" type="password" name="password" placeholder="패스워드"
                                    required="required"/>
                        <form:errors path="password" class="form_errors" />

                        <form:input path="email" type="email" name="email" placeholder="이메일" required="required"/>
                        <form:errors path="email" class="form_errors" />
                        <form:input path="name" type="text" name="name" placeholder="이름" required="required"/>
                        <form:errors path="name" class="form_errors" />
                        <button>가입</button>
                    </form:form>
                    <!--회원가입 인풋end-->
                </div>
                <!--회원가입 폼end-->

                <!--계정이 있으신가요?-->
                <div class="login__register">
                    <span>계정이 있으신가요?</span>
                    <a href="/auth/signin">로그인</a>
                </div>
                <!--계정이 있으신가요?end-->

            </article>
        </section>
    </main>
</div>
</body>

</html>