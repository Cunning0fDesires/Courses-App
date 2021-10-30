<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<!DOCTYPE html>
<html>
<c:set var="title" value="Login" />
<head>
    <meta charset="UTF-8">
    <title>Courses</title>
</head>
<body>


<div align="center">
    <h1>Courses Login Form</h1>
    <form id="login_form" action="controller"  method="post">
        <input type="hidden" name="command" value="login"/>
        <table style="with: 100%">
            <tr>
                <td>Login</td>
                <td><input type="text" name="login" /></td>
            </tr>
            <tr>
                <td>Password</td>
                <td><input type="password" name="password" /></td>
            </tr>
        </table>
        <input type="submit" value="Submit" />
    </form>
</div>
</body>
</html>