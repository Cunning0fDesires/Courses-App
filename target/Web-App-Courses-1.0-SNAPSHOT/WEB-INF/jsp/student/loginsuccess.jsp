<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>

<!DOCTYPE html>
<html>
<head>
    <script
            src="https://code.jquery.com/jquery-3.6.0.min.js"
            integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
            crossorigin="anonymous">
    </script>

    <meta charset="UTF-8">
    <title><fmt:message key="label.account"/></title>
</head>
<body>

<script>
    $(document).ready(function() {
        $("#finishedbutton").click(function () {
            $("#regtable").hide();
            $("#ongoingtable").hide();
            $("#finishedtable").show();
        });
        $("#regbutton").click(function () {
            $("#finishedtable").hide();
            $("#ongoingtable").hide();
            $("#regtable").show();
        });
        $("#ongoingbutton").click(function () {
            $("#finishedtable").hide();
            $("#regtable").hide();
            $("#ongoingtable").show();
        });
    });
</script>
<div align="center">
    <h1><fmt:message key="label.welcome.user"/></h1>
</div>

<a href="<%=request.getContextPath()%>/controller?command=logout"><button class="GFG" style="position: absolute; top:25px; right:20px; height:30px;width:70px">
    <fmt:message key="button.logout"/>
</button></a>

<a href="<%=request.getContextPath()%>/controller?command=search"><button class="GFG" style="position: absolute; top:25px; right:150px; height:30px;width:70px">
    <fmt:message key="button.search"/>
</button></a>

<form id="changeLanguage" action="controller" method="post">
    <input type="hidden" name="command" value="changeLanguage"/>
    <select name="lang">
        <c:forEach items="${sessionScope.languages}" var="language">
            <option value="${language.shortName}" ${lang == language.shortName ? 'selected' : ''}><c:out value="${language.fullName}"/></option>
        </c:forEach>
    </select>
    <input type="submit" value="<fmt:message key="button.apply.changed"/>"/>
</form>

<input type="button" id="regbutton" name="registered_courses" value="<fmt:message key="button.registered"/>" style="position: absolute; left:30px; top:200px; height:30px;width:145px"/>
<input type="button" id="ongoingbutton" name="ongoing_courses" value="<fmt:message key="button.ongoing"/>" style="position: absolute; left:30px; top:230px; height:30px;width:145px"/>
<input type="button" id="finishedbutton" name="finished_courses" value="<fmt:message key="button.finished"/>" style="position: absolute; left:30px; top:260px; height:30px;width:145px"/>

<table id="regtable" border="1" style="margin-top:120px; margin-left:300px; width:900px; display:none;">
   <caption><fmt:message key="label.register.courses"/></caption>
    <tr>
        <th><fmt:message key="label.name"/></th>
        <th><fmt:message key="label.teacher"/></th>
        <th><fmt:message key="label.duration"/></th>
        <th><fmt:message key="label.students.reg"/></th>
        <th><fmt:message key="label.grade"/></th>
    </tr>
<%
 session.getAttribute("coursesToBeStartedTable");
%>
    <c:forEach items="${coursesToBeStartedTable}" var="course">
    <tr>
        <td><c:out value="${course.name}"/></td>
        <td><c:out value="${course.teacher}"/></td>
        <td><c:out value="${course.duration}"/></td>
        <td><c:out value="${course.studentsRegistered}"/></td>
        <td><c:out value="${course.grade}"/></td>
    <tr>
    </c:forEach>
</table>



<table id="ongoingtable" border="1" style="margin-top:120px; margin-left:300px; width:900px;">
    <caption><fmt:message key="label.ongoing.courses"/></caption>
    <tr>
        <th><fmt:message key="label.name"/></th>
        <th><fmt:message key="label.teacher"/></th>
        <th><fmt:message key="label.duration"/></th>
        <th><fmt:message key="label.students.reg"/></th>
        <th><fmt:message key="label.grade"/></th>
    </tr>
    <%
        session.getAttribute("coursesInProgressTable");
    %>
    <c:forEach items="${coursesInProgressTable}" var="course">
    <tr>
        <td><c:out value="${course.name}"/></td>
        <td><c:out value="${course.teacher}"/></td>
        <td><c:out value="${course.duration}"/></td>
        <td><c:out value="${course.studentsRegistered}"/></td>
        <td><c:out value="${course.grade}"/></td>
    <tr>
        </c:forEach>
</table>



<table id="finishedtable" border="1" style="margin-top:120px; margin-left:300px; width:900px; display:none;">
    <caption><fmt:message key="label.finished.courses"/></caption>
    <tr>
        <th><fmt:message key="label.name"/></th>
        <th><fmt:message key="label.teacher"/></th>
        <th><fmt:message key="label.duration"/></th>
        <th><fmt:message key="label.students.reg"/></th>
        <th><fmt:message key="label.grade"/></th>
    </tr>
    <%
        session.getAttribute("finishedCoursesTable");
    %>
    <c:forEach items="${finishedCoursesTable}" var="course">
    <tr>
        <td><c:out value="${course.name}"/></td>
        <td><c:out value="${course.teacher}"/></td>
        <td><c:out value="${course.duration}"/></td>
        <td><c:out value="${course.studentsRegistered}"/></td>
        <td><c:out value="${course.grade}"/></td>
    <tr>
        </c:forEach>
</table>

</body>
</html>