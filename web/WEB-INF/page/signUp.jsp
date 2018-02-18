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
    <fmt:message bundle="${loc}" key="local.registration.header.message" var="signUpHeader"/>
    <fmt:message bundle="${loc}" key="local.registration.login.placeholder" var="loginPlaceholder"/>
    <fmt:message bundle="${loc}" key="local.registration.login.validation.message" var="loginInvalidMessage"/>
    <fmt:message bundle="${loc}" key="local.registration.password.placeholder" var="passwordPlaceholder"/>
    <fmt:message bundle="${loc}" key="local.registration.password.validation.message" var="passwordInvalidMessage"/>
    <fmt:message bundle="${loc}" key="local.registration.password.confirm.placeholder" var="confirmPasswordPlaceholder"/>
    <fmt:message bundle="${loc}" key="local.registration.email.placeholder" var="emailPlaceholder"/>
    <fmt:message bundle="${loc}" key="local.registration.email.validation.message" var="emailInvalidMessage"/>
    <fmt:message bundle="${loc}" key="local.registration.gender.placeholder" var="genderPlaceholder"/>
    <fmt:message bundle="${loc}" key="local.registration.gender.default" var="defaultGender"/>
    <fmt:message bundle="${loc}" key="local.registration.gender.male" var="maleGender"/>
    <fmt:message bundle="${loc}" key="local.registration.gender.female" var="femaleGender"/>
    <fmt:message bundle="${loc}" key="local.registration.gender.other" var="otherGender"/>
    <fmt:message bundle="${loc}" key="local.registration.login.message" var="loginMessage"/>
    <fmt:message bundle="${loc}" key="local.registration.signupbutton" var="signUpButtonName"/>

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
                        <h1 class="text-uppercase"><c:out value="${signUpHeader}" /></h1>
                    </div>
                    <c:if test="${requestScope.error_message != null}">
                        <div class="alert alert-danger">
                            <c:out value="${requestScope.error_message}"/>
                        </div>
                    </c:if>
                    <div class="form-row">
                        <div class="col-12 mb-3">
                            <label for="login"><c:out value="${loginPlaceholder}" /></label>
                            <input type="text" class="form-control" id="login" name="login" placeholder="${loginPlaceholder}"
                                   onfocus="isValid(event)" onkeyup="isValid(event)"
                                   pattern="^[A-Za-z][A-Za-z0-9_]{4,16}$"
                                   required>
                            <div class="invalid-feedback">
                                <c:out value="${loginInvalidMessage}" />
                            </div>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="col-12 mb-3">
                            <label for="email"><c:out value="${emailPlaceholder}" /></label>
                            <input type="email" class="form-control" id="email" name="email" placeholder="${emailPlaceholder}"
                                   onfocus="isValid(event)" onkeyup="isValid(event)"
                                   pattern='^([a-zA-Z0-9]+(\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*(\.[a-zA-Z]{1,6}))?$'
                                   required>
                            <div class="invalid-feedback">
                                <c:out value="${emailInvalidMessage}" />
                            </div>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="col-12 mb-3">
                            <label for="password"><c:out value="${passwordPlaceholder}" /></label>
                            <input type="password" class="form-control" id="password" name="password"
                                   placeholder="${passwordPlaceholder}" onfocus="isValid(event)" onkeyup="isValid(event)"
                                   pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{6,18}$" required>
                            <div class="invalid-feedback">
                                <c:out value="${passwordInvalidMessage}" />
                            </div>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="col-12 mb-3">
                            <label for="repeatedPassword"><c:out value="${confirmPasswordPlaceholder}" /></label>
                            <input type="password" class="form-control" id="repeatedPassword" name="repeatedPassword"
                                   placeholder="${confirmPasswordPlaceholder}" onfocus="isValid(event)" onkeyup="isValid(event)"
                                   pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{6,18}$"
                                   required>
                            <div class="invalid-feedback">
                                <c:out value="${passwordInvalidMessage}" />
                            </div>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-12">
                            <label for="gender"><c:out value="${genderPlaceholder}" /></label>
                            <select class="form-control" id="gender" name="gender" required>
                                <option value="" disabled selected><c:out value="${defaultGender}" /></option>
                                <option><c:out value="${maleGender}" /></option>
                                <option><c:out value="${femaleGender}" /></option>
                                <option><c:out value="${otherGender}" /></option>
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
                    <a href="/main?command=go_to_authorization" class="form-log-in-with-existing"><c:out value="${loginMessage}" /></a>
                    <div class="form-row">
                        <button class="btn btn-info" type="submit"><c:out value="${signUpButtonName}" /></button>
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
