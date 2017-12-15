<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
    <title>
        <c:out value="${requestScope.question.topic}" />
    </title>
    <link rel="stylesheet" type="text/css" href="css/main.css">
</head>
<body>

    <jsp:include page="navbar.jsp"/>

    <div align="center">
        <div class="card card-1">
            <h1><c:out value="${requestScope.question.topic}" /></h1> <br/>
            <c:out value="${requestScope.question.content}" /> <br/>
            <c:out value="${requestScope.question.publishDate}" /> <br/>
            <c:out value="${requestScope.question.author.login}" /> <br/>
        </div>

        <div class="card card-1">
            <textarea name="content" placeholder="Your answer"></textarea> <br/>
            <input type="submit" value="Answer"/>
        </div>
    </div>

</body>
</html>
