<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

    <form action="/administration" method="get">
        <input type="hidden" name="command" value="authorization" />
        Login: <br>
        <input type="text" name="login" /> <br>
        Password : <br>
        <input type="password" name="password" /> <br>
        <input type="submit" value="Log in" />
    </form>

</body>
</html>
