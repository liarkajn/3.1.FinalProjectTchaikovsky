<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Ask a question</title>
    <link rel="stylesheet" type="text/css" href="css/main.css">

    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.question.topic.placeholder" var="topicPlaceholder"/>
    <fmt:message bundle="${loc}" key="local.question.content.placeholder" var="contentPlaceholder"/>
    <fmt:message bundle="${loc}" key="local.question.askbutton.name" var="askBtnName"/>

</head>
<body>

<jsp:include page="navbar.jsp"/>

<div align="center">
    <div class="card card-1">
        <form action="main" method="get">
            <input type="hidden" name="command" value="after_question_creation"/>
            <c:if test="${requestScope.questionId != null}">
                <input type="hidden" name="question_id" value="${requestScope.questionId}" />
                <input type="text" name="topic" placeholder="${topicPlaceholder}" value="${requestScope.topic}"/> <br/>
                <textarea name="content" placeholder="${contentPlaceholder}">
                    <c:out value="${requestScope.content}" />
                </textarea> <br/>
            </c:if>
            <c:if test="${requestScope.questionId == null}">
                <input type="text" name="topic" placeholder="${topicPlaceholder}"/> <br/>
                <textarea name="content" placeholder="${contentPlaceholder}"></textarea> <br/>
            </c:if>
            <input type="submit" value="${askBtnName}"/>
        </form>
    </div>
</div>

</body>
</html>
