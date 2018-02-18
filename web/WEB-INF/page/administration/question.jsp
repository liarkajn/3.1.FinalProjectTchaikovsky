<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Question</title>
    <link rel="stylesheet" type="text/css" href="css/normalize.css">
    <link rel="stylesheet" type="text/css" href="css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="css/questionnaire.css">
</head>
<body>


<jsp:include page="navbar.jsp"/>

<main class="content">

    <div class="container">
        <div class="row">
            <div class="col-7 card m-auto">
                <form action="administration" method="get">
                    <br>
                    <c:set var="question" scope="request" value="${requestScope.question}"/>
                    <input type="hidden" name="command" value="question_editing"/>
                    <input type="hidden" name="id" value="${question.id}"/>
                    <div class="form-row">
                        <div class="col-12 mb-3">
                            <label for="topic">Topic</label>
                            <input type="text" id="topic" class="form-control" name="topic"
                                   value="${question.topic}" minlength="10" required/>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="col-12 mb-3">
                            <label for="content">Question</label>
                            <textarea id="content" name="content" class="form-control" minlength="50" required><c:out
                                    value="${requestScope.question.content}"/></textarea>
                        </div>
                    </div>
                    <a class="btn btn-danger" href="?command=question_deleting&id=${requestScope.question.id}">
                        Delete
                    </a>
                    <input class="btn btn-info" type="submit" value="Edit"/>
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
