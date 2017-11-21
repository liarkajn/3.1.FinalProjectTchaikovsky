<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
    <title>Sign in to LikeIT</title>

    <fmt:setLocale value="${sessionScope.local}" />
    <fmt:setBundle basename="local" var="loc" />
    <fmt:message bundle="${loc}" key="local.login.placeholder" var="loginPlaceholder" />
    <fmt:message bundle="${loc}" key="local.password.placeholder" var="passwordPlaceholder" />
    <fmt:message bundle="${loc}" key="local.signinbutton.name" var="signInButtonName" />

</head>
<body>

    <div align="center">

        <form method="get" action="/main">
            <input type="hidden" name="command" value="user" />
            <input type="hidden" name="action" value="signIn" />
            <input type="text" name="login" placeholder="${loginPlaceholder}" /> <br/>
            <input type="password" name="password" placeholder="${passwordPlaceholder}" /> <br/>
            <input type="submit" value="${signInButtonName}" />
        </form>

    </div>

</body>
</html>
