<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
    <title>Ask a question</title>
    <link rel="stylesheet" type="text/css" href="css/main.css">

    <fmt:setLocale value="${sessionScope.local}" />
    <fmt:setBundle basename="local" var="loc" />
    <fmt:message bundle="${loc}" key="local.question.topic.placeholder" var="topicPlaceholder" />
    <fmt:message bundle="${loc}" key="local.question.content.placeholder" var="contentPlaceholder" />
    <fmt:message bundle="${loc}" key="local.question.askbutton.name" var="askBtnName" />

</head>
<body>

    <jsp:include page="navbar.jsp"/>

    <div align="center">
        <form action="main" method="get">
            <input type="hidden" name="command" value="after_question_creation" />
            <input type="text" name="topic" placeholder="${topicPlaceholder}" /> <br/>
            <textarea name="content" placeholder="${contentPlaceholder}"></textarea> <br/>
            <input type="submit" value="${askBtnName}" />
        </form>
    </div>

</body>
</html>
