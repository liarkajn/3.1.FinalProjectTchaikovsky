<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>LikeIt</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="css/normalize.css">
    <link rel="stylesheet" type="text/css" href="css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="css/questionnaire.css">
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="local" var="loc"/>
</head>
<body>

<jsp:include page="navbar.jsp"/>

<main class="content">
    <div class="container">
        <c:choose>
            <c:when test="${fn:length(requestScope.questions) > 0}">
                <c:set var="questions" scope="request" value="${requestScope.questions}"/>
                <c:forEach items="${questions}" var="question">
                    <div class="row">
                        <div class="col-12">
                            <div class="question-column">
                                <h1 class="h1-responsive font-bold text-muted"><c:out value="${question.topic}"/></h1>
                                <h6 class="signature">Written by <a
                                        href="main?command=profile&id=${question.author.id}">
                                    <strong>
                                        <c:choose>
                                            <c:when test="${question.author.name != null && question.author.surname != null}">
                                                <c:out value="${question.author.name}"/> <c:out
                                                    value="${question.author.surname}"/>
                                            </c:when>
                                            <c:otherwise>
                                                <c:out value="${question.author.login}"/>
                                            </c:otherwise>
                                        </c:choose>
                                    </strong></a>, <c:out value="${question.publishDate.toLocalDate()}"/> at <c:out
                                        value="${question.publishDate.toLocalTime()}"/></h6>
                                <div class="question">
                                    <p><c:out value="${question.content}"/></p>
                                </div>
                                <a href="main?command=question&id=${question.id}" class="btn btn-info">Read more</a>
                                <hr>
                            </div>
                        </div>
                    </div>
                </c:forEach>

                <nav>
                    <ul class="pagination">
                        <c:choose>
                            <c:when test="${(requestScope.currentPage - 1) <= requestScope.offset}">
                                <c:forEach var="index" begin="1" end="${requestScope.currentPage - 1}">
                                    <li class="page-item">
                                        <a class="page-link"
                                           href="main?command=questions&page=<c:out value="${index}"/>">
                                            <c:out value="${index}"/>
                                        </a>
                                    </li>
                                </c:forEach>
                                <li class="page-item active">
                                    <a class="page-link"
                                       href="main?command=questions&page=<c:out value="${requestScope.currentPage}"/>">
                                        <c:out value="${requestScope.currentPage}"/>
                                    </a>
                                </li>
                                <c:choose>
                                    <c:when test="${(requestScope.lastPage - requestScope.currentPage) > requestScope.offset}">
                                        <c:forEach var="index" begin="1" end="${requestScope.offset}">
                                            <li class="page-item">
                                                <a class="page-link"
                                                   href="main?command=questions&page=<c:out value="${requestScope.currentPage + index}"/>">
                                                    <c:out value="${requestScope.currentPage + index}"/>
                                                </a>
                                            </li>
                                        </c:forEach>
                                        <li class="page-item ml-2">
                                            <a class="page-link"
                                               href="main?command=questions&page=<c:out value="${requestScope.lastPage}"/>">
                                                <c:out value="${requestScope.lastPage}"/>
                                            </a>
                                        </li>
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach var="index" begin="${requestScope.currentPage + 1}"
                                                   end="${requestScope.lastPage}">
                                            <li class="page-item">
                                                <a class="page-link"
                                                   href="main?command=questions&page=<c:out value="${index}"/>">
                                                    <c:out value="${index}"/>
                                                </a>
                                            </li>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                            </c:when>
                            <c:otherwise>
                                <li class="page-item mr-2">
                                    <a class="page-link" href="main?command=questions&page=1">
                                        1
                                    </a>
                                </li>
                                <c:forEach var="index" begin="${requestScope.currentPage - requestScope.offset}"
                                           end="${requestScope.currentPage - 1}">
                                    <li class="page-item">
                                        <a class="page-link"
                                           href="main?command=questions&page=<c:out value="${index}"/>">
                                            <c:out value="${index}"/>
                                        </a>
                                    </li>
                                </c:forEach>
                                <li class="page-item active">
                                    <a class="page-link"
                                       href="main?command=questions&page=<c:out value="${requestScope.currentPage}"/>">
                                        <c:out value="${requestScope.currentPage}"/>
                                    </a>
                                </li>
                                <c:choose>
                                    <c:when test="${(requestScope.lastPage - requestScope.currentPage) > requestScope.offset}">
                                        <c:forEach var="index" begin="1" end="${requestScope.offset}">
                                            <li class="page-item">
                                                <a class="page-link"
                                                   href="main?command=questions&page=<c:out value="${requestScope.currentPage + index}"/>">
                                                    <c:out value="${requestScope.currentPage + index}"/>
                                                </a>
                                            </li>
                                        </c:forEach>
                                        <li class="page-item ml-2">
                                            <a class="page-link"
                                               href="main?command=questions&page=<c:out value="${requestScope.lastPage}"/>">
                                                <c:out value="${requestScope.lastPage}"/>
                                            </a>
                                        </li>
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach var="index" begin="${requestScope.currentPage + 1}"
                                                   end="${requestScope.lastPage}">
                                            <li class="page-item">
                                                <a class="page-link"
                                                   href="main?command=questions&page=<c:out value="${index}"/>">
                                                    <c:out value="${index}"/>
                                                </a>
                                            </li>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                            </c:otherwise>
                        </c:choose>
                    </ul>
                </nav>
            </c:when>
            <c:otherwise>
                <h3 class="text-muted">0 results</h3>
            </c:otherwise>
        </c:choose>
    </div>
</main>

<script type="text/javascript" src="js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="js/popper.js"></script>
<script type="text/javascript" src="js/bootstrap.js"></script>
<script type="text/javascript" src="js/questionnaire.js"></script>
</body>
</html>
