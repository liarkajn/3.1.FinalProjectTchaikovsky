<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Ask a question</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="css/normalize.css">
    <link rel="stylesheet" type="text/css" href="css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="css/questionnaire.css">

    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.question.topic.placeholder" var="topicPlaceholder"/>
    <fmt:message bundle="${loc}" key="local.question.content.placeholder" var="contentPlaceholder"/>
    <fmt:message bundle="${loc}" key="local.question.askbutton.name" var="askBtnName"/>

</head>
<body>

<jsp:include page="navbar.jsp"/>

<main class="content">
    <div class="container">
        <div class="row">
            <div class="col-12 col-md-10 col-lg-7 card m-auto">
                <form action="main" method="get">
                    <br>
                    <c:if test="${requestScope.question != null}">
                        <input type="hidden" name="command" value="question_editing"/>
                        <input type="hidden" name="question_id" value="${requestScope.question.id}"/>
                        <div class="form-row">
                            <div class="col-12 mb-3">
                                <label for="topic_editing">Topic</label>
                                <input type="text" id="topic_editing" class="form-control" name="topic"
                                       placeholder="${topicPlaceholder}"
                                       value="${requestScope.question.topic}" minlength="10" required/>
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="col-12 mb-3">
                                <label for="content_editing">Question</label>
                                <textarea id="content_editing" name="content" class="form-control"
                                          placeholder="${contentPlaceholder}" minlength="50" required><c:out
                                        value="${requestScope.question.content}"/></textarea>
                            </div>
                        </div>
                        <a class="btn btn-danger" href="main?command=question_deleting&id=${requestScope.question.id}">
                            Delete
                        </a>
                        <input class="btn btn-info" type="submit" value="Edit"/>
                    </c:if>
                    <c:if test="${requestScope.question == null}">
                        <input type="hidden" name="command" value="question_creation"/>
                        <div class="form-row">
                            <div class="col-12 mb-3">
                                <label for="topic">Topic</label>
                                <input type="text" class="form-control" id="topic" name="topic"
                                       placeholder="${topicPlaceholder}" minlength="10" required/>
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="col-12 mb-3">
                                <label for="content">Question</label>
                                <textarea class="form-control" id="content" name="content"
                                          placeholder="${contentPlaceholder}" minlength="50" required></textarea>
                            </div>
                        </div>
                        <input type="submit" class="btn btn-info" value="${askBtnName}"/>
                    </c:if>
                </form>
            </div>
        </div>
    </div>
</main>

<script type="text/javascript" src="js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="js/popper.js"></script>
<script type="text/javascript" src="js/bootstrap.js"></script>
</body>
</html>
