<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Sign in to LikeIT</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="css/normalize.css">
    <link rel="stylesheet" type="text/css" href="css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="css/questionnaire.css">
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.authorization.header.message" var="signInHeader"/>
    <fmt:message bundle="${loc}" key="local.authorization.login.placeholder" var="loginPlaceholder"/>
    <fmt:message bundle="${loc}" key="local.authorization.login.validation.message" var="loginInvalidMessage"/>
    <fmt:message bundle="${loc}" key="local.authorization.password.placeholder" var="passwordPlaceholder"/>
    <fmt:message bundle="${loc}" key="local.authorization.password.validation.message" var="passwordInvalidMessage"/>
    <fmt:message bundle="${loc}" key="local.authorization.nothaveaccount.message" var="isAccountExists"/>
    <fmt:message bundle="${loc}" key="local.authorization.createanaccount.message" var="createAnAccount"/>
    <fmt:message bundle="${loc}" key="local.authorization.signinbutton.name" var="signInButtonName"/>

</head>
<body>

<jsp:include page="navbar.jsp"/>

<main class="content">
    <div class="container">
        <div class="row">
            <div class="col-11 col-md-6 col-lg-4 card m-auto">
                <form method="get" action="main">
                    <input type="hidden" name="command" value="authorization"/>
                    <div class="title-row">
                        <h1 class="text-uppercase">${signInHeader}</h1>
                    </div>
                    <c:if test="${requestScope.error_message != null}">
                        <div class="alert alert-danger">
                            <c:out value="${requestScope.error_message}"/>
                        </div>
                    </c:if>
                    <div class="form-row">
                        <div class="col-12 col-lg-12 mb-3">
                            <label for="login"><c:out value="${loginPlaceholder}"/></label>
                            <input type="text" class="form-control" id="login" name="login"
                                   value="${requestScope.login}" placeholder="${loginPlaceholder}"
                                   onfocus="isValid(event)"
                                   onkeyup="isValid(event)" pattern="^[A-Za-z][A-Za-z0-9_]{4,16}$" required>
                            <div class="invalid-feedback">
                                <c:out value="${loginInvalidMessage}"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="col-12 col-lg-12 mb-3">
                            <label for="password"><c:out value="${passwordPlaceholder}"/></label>
                            <input type="password" class="form-control" id="password" name="password"
                                   placeholder="${passwordPlaceholder}" onfocus="isValid(event)"
                                   onkeyup="isValid(event)"
                                   pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{6,18}$" required>
                            <div class="invalid-feedback">
                                <c:out value="${passwordInvalidMessage}"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="form-check">
                            <input class="form-check-input" type="checkbox" id="remember" name="remember">
                            <label class="form-check-label" for="remember">
                                Remember me
                            </label>
                        </div>
                    </div>
                    <p><c:out value="${isAccountExists}"/> <a href="/main?command=go_to_registration" class="form-log-in-with-existing">
                        <c:out value="${createAnAccount}"/></a></p>
                    <div class="form-row">
                        <button class="btn btn-info" type="submit"><c:out value="${signInButtonName}" /></button>
                    </div>
                    <br>
                </form>
            </div>
        </div>
    </div>
</main>

<script type="text/javascript" src="js/questionnaire.js"></script>
<script type="text/javascript" src="js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="js/popper.js"></script>
<script type="text/javascript" src="js/bootstrap.js"></script>
</body>
</html>
