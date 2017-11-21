<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>ERROR</title>
</head>
<body>

    <div align="center">
        <label>Oops! Something went wrong. Please try again later. :)</label><br/>
        <c:if test="${requestScope.exception != null}">
            <label><c:out value="${requestScope.exception.message}"/></label>
        </c:if>
    </div>

</body>
</html>
