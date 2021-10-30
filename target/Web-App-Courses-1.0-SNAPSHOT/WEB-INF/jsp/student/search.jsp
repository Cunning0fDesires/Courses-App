<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>

<!DOCTYPE html>
<html>
<c:set var="title" value="Search" />
<head>
    <script
            src="https://code.jquery.com/jquery-3.6.0.min.js"
            integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
            crossorigin="anonymous">
    </script>

    <meta charset="UTF-8">
    <title><fmt:message key="label.search"/></title>
</head>
<body>

<script>
    $(document).ready(function() {
        $("#name").click(function () {
            $("#sortbystudents").hide();
            $("#sortbyduration").hide();
            $("#sortbyname").show();
        });
        $("#duration").click(function () {
            $("#sortbystudents").hide();
            $("#sortbyname").hide();
            $("#sortbyduration").show();
        });
        $("#regnumber").click(function () {
            $("#sortbyname").hide();
            $("#sortbyduration").hide();
            $("#sortbystudents").show();
        });
        $( "#courseRegistration" ).validate({
            rules: {
                course: {
                    required: true,
                    remote: "checkRegisteredCourse.jsp"
                }
            }
        });
    });
</script>


<div align="center">
    <h1><fmt:message key="label.courses.search"/></h1>
</div>

<a href="<%=request.getContextPath()%>/controller?command=logout"><button class="GFG" style="position: absolute; top:25px; right:20px; height:30px;width:70px">
    <fmt:message key="button.logout"/>
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

<input type="button" id="name" name="sort_name" value="<fmt:message key="button.sort.name"/>" style="position: absolute; left:30px; top:200px; height:30px;width:250px"/>
<input type="button" id="duration" name="sort_duration" value="<fmt:message key="button.sort.duration"/>" style="position: absolute; left:30px; top:230px; height:30px;width:250px"/>
<input type="button" id="regnumber" name="sort_reg" value="<fmt:message key="button.sort.students"/>" style="position: absolute; left:30px; top:260px; height:30px;width:250px"/>


<form id="courseRegistration" action="controller" method="post" style="position:absolute; left:30px; top:400px; height:30px;width:220px">
    <input type="hidden" name="command" value="registerForCourse"/>
    <label for="courses"><fmt:message key="label.register.for.course"/></label>
    <%
        session.getAttribute("sortedByName");
    %>
    <select name="course" id="courses">
        <c:forEach items="${sortedByName}" var="course">
        <option value="${course.name}"><c:out value="${course.name}"/></option>
        </c:forEach>
    </select>
    <br><br>
    <input type="submit" value="<fmt:message key="button.register"/>">
</form>


<table id="sortbyname" border="1" style="margin-top:120px; margin-left:300px; width:900px;">
    <caption><fmt:message key="label.sorted.name"/></caption>
    <tr>
        <th><fmt:message key="label.name"/></th>
        <th><fmt:message key="label.topic"/></th>
        <th><fmt:message key="label.teacher"/></th>
        <th><fmt:message key="label.duration"/></th>
        <th><fmt:message key="label.students.reg"/></th>
    </tr>
    <%
        session.getAttribute("sortedByName");
    %>
    <c:forEach items="${sortedByName}" var="course">
    <tr>
        <td><c:out value="${course.name}"/></td>
        <td><c:out value="${course.topic}"/></td>
        <td><c:out value="${course.teacher}"/></td>
        <td><c:out value="${course.duration}"/></td>
        <td><c:out value="${course.studentsRegistered}"/></td>
    <tr>
        </c:forEach>
</table>


<table id="sortbyduration" border="1" style="margin-top:120px; margin-left:300px; width:900px; display:none;">
    <caption><fmt:message key="label.sort.duration"/></caption>
    <tr>
        <th><fmt:message key="label.name"/></th>
        <th><fmt:message key="label.topic"/></th>
        <th><fmt:message key="label.teacher"/></th>
        <th><fmt:message key="label.duration"/></th>
        <th><fmt:message key="label.students.reg"/></th>
    </tr>
    <%
        session.getAttribute("sortedByDuration");
    %>
    <c:forEach items="${sortedByDuration}" var="course">
    <tr>
        <td><c:out value="${course.name}"/></td>
        <td><c:out value="${course.topic}"/></td>
        <td><c:out value="${course.teacher}"/></td>
        <td><c:out value="${course.duration}"/></td>
        <td><c:out value="${course.studentsRegistered}"/></td>
    <tr>
        </c:forEach>
</table>


<table id="sortbystudents" border="1" style="margin-top:120px; margin-left:300px; width:900px; display:none;">
    <caption><fmt:message key="label.sort.students"/></caption>
    <tr>
        <th><fmt:message key="label.name"/></th>
        <th><fmt:message key="label.topic"/></th>
        <th><fmt:message key="label.teacher"/></th>
        <th><fmt:message key="label.duration"/></th>
        <th><fmt:message key="label.students.reg"/></th>
    </tr>
    <%
        session.getAttribute("sortedByStudents");
    %>
    <c:forEach items="${sortedByStudents}" var="course">
    <tr>
        <td><c:out value="${course.name}"/></td>
        <td><c:out value="${course.topic}"/></td>
        <td><c:out value="${course.teacher}"/></td>
        <td><c:out value="${course.duration}"/></td>
        <td><c:out value="${course.studentsRegistered}"/></td>
    <tr>
        </c:forEach>
</table>



</body>
</html>