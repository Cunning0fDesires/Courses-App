<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Adding a teacher</title>
</head>
<body>

<h1 align="center">Teacher Registration</h1>
<form align="center" action="controller"  method="post">
    <input type="hidden" name="command" value="addteacher"/>

    <table align="center" style="with:50%">
        <tr>
            <td>Name</td>
            <td><input type="text" name="name" /></td>
        </tr>
        <tr>
            <td>Surname</td>
            <td><input type="text" name="surname" /></td>
        </tr>
        <tr>
            <td>Login</td>
            <td><input type="text" name="login" /></td>
        </tr>
        <tr>
            <td>Password</td>
            <td><input type="password" name="password" /></td>
        </tr>
        <tr>
            <td>Email</td>
            <td><input type="email" name="email" /></td>
        </tr>
    </table>
    <input align="center" type="submit" value="Register" />
</form>

</body>
</html>
