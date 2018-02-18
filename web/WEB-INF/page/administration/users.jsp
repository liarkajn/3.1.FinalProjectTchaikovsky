<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Users</title>
    <link rel="stylesheet" type="text/css" href="css/normalize.css">
    <link rel="stylesheet" type="text/css" href="css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="css/questionnaire.css">
</head>
<body>

<jsp:include page="navbar.jsp"/>

<main class="content">

    <div class="container">
        <c:if test="${requestScope.users != null}">
            <c:set var="users" scope="request" value="${requestScope.users}"/>
            <table class="table table-hover">
                <thead>
                <tr>
                    <th scope="col">Login</th>
                    <th scope="col">Email</th>
                    <th scope="col">Name</th>
                    <th scope="col">Surname</th>
                    <th scope="col">Registration date</th>
                </tr>
                </thead>
                <c:forEach items="${users}" var="user">
                    <tr>
                        <td><a href="administration?command=user&id=${user.id}"><c:out value="${user.login}"/></a></td>
                        <td><c:out value="${user.email}"/></td>
                        <td><c:out value="${user.name}"/></td>
                        <td><c:out value="${user.surname}"/></td>
                        <td><c:out value="${user.registrationDate.toLocalDate()}"/> <c:out value="${user.registrationDate.toLocalTime()}"/></td>
                    </tr>
                </c:forEach>
            </table>

            <nav>
                <ul class="pagination">
                    <c:choose>
                        <c:when test="${(requestScope.currentPage - 1) <= requestScope.offset}">
                            <c:forEach var="index" begin="1" end="${requestScope.currentPage - 1}">
                                <li class="page-item">
                                    <a class="page-link"
                                       href="?command=users&page=<c:out value="${index}"/>">
                                        <c:out value="${index}"/>
                                    </a>
                                </li>
                            </c:forEach>
                            <li class="page-item active">
                                <a class="page-link"
                                   href="?command=users&page=<c:out value="${requestScope.currentPage}"/>">
                                    <c:out value="${requestScope.currentPage}"/>
                                </a>
                            </li>
                            <c:choose>
                                <c:when test="${(requestScope.lastPage - requestScope.currentPage) > requestScope.offset}">
                                    <c:forEach var="index" begin="1" end="${requestScope.offset}">
                                        <li class="page-item">
                                            <a class="page-link"
                                               href="?command=users&page=<c:out value="${requestScope.currentPage + index}"/>">
                                                <c:out value="${requestScope.currentPage + index}"/>
                                            </a>
                                        </li>
                                    </c:forEach>
                                    <li class="page-item ml-2">
                                        <a class="page-link"
                                           href="?command=users&page=<c:out value="${requestScope.lastPage}"/>">
                                            <c:out value="${requestScope.lastPage}"/>
                                        </a>
                                    </li>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach var="index" begin="${requestScope.currentPage + 1}"
                                               end="${requestScope.lastPage}">
                                        <li class="page-item">
                                            <a class="page-link"
                                               href="?command=users&page=<c:out value="${index}"/>">
                                                <c:out value="${index}"/>
                                            </a>
                                        </li>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                        </c:when>
                        <c:otherwise>
                            <li class="page-item mr-2">
                                <a class="page-link" href="?command=users&page=1">
                                    1
                                </a>
                            </li>
                            <c:forEach var="index" begin="${requestScope.currentPage - requestScope.offset}"
                                       end="${requestScope.currentPage - 1}">
                                <li class="page-item">
                                    <a class="page-link"
                                       href="?command=users&page=<c:out value="${index}"/>">
                                        <c:out value="${index}"/>
                                    </a>
                                </li>
                            </c:forEach>
                            <li class="page-item active">
                                <a class="page-link"
                                   href="?command=users&page=<c:out value="${requestScope.currentPage}"/>">
                                    <c:out value="${requestScope.currentPage}"/>
                                </a>
                            </li>
                            <c:choose>
                                <c:when test="${(requestScope.lastPage - requestScope.currentPage) > requestScope.offset}">
                                    <c:forEach var="index" begin="1" end="${requestScope.offset}">
                                        <li class="page-item">
                                            <a class="page-link"
                                               href="?command=users&page=<c:out value="${requestScope.currentPage + index}"/>">
                                                <c:out value="${requestScope.currentPage + index}"/>
                                            </a>
                                        </li>
                                    </c:forEach>
                                    <li class="page-item ml-2">
                                        <a class="page-link"
                                           href="?command=users&page=<c:out value="${requestScope.lastPage}"/>">
                                            <c:out value="${requestScope.lastPage}"/>
                                        </a>
                                    </li>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach var="index" begin="${requestScope.currentPage + 1}"
                                               end="${requestScope.lastPage}">
                                        <li class="page-item">
                                            <a class="page-link"
                                               href="?command=users&page=<c:out value="${index}"/>">
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

        </c:if>

    </div>

</main>
<script type="text/javascript" src="js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="js/popper.js"></script>
<script type="text/javascript" src="js/bootstrap.js"></script>
</body>
</html>
