<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
    <title>Sign up to LikeIT</title>

    <fmt:setLocale value="${sessionScope.local}" />
    <fmt:setBundle basename="local" var="loc" />
    <fmt:message bundle="${loc}" key="local.login.placeholder" var="loginPlaceholder" />
    <fmt:message bundle="${loc}" key="local.password.placeholder" var="passwordPlaceholder" />
    <fmt:message bundle="${loc}" key="local.password.repeated.placeholder" var="repeatedPasswordPlaceholder" />
    <fmt:message bundle="${loc}" key="local.email.placeholder" var="emailPlaceholder" />
    <fmt:message bundle="${loc}" key="local.signupbutton.name" var="signUpButtonName" />

</head>
<body>

    <div align="center">

        <form method="get" action="/main">

            <input type="hidden" name="command" value="user" />
            <input type="hidden" name="action" value="signUp" />
            <input type="text" name="login" placeholder="${loginPlaceholder}" /> <br/>
            <input type="text" name="email" placeholder="${emailPlaceholder}" /> <br/>
            <input type="password" name="password" placeholder="${passwordPlaceholder}" /> <br/>
            <input type="password" name="repeatedPassword" placeholder="${repeatedPasswordPlaceholder}" /> <br/>
            <input type="submit" value="${signUpButtonName}" />

        </form>

    </div>

</body>
</html>
