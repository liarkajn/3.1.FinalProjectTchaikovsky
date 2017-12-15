<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>LikeIT</title>
    <link rel="stylesheet" href=“css/normalize.css">
    <link rel="stylesheet" type="text/css" href="css/main.css">

    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.signinbutton.name" var="signInButtonName"/>
    <fmt:message bundle="${loc}" key="local.signupbutton.name" var="signUpButtonName"/>

</head>
<body>
<div>

    <jsp:include page="navbar.jsp"/>

    <div>

        <form action="main" method="post">
            <input type="hidden" name="local" value="ru"/>
            <input type="submit" value="Русский"/>
        </form>

        <form action="main" method="post">
            <input type="hidden" name="local" value="en"/>
            <input type="submit" value="English"/>
        </form>

        <form action="main" method="post">
            <select size="1" name="local">
                <option value="en">English</option>
                <option value="ru">Русский</option>
            </select>
        </form>

    </div>

    <div align="center  ">

        <div class="card card-1">

            <form action="main" method="get">
                <input type="hidden" name="command" value="signIn"/>
                <input type="submit" value="${signInButtonName}"/>
            </form>


            <form action="main" method="get">
                <input type="hidden" name="command" value="signUp"/>
                <input type="submit" value="${signUpButtonName}"/>
            </form>

        </div>
    </div>

</div>

</body>
</html>
