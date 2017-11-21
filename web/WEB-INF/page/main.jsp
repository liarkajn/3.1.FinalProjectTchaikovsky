<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
    <title>Main page</title>

    <fmt:setLocale value="${sessionScope.local}" />
    <fmt:setBundle basename="local" var="loc" />
    <fmt:message bundle="${loc}" key="local.welcome.message" var="welcomeMessage" />
    <fmt:message bundle="${loc}" key="local.question.askbutton.name" var="askbtnName" />

</head>
<body>

    <div align="center">

        <h2> <c:out value="${welcomeMessage}" />, <c:out value="${sessionScope.login}" /></h2>

        <form action="main" method="get">
            <input type="hidden" name="command" value="question" />
            <input type="hidden" name="action" value="ask" />
            <input type="submit" value="${askbtnName}" />
        </form>

        <c:if test="${requestScope.questions != null}">
            <c:set var="questions" scope="request" value="${requestScope.questions}"/>
            <table border="1">
                <tr>
                    <td>Topic</td>
                    <td>Question</td>
                    <td>Author</td>
                    <td>Publish date</td>
                </tr>
                <c:forEach items="${questions}" var="question">
                    <tr>
                        <td><c:out value="${question.topic}" /></td>
                        <td><c:out value="${question.content}" /></td>
                        <td><c:out value="${question.author.login}" /></td>
                        <td><c:out value="${question.publishDate}" /></td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>

    </div>

</body>
</html>
