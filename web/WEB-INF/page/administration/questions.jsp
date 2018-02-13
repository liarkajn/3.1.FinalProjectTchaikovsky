<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Questions</title>
    <link rel="stylesheet" href="css/normalize.css">
    <link rel="stylesheet" type="text/css" href="css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="css/questionnaire.css">
</head>
<body>
<jsp:include page="navbar.jsp"/>
<main>
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
                    <c:set var="number" value="${number + 1}" />
                    <td><a href="administration?command=question&id=${question.id}"><c:out value="${question.topic}"/></a></td>
                    <td><c:out value="${question.content}"/></td>
                    <td><a href="administration?command=user&id=${question.author.id}"><c:out value="${question.author.login}"/></a></td>
                    <td><c:out value="${question.publishDate}"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
</main>
</body>
</html>
