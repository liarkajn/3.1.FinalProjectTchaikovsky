<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Questions</title>
</head>
<body>


<jsp:include page="navbar.jsp"/>

<main>

    <c:if test="${requestScope.questions != null}">
        <c:set var="questions" scope="request" value="${requestScope.questions}"/>
        <table>
            <c:forEach items="${questions}" var="question">
                <tr>
                    <td><a href="administration?command=question&id=${question.id}"><c:out value="${question.topic}"/></a></td>
                    <td><c:out value="${question.content}"/></td>
                    <td><a href="administration?command=user&id=${question.author.id}"><c:out value="${question.author.login}"/></a></td>
                    <td><c:out value="${question.publishDate}"/></td>
                </tr>
            </c:forEach>
        </table>
    </c:if>

</main>

</body>
</html>
