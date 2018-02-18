<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>
        <c:choose>
            <c:when test="${requestScope.user.name != null && requestScope.user.surname != null}">
                <c:out value="${requestScope.user.name} ${requestScope.user.surname} (${requestScope.user.login})"/>
            </c:when>
            <c:otherwise>
                <c:out value="${requestScope.user.login}"/>
            </c:otherwise>
        </c:choose>
    </title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="css/normalize.css">
    <link rel="stylesheet" type="text/css" href="css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="css/questionnaire.css">
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.profile.contactinformation" var="contactInformationLabel"/>
    <fmt:message bundle="${loc}" key="local.profile.login.label" var="loginLabel"/>
    <fmt:message bundle="${loc}" key="local.profile.login.placeholder" var="loginPlaceholder"/>
    <fmt:message bundle="${loc}" key="local.profile.email.label" var="emailLabel"/>
    <fmt:message bundle="${loc}" key="local.profile.email.placeholder" var="emailPlaceholder"/>
    <fmt:message bundle="${loc}" key="local.profile.personalinformation" var="personalInformationLabel"/>
    <fmt:message bundle="${loc}" key="local.profile.name.label" var="nameLabel"/>
    <fmt:message bundle="${loc}" key="local.profile.name.placeholder" var="namePlaceholder"/>
    <fmt:message bundle="${loc}" key="local.profile.surname.label" var="surnameLabel"/>
    <fmt:message bundle="${loc}" key="local.profile.surname.placeholder" var="surnamePlaceholder"/>
    <fmt:message bundle="${loc}" key="local.profile.gender" var="genderLabel"/>
    <fmt:message bundle="${loc}" key="local.profile.gender.male" var="maleOption"/>
    <fmt:message bundle="${loc}" key="local.profile.gender.female" var="femaleOption"/>
    <fmt:message bundle="${loc}" key="local.profile.gender.other" var="otherOption"/>
    <fmt:message bundle="${loc}" key="local.profile.bio.label" var="bioLabel"/>
    <fmt:message bundle="${loc}" key="local.profile.bio.placeholder" var="bioPlaceholder"/>
    <fmt:message bundle="${loc}" key="local.profile.editbutton" var="editBtn"/>
    <fmt:message bundle="${loc}" key="local.registration.login.validation.message" var="loginValidation"/>
    <fmt:message bundle="${loc}" key="local.registration.email.validation.message" var="emailValidation"/>
</head>
<body>

<jsp:include page="navbar.jsp"/>

<main class="content">
    <div class="container">
        <div class="row">
            <div class="col-11 col-md-11 col-lg-8 card m-auto">
                <form method="get" action="main">
                    <br>
                    <div class="row">
                        <input type="hidden" name="command" value="profile_editing"/>
                        <input type="hidden" name="id" value="${user.id}"/>
                        <div class="col-12 col-md-6">
                            <h5 class="text-muted"><c:out value="${contactInformationLabel}" />: </h5>
                            <div class="form-row">
                                <div class="col-12 mb-3">
                                    <label for="login"><c:out value="${loginLabel}" /></label>
                                    <input type="text" class="form-control" id="login" name="login"
                                           value="${user.login}"
                                           placeholder="${loginPlaceholder}" onfocus="isValid(event)" onkeyup="isValid(event)"
                                           pattern="^[A-Za-z][A-Za-z0-9_]{4,16}$"
                                           required>
                                    <div class="invalid-feedback">
                                        <c:out value="${loginValidation}" />
                                    </div>
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="col-12 mb-3">
                                    <label for="email"><c:out value="${emailLabel}" /></label>
                                    <input type="email" class="form-control" id="email" name="email"
                                           value="${user.email}"
                                           placeholder="${emailPlaceholder}" onfocus="isValid(event)" onkeyup="isValid(event)"
                                           pattern='[A-Za-z0-9.]+@[a-z0-9.-]+.[a-z]{2,3}'
                                           required>
                                    <div class="invalid-feedback">
                                        <c:out value="${emailValidation}" />
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-12 col-md-6">
                            <h5 class="text-muted"><c:out value="${personalInformationLabel}" />:</h5>
                            <div class="form-row">
                                <div class="col-12 mb-3">
                                    <label for="name"><c:out value="${nameLabel}" /></label>
                                    <input type="text" class="form-control" id="name" name="name" value="${user.name}"
                                           placeholder="${namePlaceholder}">
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="col-12 mb-3">
                                    <label for="surname"><c:out value="${surnameLabel}" /></label>
                                    <input type="text" class="form-control" id="surname" name="surname"
                                           value="${user.surname}"
                                           placeholder="${surnamePlaceholder}">
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group col-12">
                                    <label for="gender"><c:out value="${genderLabel}" /></label>
                                    <select class="form-control" id="gender" name="gender" required>
                                        <c:choose>
                                            <c:when test="${user.gender.toString().toLowerCase() == 'male'}">
                                                <option selected><c:out value="${maleOption}" /></option>
                                                <option><c:out value="${femaleOption}" /></option>
                                                <option><c:out value="${otherOption}" /></option>
                                            </c:when>
                                            <c:when test="${user.gender.toString().toLowerCase() == 'female'}">
                                                <option><c:out value="${maleOption}" /></option>
                                                <option selected><c:out value="${femaleOption}" /></option>
                                                <option><c:out value="${otherOption}" /></option>
                                            </c:when>
                                            <c:otherwise>
                                                <option><c:out value="${maleOption}" /></option>
                                                <option><c:out value="${femaleOption}" /></option>
                                                <option selected><c:out value="${otherOption}" /></option>
                                            </c:otherwise>
                                        </c:choose>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="col-12 mb-3">
                            <label for="bio"><c:out value="${bioLabel}" /></label>
                            <textarea id="bio" name="bio" class="form-control" placeholder="${bioPlaceholder}"
                                      minlength="10"><c:out value="${user.bio}"/></textarea>
                        </div>
                    </div>
                    <div class="form-row">
                        <input class="btn btn-accept" type="submit" value="${editBtn}" />
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
