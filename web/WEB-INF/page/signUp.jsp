<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Sign up to LikeIT</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="css/normalize.css">
    <link rel="stylesheet" type="text/css" href="css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="css/questionnaire.css">
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.login.placeholder" var="loginPlaceholder"/>
    <fmt:message bundle="${loc}" key="local.password.placeholder" var="passwordPlaceholder"/>
    <fmt:message bundle="${loc}" key="local.password.repeated.placeholder" var="repeatedPasswordPlaceholder"/>
    <fmt:message bundle="${loc}" key="local.email.placeholder" var="emailPlaceholder"/>
    <fmt:message bundle="${loc}" key="local.signupbutton.name" var="signUpButtonName"/>
</head>
<body>

<jsp:include page="navbar.jsp"/>

<main class="content">
    <div class="container">
        <div class="row">
            <div class="col-11 col-md-6 col-lg-4 card m-auto">
                <form method="get" action="main">
                    <input type="hidden" name="command" value="registration"/>
                    <div class="title-row">
                        <h1>SIGN UP</h1>
                    </div>
                    <div class="form-row">
                        <div class="col-12 mb-3">
                            <label for="login">Login</label>
                            <input type="text" class="form-control" id="login" name="login" placeholder="Login"
                                   onfocus="isValid(event)" onkeyup="isValid(event)"
                                   pattern="^[A-Za-z][A-Za-z0-9_]{4,16}$"
                                   required>
                            <div class="invalid-feedback">
                                Login must contain latin, numbers and special symbol ( _ )
                            </div>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="col-12 mb-3">
                            <label for="email">Email</label>
                            <input type="email" class="form-control" id="email" name="email" placeholder="Email"
                                   onfocus="isValid(event)" onkeyup="isValid(event)"
                                   pattern='[A-Za-z0-9.]+@[a-z0-9.-]+.[a-z]{2,3}'
                                   required>
                            <div class="invalid-feedback">
                                Email must contain symbol @ and .
                            </div>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="col-12 mb-3">
                            <label for="password">Password</label>
                            <input type="password" class="form-control" id="password" name="password"
                                   placeholder="Password" onfocus="isValid(event)" onkeyup="isValid(event)"
                                   pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{6,18}$" required>
                            <div class="invalid-feedback">
                                The password must contain at least 1 small, 1 large Latin letters and 1 number
                            </div>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="col-12 mb-3">
                            <label for="repeatedPassword">Confirm password</label>
                            <input type="password" class="form-control" id="repeatedPassword" name="repeatedPassword"
                                   placeholder="Confirm password" onfocus="isValid(event)" onkeyup="isValid(event)"
                                   pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{6,18}$"
                                   required>
                            <div class="invalid-feedback">
                                The password must contain at least 1 small, 1 large Latin letters and 1 number
                            </div>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-12">
                            <label for="gender">Gender</label>
                            <select class="form-control" id="gender" name="gender" required>
                                <option value="" disabled selected>I'm ...</option>
                                <option>Male</option>
                                <option>Female</option>
                                <option>Other</option>
                            </select>
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
                    <a href="/main?command=go_to_authorization" class="form-log-in-with-existing">Already have an account? Login</a>
                    <div class="form-row">
                        <button class="btn btn-info" type="submit">Register</button>
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
