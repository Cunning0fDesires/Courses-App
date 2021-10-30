<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Adding a course</title>
</head>
<body>

<h1 align="center">Course Registration</h1>
<form align="center" action="controller"  method="post">
    <input type="hidden" name="command" value="addcourse"/>

    <table align="center" style="with:50%">
        <tr>
            <td>Name</td>
            <td><input type="text" name="course_name" /></td>
        </tr>
        <tr>
            <td>Topic</td>
            <td><input type="text" name="topic" /></td>
        </tr>
        <tr>
            <td>Start date</td>
            <td><input type="date" name="start_date" /></td>
        </tr>
        <tr>
            <td>End date</td>
            <td><input type="date" name="end_date" /></td>
        </tr>
    </table>
    <input align="center" type="submit" value="Register" />
</form>

</body>
</html>
