<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
    <title>Control panel</title>
    <fmt:setLocale value="${sessionScope.local}" />
    <fmt:setBundle basename="local" var="loc" />
</head>
<body>

    <h2>Control panel</h2><br/>
    <div>
        <h2>Name</h2>

        <table>
            <thead>
            <tr>
                <td>Name : </td>
                <td>Role : </td>
                <td>
                    <input type="submit" name="Delete" />
                </td>
            </tr>
            </thead>

            <c:if test="${requestScope.users != null}">
                <c:forEach items="${requestScope.users}" var="user" >
                    <tr>
                        <td>
                            <c:out value="${user.login}" /> <br/>
                            <c:out value="${user.name}" /> <br/>
                            <c:out value="${user.surname}" /> <br/>
                        </td>
                    </tr>
                </c:forEach>
            </c:if>

        </table>

    </div>

</body>
</html>
