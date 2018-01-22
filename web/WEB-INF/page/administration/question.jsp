<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Question</title>
</head>
<body>


<jsp:include page="navbar.jsp" />

<main>

    <form action="/administration" method="get">
        <c:set var="question" scope="request" value="${requestScope.question}"/>
        <input type="hidden" name="command" value="question_editing"/>
        <input type="hidden" name="id" value="${question.id}"/>
        <div>
            Topic:
            <input type="text" name="topic" value="${question.topic}" readonly/>
            <input type="button" value="Edit" onclick="edit(event)"/>
        </div>
        <div>
            Content:
            <input type="text" name="content" value="${question.content}" readonly/>
            <input type="button" value="Edit" onclick="edit(event)"/>
        </div>
        <input type="submit" value="Edit" />
    </form>

</main>

<script>

    function edit(event) {
        var current = event.currentTarget;
        var input = current.previousElementSibling;
        input.readOnly = false;

        current.value = "OK";
        current.addEventListener("click", endEdit, true);
    }

    function endEdit(event) {
        var current = event.currentTarget;
        var input = current.previousElementSibling;
        input.readOnlyOnly = true;

        current.value = "Edit";
        current.addEventListener("click", edit, true);
    }

</script>

</body>
</html>
