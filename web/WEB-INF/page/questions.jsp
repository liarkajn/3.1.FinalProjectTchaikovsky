<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Main page</title>
    <link rel="stylesheet" href="css/normalize.css">
    <%--<link rel="stylesheet" href="css/questionnaire.css">--%>
    <link rel="stylesheet" type="text/css" href="css/main.css">

    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="local" var="loc"/>
</head>
<body>

<div align="center">

    <jsp:include page="navbar.jsp"/>

    <c:if test="${requestScope.questions != null}">
        <c:set var="questions" scope="request" value="${requestScope.questions}"/>
        <c:forEach items="${questions}" var="question">
            <div class="card card-1">
                <div align="center">
                    <a href="main?command=question&id=${question.id}"><c:out value="${question.topic}"/></a> <br/>
                    <c:out value="${question.content}"/> <br/>
                    <c:out value="${question.publishDate}"/> <br/>
                    <a href="main?command=profile&id=${question.author.id}"><c:out value="${question.author.login}"/></a> <br/>
                </div>
            </div>
        </c:forEach>
    </c:if>
</div>

</body>
</html>
