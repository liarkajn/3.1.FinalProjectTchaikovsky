<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Questions</title>
    <link rel="stylesheet" type="text/css" href="css/normalize.css">
    <link rel="stylesheet" type="text/css" href="css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="css/questionnaire.css">
</head>
<body>

<jsp:include page="navbar.jsp"/>

<main class="content">

    <div class="container">
        <c:if test="${requestScope.questions != null}">
            <c:set var="questions" scope="request" value="${requestScope.questions}"/>
            <table class="table table-hover">
                <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Topic</th>
                    <th scope="col">Content</th>
                    <th scope="col">Author</th>
                    <th scope="col">Publish date</th>
                </tr>
                </thead>
                <tbody>
                <c:set var="number" value="1"/>
                <c:forEach items="${questions}" var="question">
                    <tr>
                        <th scope="row"><c:out value="${number}"/></th>
                        <c:set var="number" value="${number + 1}"/>
                        <td><a href="administration?command=question&id=${question.id}"><c:out
                                value="${question.topic}"/></a></td>
                        <td><c:out value="${question.content}"/></td>
                        <td><a href="administration?command=user&id=${question.author.id}"><c:out
                                value="${question.author.login}"/></a></td>
                        <td><c:out value="${question.publishDate.toLocalDate()}"/> <c:out value="${question.publishDate.toLocalTime()}"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>

        <nav>
            <ul class="pagination">
                <c:choose>
                    <c:when test="${(requestScope.currentPage - 1) <= requestScope.offset}">
                        <c:forEach var="index" begin="1" end="${requestScope.currentPage - 1}">
                            <li class="page-item">
                                <a class="page-link"
                                   href="?command=questions&page=<c:out value="${index}"/>">
                                    <c:out value="${index}"/>
                                </a>
                            </li>
                        </c:forEach>
                        <li class="page-item active">
                            <a class="page-link"
                               href="?command=questions&page=<c:out value="${requestScope.currentPage}"/>">
                                <c:out value="${requestScope.currentPage}"/>
                            </a>
                        </li>
                        <c:choose>
                            <c:when test="${(requestScope.lastPage - requestScope.currentPage) > requestScope.offset}">
                                <c:forEach var="index" begin="1" end="${requestScope.offset}">
                                    <li class="page-item">
                                        <a class="page-link"
                                           href="?command=questions&page=<c:out value="${requestScope.currentPage + index}"/>">
                                            <c:out value="${requestScope.currentPage + index}"/>
                                        </a>
                                    </li>
                                </c:forEach>
                                <li class="page-item ml-2">
                                    <a class="page-link"
                                       href="?command=questions&page=<c:out value="${requestScope.lastPage}"/>">
                                        <c:out value="${requestScope.lastPage}"/>
                                    </a>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="index" begin="${requestScope.currentPage + 1}"
                                           end="${requestScope.lastPage}">
                                    <li class="page-item">
                                        <a class="page-link"
                                           href="?command=questions&page=<c:out value="${index}"/>">
                                            <c:out value="${index}"/>
                                        </a>
                                    </li>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:otherwise>
                        <li class="page-item mr-2">
                            <a class="page-link" href="?command=questions&page=1">
                                1
                            </a>
                        </li>
                        <c:forEach var="index" begin="${requestScope.currentPage - requestScope.offset}"
                                   end="${requestScope.currentPage - 1}">
                            <li class="page-item">
                                <a class="page-link"
                                   href="?command=questions&page=<c:out value="${index}"/>">
                                    <c:out value="${index}"/>
                                </a>
                            </li>
                        </c:forEach>
                        <li class="page-item active">
                            <a class="page-link"
                               href="?command=questions&page=<c:out value="${requestScope.currentPage}"/>">
                                <c:out value="${requestScope.currentPage}"/>
                            </a>
                        </li>
                        <c:choose>
                            <c:when test="${(requestScope.lastPage - requestScope.currentPage) > requestScope.offset}">
                                <c:forEach var="index" begin="1" end="${requestScope.offset}">
                                    <li class="page-item">
                                        <a class="page-link"
                                           href="?command=questions&page=<c:out value="${requestScope.currentPage + index}"/>">
                                            <c:out value="${requestScope.currentPage + index}"/>
                                        </a>
                                    </li>
                                </c:forEach>
                                <li class="page-item ml-2">
                                    <a class="page-link"
                                       href="?command=questions&page=<c:out value="${requestScope.lastPage}"/>">
                                        <c:out value="${requestScope.lastPage}"/>
                                    </a>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="index" begin="${requestScope.currentPage + 1}"
                                           end="${requestScope.lastPage}">
                                    <li class="page-item">
                                        <a class="page-link"
                                           href="?command=questions&page=<c:out value="${index}"/>">
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

    </div>

</main>

<script type="text/javascript" src="js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="js/popper.js"></script>
<script type="text/javascript" src="js/bootstrap.js"></script>
</body>
</html>
