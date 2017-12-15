<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
    <title>
        <c:choose>
            <c:when test="${requestScope.user.name != null && requestScope.user.surname != null}">
                <c:out value="${requestScope.user.name} ${requestScope.user.surname} (${requestScope.user.login})" />
            </c:when>

            <c:otherwise>
                <c:out value="${requestScope.user.login}" />
            </c:otherwise>

        </c:choose>
    </title>
    <link rel="stylesheet" type="text/css" href="css/main.css">
</head>
<body>

    <jsp:include page="navbar.jsp"/>

    <div align="center">
        <div class="card">
            <div class="label"> Login : <c:out value="${requestScope.user.login}" /> </div> <br/>
            E-mail : <c:out value="${requestScope.user.email}" /> <br/>
            Registration date : <c:out value="${requestScope.user.registrationDate}" /> <br/>
            Name : <c:out value="${requestScope.user.name}" /> <br/>
            Surname : <c:out value="${requestScope.user.surname}" /> <br/>
            Role : <c:out value="${requestScope.user.role}" /> <br/>
        </div>
    </div>

</body>
</html>
