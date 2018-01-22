<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Users</title>
</head>
<body>


<jsp:include page="navbar.jsp" />

<main>

    <c:if test="${requestScope.users != null}">
        <c:set var="users" scope="request" value="${requestScope.users}"/>
        <table>
            <c:forEach items="${users}" var="user">
                <tr>
                    <td><a href="administration?command=user&id=${user.id}"><c:out value="${user.login}"/></a></td>
                    <td><c:out value="${user.email}"/></td>
                    <td><c:out value="${user.name}"/></td>
                    <td><c:out value="${user.surname}"/></td>
                    <td><c:out value="${user.registrationDate}"/></td>
                    <td><c:out value="${user.bio}"/></td>
                </tr>
            </c:forEach>
        </table>
    </c:if>

</main>

</body>
</html>
