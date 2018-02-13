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
    <fmt:message bundle="${loc}" key="local.login.placeholder" var="loginPlaceholder"/>
    <fmt:message bundle="${loc}" key="local.password.placeholder" var="passwordPlaceholder"/>
    <fmt:message bundle="${loc}" key="local.signinbutton.name" var="signInButtonName"/>

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
                        <h1>SIGN IN</h1>
                    </div>
                    <c:if test="${requestScope.error_message != null}">
                        <div class="alert alert-danger">
                            <c:out value="${requestScope.error_message}"/>
                        </div>
                    </c:if>
                    <div class="form-row">
                        <div class="col-12 col-lg-12 mb-3">
                            <label for="login">Login</label>
                            <input type="text" class="form-control" id="login" name="login"
                                   value="${requestScope.login}" placeholder="Login" onfocus="isValid(event)"
                                   onkeyup="isValid(event)" pattern="^[A-Za-z][A-Za-z0-9_]{4,16}$" required>
                            <div class="invalid-feedback">
                                Login must contain latin, numbers and special symbol ( _ )
                            </div>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="col-12 col-lg-12 mb-3">
                            <label for="password">Password</label>
                            <input type="password" class="form-control" id="password" name="password"
                                   placeholder="Password" onfocus="isValid(event)" onkeyup="isValid(event)"
                                   pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{6,18}$" required>
                            <div class="invalid-feedback">
                                The password must contain at least 1 small, 1 large Latin letters and 1 number
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
                    <p>New to LikeIT? <a href="/main?command=go_to_registration" class="form-log-in-with-existing">Create
                        an account.</a></p>
                    <div class="form-row">
                        <button class="btn btn-info" type="submit">Sign in</button>
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
