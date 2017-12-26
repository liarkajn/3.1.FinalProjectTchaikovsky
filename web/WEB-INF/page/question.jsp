<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>
        <c:out value="${requestScope.question.topic}"/>
    </title>
    <link rel="stylesheet" type="text/css" href="css/main.css">
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.question.answer.content.placeholder" var="answerPlaceholder"/>
    <fmt:message bundle="${loc}" key="local.question.answerbutton.name" var="answerBtnName"/>
    <fmt:message bundle="${loc}" key="local.question.editbutton.name" var="editBtn"/>
</head>
<body>

<jsp:include page="navbar.jsp"/>

<div align="center">
    <div class="card card-1">
        <h1><c:out value="${requestScope.question.topic}"/></h1> <br/>
        <c:out value="${requestScope.question.content}"/> <br/>
        <c:out value="${requestScope.question.publishDate}"/> <br/>
        <c:out value="${requestScope.question.author.login}"/> <br/>
        <c:if test="${requestScope.question.author.id == sessionScope.id}">
            <form action="main" method="get">
                <input type="hidden" name="command" value="question_creation">
                <input type="hidden" name="question_id" value="${requestScope.question.id}" />
                <input type="submit" value="${editBtn}" />
            </form>
        </c:if>
    </div>

    <c:if test="${sessionScope.id != null && requestScope.question.author.id != sessionScope.id}">
        <div class="card card-1">
            <form action="main" method="get">
                <input type="hidden" name="command" value="after_answer_creation"/>
                <input type="hidden" name="question_id" value="${requestScope.question.id}"/>
                <textarea name="content" placeholder="${answerPlaceholder}"></textarea> <br/>
                <input type="submit" value="${answerBtnName}"/>
            </form>
        </div>
    </c:if>

    <c:if test="${requestScope.answers != null}">
        <c:forEach items="${requestScope.answers}" var="answer">
            <div class="card card-1">
                <div id="${answer.id}">
                    <c:out value="${answer.author.login}"/> <br/>
                    <div class="content">
                        <c:out value="${answer.content}"/>
                    </div>
                    <c:out value="${answer.publishDate}"/>
                    <c:if test="${answer.author.id == sessionScope.id}">
                        <input type="submit" value="${editBtn}" onclick="edit(event)"/>
                    </c:if> <br/>
                    <c:if test="${sessionScope.id != null && answer.author.id != sessionScope.id}">
                        <form action="main" method="get">
                            <input type="hidden" name="command" value="vote_creation"/>
                            <input type="hidden" name="question_id" value="${requestScope.question.id}"/>
                            <input type="hidden" name="answer_id" value="${answer.id}"/>
                            <input type="number" name="vote" value="0"/>
                            <input type="submit" value="Vote"/>
                        </form>
                    </c:if>
                </div>
            </div>
        </c:forEach>
    </c:if>

</div>

<script type="text/javascript">

    function edit(event) {

        var form = document.createElement('form');
        form.action = 'main';
        form.method = 'get';

        var current = event.currentTarget.parentNode;
        var content = current.getElementsByClassName("content")[0].innerHTML;

        var editBox = document.createElement('input');
        editBox.type = 'text';
        editBox.name = 'content';
        editBox.value = content;

        var submitBtn = document.createElement('input');
        submitBtn.type = 'submit';

        var command = document.createElement('input');
        command.type = 'hidden';
        command.name = 'command';
        command.value = 'answer_editing';

        var answerId = document.createElement('input');
        answerId.type = 'hidden';
        answerId.name = 'answer_id';
        answerId.value = current.id;

        form.appendChild(editBox);
        form.appendChild(command);
        form.appendChild(answerId);
        form.appendChild(document.createElement('br'));
        form.appendChild(submitBtn);

        current.parentNode.insertBefore(form, current);
        current.style.visibility = 'hidden';
    }

</script>

</body>
</html>
