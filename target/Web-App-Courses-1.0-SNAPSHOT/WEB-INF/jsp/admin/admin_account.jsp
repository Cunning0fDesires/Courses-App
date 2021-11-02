<%@ page import="com.epam.project.webappcourses.web.Path" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head>
    <script
            src="https://code.jquery.com/jquery-3.6.0.min.js"
            integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
            crossorigin="anonymous">
    </script>

    <script>
        $(document).ready(function() {
            $("#sortbutton").click(function () {
                $("#courses_list").hide();
                $("#sorted_courses_list").show();
            });
        });
    </script>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <title>Admin account</title>
</head>

<body>
<div align="center">
    <h1>Welcome, admin! ^___^</h1>
</div>

<a href="<%=request.getContextPath()%>/controller?command=logout"><button class="GFG" style="position: absolute; top:25px; right:20px; height:30px;width:70px">
Log out
</button></a>

<a href="/addteacher"><button class="GFG" style="position: absolute; top:150px; left:600px; height:30px;width:120px">
    Add a teacher
</button></a>

<a href="<%=request.getContextPath()%>/addcourse"><button class="GFG" style="position: absolute; top:150px; left:900px; height:30px;width:120px">
    Add a course
</button></a>

<form id="change_status" action="controller" method="post" style="position:absolute; left:900px; bottom:300px; height:30px;width:220px">
    <input type="hidden" name="command" value="changeStatus"/>

    <label for="statuses">Change a student's status:</label>
    <%
        session.getAttribute("allStudentsWithStatus");
    %>
    <select name="id" id="studentids">
        <c:forEach items="${allStudentsWithStatus}" var="student">
            <option value="${student.id}"><c:out value="${student.id}"/></option>
        </c:forEach>
    </select>
    <select name="status" id="statuses">
        <option value="active">active</option>
        <option value="blocked">blocked</option>
    </select>
    <br><br>
    <input type="submit" value="Submit">
</form>

<form id="attach_course" action="controller" method="post" style="position:absolute; left:600px; bottom:300px; height:30px;width:220px">
    <input type="hidden" name="command" value="attachCourse"/>

    <label for="attach_course">Attach a teacher to a course:</label>
    <%
        session.getAttribute("allTeachers");
        session.getAttribute("allCourses");
    %>
    <select name="teacher" id="teacherId">
        <c:forEach items="${allTeachers}" var="teacher">
            <option value="${teacher.id}"><c:out value="${teacher.id}"/></option>
        </c:forEach>
    </select>
    <select name="course" id="courses">
        <c:forEach items="${allCourses}" var="course">
            <option value="${course.id}"><c:out value="${course.id}"/></option>
        </c:forEach>
    </select>
    <br><br>
    <input type="submit" value="Submit">
</form>



        <table id="teachers" border="1">
            <caption>Teachers</caption>
            <tr>
                <th>teacher id</th>
                <th>teacher</th>
                <th>course</th>
            </tr>
            <%
                session.getAttribute("allTeachersWithCoursesList");
            %>
            <c:forEach items="${allTeachersWithCoursesList}" var="teacher">
            <tr>
                <td><c:out value="${teacher.id}"/></td>
                <td><c:out value="${teacher.teacher}"/></td>
                <td><c:out value="${teacher.course}"/></td>
            <tr>
                </c:forEach>
        </table>


        <table id="courses_list" border="1" >
            <caption>Courses</caption>
            <tr>
                <th>id</th>
                <th>name</th>
                <th>topic</th>
                <th>start date</th>
                <th>end date</th>
            </tr>
            <%
                session.getAttribute("allCourses");
            %>
            <c:forEach items="${allCourses}" var="course">
            <tr>
                <td><c:out value="${course.id}"/></td>
                <td><c:out value="${course.name}"/></td>
                <td><c:out value="${course.topic}"/></td>
                <td><c:out value="${course.startDate}"/></td>
                <td><c:out value="${course.endDate}"/></td>
            <tr>
                </c:forEach>
        </table>


        <table id="students" border="1">
            <caption>Students</caption>
            <tr>
                <th>id</th>
                <th>student</th>
                <th>status</th>
            </tr>
            <%
                session.getAttribute("allStudentsWithStatus");
            %>
            <c:forEach items="${allStudentsWithStatus}" var="student">
            <tr>
                <td><c:out value="${student.id}"/></td>
                <td><c:out value="${student.student}"/></td>
                <td><c:out value="${student.status}"/></td>
            <tr>
                </c:forEach>
        </table>


    <table id="sorted_courses_list" border="1" style="display:none;" >
        <caption>Courses</caption>
        <tr>
            <th>id</th>
            <th>name</th>
            <th>topic</th>
            <th>start date</th>
            <th>end date</th>
        </tr>
        <%
            session.getAttribute("sortedCourses");
        %>
        <c:forEach items="${sortedCourses}" var="course">
        <tr>
            <td><c:out value="${course.id}"/></td>
            <td><c:out value="${course.name}"/></td>
            <td><c:out value="${course.topic}"/></td>
            <td><c:out value="${course.startDate}"/></td>
            <td><c:out value="${course.endDate}"/></td>
        <tr>
            </c:forEach>
    </table>

    <input type="button" id="sortbutton" name="sorted_courses" value="See a sorted table"/>

<form align="center" action="controller"  method="post">
    <input type="hidden" name="command" value="sortAdmin"/>
    <table align="center" style="with:50%;">
    <tr>
        <td>Start date</td>
        <td><input type="date" name="start_date_filter" /></td>
    </tr>
    <tr>
        <td>End date</td>
        <td><input type="date" name="end_date_filter" /></td>
    </tr>
</table>
    <input align="center" type="submit" value="Sort" />
</form>


</body>
</html>
