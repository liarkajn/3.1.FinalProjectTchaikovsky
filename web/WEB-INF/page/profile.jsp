<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
    <title>
        <c:choose>
            <c:when test="${requestScope.user.name != null && requestScope.user.surname != null}">
                <c:out value="${requestScope.user.name} ${requestScope.user.surname} (${requestScope.user.login})" />
            </c:when>
            <c:otherwise>
                <c:out value="${requestScope.user.login}" />
            </c:otherwise>
        </c:choose>
    </title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="css/normalize.css">
    <link rel="stylesheet" type="text/css" href="css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="css/questionnaire.css">
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.profile.answers.count" var="answersCount"/>
    <fmt:message bundle="${loc}" key="local.profile.questions.count" var="questionsCount"/>
    <fmt:message bundle="${loc}" key="local.profile.averagemark" var="averageMark"/>
    <fmt:message bundle="${loc}" key="local.profile.bio.default" var="defaultBio"/>
    <fmt:message bundle="${loc}" key="local.profile.contactinformation" var="contactInformation"/>
    <fmt:message bundle="${loc}" key="local.profile.email" var="emailField"/>
    <fmt:message bundle="${loc}" key="local.profile.gender" var="genderField"/>
</head>
<body>

<jsp:include page="navbar.jsp"/>

<main class="content">
    <div class="container">
        <c:if test="${requestScope.error_message != null}">
            <div class="row">
                <div class="col-12">
                    <div class="alert alert-danger">
                        <c:out value="${requestScope.error_message}"/>
                    </div>
                </div>
            </div>
        </c:if>
        <div class="row">
            <div class="col-12 col-md-4 col-lg-3 col-xl-3" align="center">
                <div class="text-center">
                    <c:choose>
                        <c:when test="${requestScope.user.gender.toString().toLowerCase() == 'male'}">
                            <img src="icons/ic_profile_male.png" class="profile_image">
                        </c:when>
                        <c:otherwise>
                            <img src="icons/ic_profile_female.png" class="profile_image">
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="row">
                    <table class="profile_info">
                        <thead>
                        <tr>
                            <td><c:out value="${answersCount}" /></td>
                            <td><c:out value="${questionsCount}" /></td>
                            <td><c:out value="${averageMark}" /></td>
                        </tr>
                        </thead>
                        <tr>
                            <td><c:out value="${requestScope.user.answersCount}" /></td>
                            <td><c:out value="${requestScope.user.questionsCount}" /></td>
                            <td><c:out value="${requestScope.user.averageMark}" /></td>
                        </tr>
                    </table>
                </div>
            </div>
            <div class="col-12 col-md-7 col-lg-8 col-xl-9 user_info">
                <br>
                <h1 class="h1-responsive text-muted">
                    <c:choose>
                        <c:when test="${requestScope.user.name != null && requestScope.user.surname != null}">
                            <c:out value="${requestScope.user.name}" /> <c:out value="${requestScope.user.surname}" />
                        </c:when>
                        <c:otherwise>
                            <c:out value="${requestScope.user.login}" />
                        </c:otherwise>
                    </c:choose>
                    <c:if test="${requestScope.user.id == sessionScope.id}">
                        <a href="main?command=go_to_profile_editing&id=${requestScope.user.id}">
                            <img class="edit_btn" src="icons/open-iconic-master/svg/pencil.svg" alt="Edit" title="Edit">
                        </a>
                    </c:if>
                </h1>
                <c:choose>
                    <c:when test="${requestScope.user.bio != null}">
                        <p><c:out value="${requestScope.user.bio}"/></p>
                    </c:when>
                    <c:otherwise>
                        <p class="text-muted"><c:out value="${defaultBio}" /></p>
                    </c:otherwise>
                </c:choose>
                <h6 class="text-muted"><c:out value="${contactInformation}" />:</h6>
                <p><c:out value="${emailField}" /> : <c:out value="${requestScope.user.email}" /></p>
                <p><c:out value="${genderField}" /> : <c:out value="${requestScope.user.gender.toString().toLowerCase()}"/></p>
            </div>
        </div>
    </div>
</main>

<script type="text/javascript" src="js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="js/popper.js"></script>
<script type="text/javascript" src="js/bootstrap.js"></script>
</body>
</html>
