<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Sign in to LikeIT</title>
    <link rel="stylesheet" type="text/css" href="css/main.css">

    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.login.placeholder" var="loginPlaceholder"/>
    <fmt:message bundle="${loc}" key="local.password.placeholder" var="passwordPlaceholder"/>
    <fmt:message bundle="${loc}" key="local.signinbutton.name" var="signInButtonName"/>

</head>
<body>

<jsp:include page="navbar.jsp"/>

<div align="center">

    <form method="get" action="/main">
        <input type="hidden" name="command" value="authorization"/>
        <input type="text" name="login" placeholder="${loginPlaceholder}" required /> <br/>
        <input type="password" name="password" placeholder="${passwordPlaceholder}" required /> <br/>
        <input type="submit" value="${signInButtonName}"/>
    </form>

</div>

</body>
</html>
