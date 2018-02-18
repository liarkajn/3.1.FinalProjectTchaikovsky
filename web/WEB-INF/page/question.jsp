<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>
        <c:out value="${requestScope.question.topic}"/>
    </title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="css/normalize.css">
    <link rel="stylesheet" type="text/css" href="css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="css/questionnaire.css">
    <link rel="stylesheet" href="http://netdna.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.question.author.answers.count" var="authorAnswersCount"/>
    <fmt:message bundle="${loc}" key="local.question.author.questions.count" var="authorQuestionsCount"/>
    <fmt:message bundle="${loc}" key="local.question.author.registered" var="authorRegisteredTime"/>
    <fmt:message bundle="${loc}" key="local.question.author.prefix" var="authorAskedPrefix"/>
    <fmt:message bundle="${loc}" key="local.question.time.delimiter" var="publishTimeDelimiter"/>
    <fmt:message bundle="${loc}" key="local.question.answers.count" var="answersCount"/>
    <fmt:message bundle="${loc}" key="local.question.authorization.signin.link" var="signInLink"/>
    <fmt:message bundle="${loc}" key="local.question.authorization.signup.link" var="signUpLink"/>
    <fmt:message bundle="${loc}" key="local.question.authorization.delimiter" var="authorizationDelimiter"/>
    <fmt:message bundle="${loc}" key="local.question.answer.label" var="answerLabel"/>
    <fmt:message bundle="${loc}" key="local.question.answer.placeholder" var="answerPlaceholder"/>
    <fmt:message bundle="${loc}" key="local.question.answer.answerbutton" var="answerBtn"/>
</head>
<body>

<jsp:include page="navbar.jsp"/>

<main class="content">
    <div class="container question">
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
            <div class="col-12 question_form">
                <div class="row">
                    <div class="col-md-3 col-lg-2 author-plate-md" align="center">
                        <c:choose>
                            <c:when test="${requestScope.question.author.gender.toString().toLowerCase() == 'male'}">
                                <img src="icons/ic_profile_male.png" class="profile-image">
                            </c:when>
                            <c:otherwise>
                                <img src="icons/ic_profile_female.png" class="profile-image">
                            </c:otherwise>
                        </c:choose>
                        <h6 class="signature"><a href="main?command=profile&id=<c:out value="${requestScope.question.author.id}"/>">
                            <strong>
                                <c:choose>
                                    <c:when test="${requestScope.question.author.name != null && requestScope.question.author.surname != null}">
                                        <c:out value="${requestScope.question.author.name}"/> <c:out value="${requestScope.question.author.surname}"/>
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="${requestScope.question.author.login}"/>
                                    </c:otherwise>
                                </c:choose>
                            </strong>
                        </a></h6>
                        <h6 class="author-plate-info"><c:out value="${authorAnswersCount}" />: <c:out value="${requestScope.question.author.answersCount}" /></h6>
                        <h6 class="author-plate-info"><c:out value="${authorQuestionsCount}" />: <c:out value="${requestScope.question.author.questionsCount}" /></h6>
                        <h6 class="author-plate-info"><c:out value="${authorRegisteredTime}" />: <c:out value="${requestScope.question.author.registrationDate.toLocalDate()}" /></h6>
                    </div>
                    <div class="col-12 col-md-9 col-lg-10">
                        <h1 class="h1-responsive text-muted">
                            <c:out value="${requestScope.question.topic}"/>
                            <c:if test="${sessionScope.id == requestScope.question.author.id && fn:length(requestScope.answers) == 0}">
                                <a href="main?command=go_to_question_editing&id=${requestScope.question.id}">
                                    <img class="edit_btn" src="icons/open-iconic-master/svg/pencil.svg" alt="Edit" title="Edit">
                                </a>
                            </c:if>
                        </h1>
                        <h6 class="signature"><c:out value="${authorAskedPrefix}" /> <a href="main?command=profile&id=<c:out value="${requestScope.question.author.id}"/>">
                            <strong>
                                <c:choose>
                                    <c:when test="${requestScope.question.author.name != null && requestScope.question.author.surname != null}">
                                        <c:out value="${requestScope.question.author.name}"/> <c:out value="${requestScope.question.author.surname}"/>
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="${requestScope.question.author.login}"/>
                                    </c:otherwise>
                                </c:choose>
                            </strong>
                        </a>, <c:out value="${requestScope.question.publishDate.toLocalDate()}"/> <c:out value="${publishTimeDelimiter}" /> <c:out value="${requestScope.question.publishDate.toLocalTime()}"/></h6>
                        <p><c:out value="${requestScope.question.content}"/></p>
                        <div class="author-plate">
                            <div class="row">
                                <div class="col-4">
                                    <c:choose>
                                        <c:when test="${requestScope.question.author.gender.toString().toLowerCase() == 'male'}">
                                            <img src="icons/ic_profile_male.png" class="profile-image">
                                        </c:when>
                                        <c:otherwise>
                                            <img src="icons/ic_profile_female.png" class="profile-image">
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <div class="col-8">
                                    <h6 class="signature">
                                        <a href="main?command=profile&id=<c:out value="${requestScope.question.author.id}"/>">
                                        <strong>
                                            <c:choose>
                                                <c:when test="${requestScope.question.author.name != null && requestScope.question.author.surname != null}">
                                                    <c:out value="${requestScope.question.author.name}"/> <c:out value="${requestScope.question.author.surname}"/>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:out value="${requestScope.question.author.login}"/>
                                                </c:otherwise>
                                            </c:choose>
                                        </strong>
                                        </a>
                                    </h6>
                                    <h6 class="small"><c:out value="${authorAnswersCount}" />: <c:out value="${requestScope.question.author.answersCount}" /></h6>
                                    <h6 class="small"><c:out value="${authorQuestionsCount}" />: <c:out value="${requestScope.question.author.questionsCount}" /></h6>
                                    <h5 class="small"><c:out value="${authorRegisteredTime}" />: <c:out value="${requestScope.question.author.registrationDate.toLocalDate()}" /></h5>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <br>
        <br>

        <div class="row">
            <label align="center">
                <h3 class="text-muted"><c:out value="${fn:length(requestScope.answers)}"/> <c:out value="${answersCount}" />:</h3>
            </label>
        </div>

        <c:if test="${requestScope.answers != null}">
            <c:forEach items="${requestScope.answers}" var="answer">
                <div class="row answer-md">
                    <div class="col-12">
                        <div class="row">
                            <div class="col-md-3 col-lg-2 author-plate-md" align="center">
                                <c:choose>
                                    <c:when test="${answer.author.gender.toString().toLowerCase() == 'male'}">
                                        <img src="icons/ic_profile_male.png" class="profile-image">
                                    </c:when>
                                    <c:otherwise>
                                        <img src="icons/ic_profile_female.png" class="profile-image">
                                    </c:otherwise>
                                </c:choose>
                                <h6 class="signature">
                                    <a href="main?command=profile&id=<c:out value="${answer.author.id}"/>">
                                        <strong>
                                            <c:choose>
                                                <c:when test="${answer.author.name != null && answer.author.surname != null}">
                                                    <c:out value="${answer.author.name}"/> <c:out value="${answer.author.surname}"/>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:out value="${answer.author.login}"/>
                                                </c:otherwise>
                                            </c:choose>
                                        </strong>
                                    </a>
                                </h6>
                                <h6 class="author-plate-info"><c:out value="${authorAnswersCount}" />: <c:out value="${answer.author.answersCount}" /></h6>
                                <h6 class="author-plate-info"><c:out value="${authorQuestionsCount}" />: <c:out value="${answer.author.questionsCount}" /></h6>
                                <h6 class="author-plate-info"><c:out value="${authorRegisteredTime}" />: <c:out value="${answer.author.registrationDate.toLocalDate()}" /></h6>
                            </div>
                            <div class="col-md-9 col-lg-10">
                                <p><c:out value="${answer.content}"/></p>
                            </div>
                        </div>
                        <c:if test="${requestScope.question.author.id == sessionScope.id}" >
                            <div class="row">
                                <div class="stars m-auto">
                                    <form action="main" method="get">
                                        <input type="hidden" name="command" value="answer_rating" />
                                        <input type="hidden" name="answer_id" value="${answer.id}" />
                                        <input type="hidden" name="question_id" value="${requestScope.question.id}" />
                                        <input type="number" max="5" name="rating" value="${answer.vote.mark}"/>
                                            <%--<input class="star star-5" id="${answer.id}-5" type="radio" name="rating" value="1" />--%>
                                            <%--<label class="star star-5" for="${answer.id}-5"></label>--%>
                                            <%--<input class="star star-4" id="${answer.id}-4" type="radio" name="rating" value="2" />--%>
                                            <%--<label class="star star-4" for="${answer.id}-4"></label>--%>
                                            <%--<input class="star star-3" id="${answer.id}-3" type="radio" name="rating" value="3" />--%>
                                            <%--<label class="star star-3" for="${answer.id}-3"></label>--%>
                                            <%--<input class="star star-2" id="${answer.id}-2" type="radio" name="rating" value="4" />--%>
                                            <%--<label class="star star-2" for="${answer.id}-2"></label>--%>
                                            <%--<input class="star star-1" id="${answer.id}-1" type="radio" name="rating" value="5" />--%>
                                            <%--<label class="star star-1" for="${answer.id}-1"></label>--%>
                                    </form>
                                </div>
                            </div>
                        </c:if>
                        <div class="author-plate">
                            <div class="row">
                                <div class="col-4">
                                    <c:choose>
                                        <c:when test="${answer.author.gender.toString().toLowerCase() == 'male'}">
                                            <img src="icons/ic_profile_male.png" class="profile-image">
                                        </c:when>
                                        <c:otherwise>
                                            <img src="icons/ic_profile_female.png" class="profile-image">
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <div class="col-8">
                                    <h6 class="signature">
                                        <a href="main?command=profile&id=<c:out value="${answer.author.id}"/>">
                                            <strong>
                                                <c:choose>
                                                    <c:when test="${answer.author.name != null && answer.author.surname != null}">
                                                        <c:out value="${answer.author.name}"/> <c:out value="${answer.author.surname}"/>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:out value="${answer.author.login}"/>
                                                    </c:otherwise>
                                                </c:choose>
                                            </strong>
                                        </a>
                                    </h6>
                                    <h6 class="author-plate-info"><c:out value="${authorAnswersCount}" />: <c:out value="${answer.author.answersCount}" /></h6>
                                    <h6 class="author-plate-info"><c:out value="${authorQuestionsCount}" />: <c:out value="${answer.author.questionsCount}" /></h6>
                                    <h6 class="author-plate-info"><c:out value="${authorRegisteredTime}" />: <c:out value="${answer.author.registrationDate.toLocalDate()}" /></h6>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <hr>
            </c:forEach>
        </c:if>

        <br>
        <br>
        <c:choose>
            <c:when test="${sessionScope.id != null}">
                <c:if test="${sessionScope.id != requestScope.question.author.id}">
                    <form action="main" method="get">
                        <div class="row">
                            <div class="col-12 col-md-8 m-auto">
                                <input type="hidden" name="command" value="answer_creation" />
                                <input type="hidden" name="question_id" value="${requestScope.question.id}" />
                                <h5 class="text-uppercase"><c:out value="${answerLabel}" />:</h5>
                                <textarea class="answer_area" name="content" placeholder="${answerPlaceholder}" minlength="50"></textarea>
                                <input type="submit" class="btn btn-info" value="${answerBtn}">
                            </div>
                        </div>
                    </form>
                </c:if>
            </c:when>
            <c:otherwise>
                <div class="row signature">
                    <h5>
                        <a href="main?command=go_to_authorization">
                            <c:out value="${signInLink}" />
                        </a> <c:out value="${authorizationDelimiter}" /> <a href="main?command=go_to_registration">
                            <c:out value="${signUpLink}" />
                        </a>
                    </h5>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</main>

<script type="text/javascript" src="js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="js/popper.js"></script>
<script type="text/javascript" src="js/bootstrap.js"></script>
</body>
</html>
