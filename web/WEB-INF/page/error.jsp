<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>ERROR</title>
</head>
<body>

    <div align="center">
        <label>Oops! Something went wrong. Please try again later. :)</label><br/>
        <label><c:out value="${requestScope.message}"/></label>
    </div>

</body>
</html>
