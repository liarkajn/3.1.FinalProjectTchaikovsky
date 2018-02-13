<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>User</title>
</head>
<body>


<jsp:include page="navbar.jsp"/>

<main>
    <form action="/administration" method="get">
        <c:set var="user" scope="request" value="${requestScope.user}"/>
        <input type="hidden" name="command" value="user_editing"/>
        <input type="hidden" name="id" value="${user.id}"/>
        <div>
            Login:
            <input type="text" name="login" value="${user.login}" readonly/>
            <input type="button" value="Edit" onclick="edit(event)"/>
        </div>
        <div>
            Name:
            <input type="text" name="name" value="${user.name}" readonly/>
            <input type="button" value="Edit" onclick="edit(event)"/>
        </div>
        <div>
            Surname:
            <input type="text" name="surname" value="${user.surname}" readonly/>
            <input type="button" value="Edit" onclick="edit(event)"/>
        </div>
        <div>
            Registration Date:
            <input type="text" name="email" value="${user.email}" readonly/>
            <input type="button" value="Edit" onclick="edit(event)"/>
        </div>
        <div>
            Bio:
            <input type="text" name="boi" value="${user.bio}" readonly/>
            <input type="button" value="Edit" onclick="edit(event)"/>
        </div>
        <div>
            Role:
            <input type="text" name="role" value="${user.role}" readonly/>
            <input type="button" value="Edit" onclick="edit(event)"/>
        </div>
        <input type="submit" value="Edit" />
    </form>
    <c:choose>
        <c:when test="${requestScope.user.banned == false}">
            <a href="/administration?command=ban&ban=true&id=<c:out value="${requestScope.user.id}"/>">ban</a>
        </c:when>
        <c:otherwise>
            <a href="/administration?command=ban&ban=false&id=<c:out value="${requestScope.user.id}"/>">unban</a>
        </c:otherwise>
    </c:choose>
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
