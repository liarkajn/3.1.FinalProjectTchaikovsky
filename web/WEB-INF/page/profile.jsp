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
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.profile.loginfield.name" var="loginFieldName"/>
    <fmt:message bundle="${loc}" key="local.profile.registrationdatefield.name" var="registrationDateFieldName"/>
    <fmt:message bundle="${loc}" key="local.profile.namefield.name" var="nameFieldName"/>
    <fmt:message bundle="${loc}" key="local.profile.surnamefield.name" var="surnameFieldName"/>
    <fmt:message bundle="${loc}" key="local.profile.rolefield.name" var="roleFieldName"/>
</head>
<body>

    <jsp:include page="navbar.jsp"/>

    <div align="center">
        <div class="card">
            <div class="label"> ${loginFieldName} : <c:out value="${requestScope.user.login}" /> </div> <br/>
            E-mail : <c:out value="${requestScope.user.email}" /> <br/>
            ${registrationDateFieldName} : <c:out value="${requestScope.user.registrationDate}" /> <br/>
            <c:if test="${requestScope.user.name != null && requestScope.user.surname != null}">
                ${nameFieldName} : <c:out value="${requestScope.user.name}" /> <br/>
                ${surnameFieldName} : <c:out value="${requestScope.user.surname}" /> <br/>
            </c:if>
            ${roleFieldName} : <c:out value="${requestScope.user.role}" /> <br/>
        </div>
    </div>

</body>
</html>
