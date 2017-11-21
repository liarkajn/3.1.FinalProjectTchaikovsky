<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
  <head>
    <title>LikeIT</title>

    <fmt:setLocale value="${sessionScope.local}" />
    <fmt:setBundle basename="local" var="loc" />
    <fmt:message bundle="${loc}" key="local.signinbutton.name" var="signInButtonName" />
    <fmt:message bundle="${loc}" key="local.signupbutton.name" var="signUpButtonName" />

  </head>
  <body>

    <div align="center">

      <form action="main" method="post">
        <input type="hidden" name="local" value="ru" />
        <input type="submit" value="Русский" />
      </form>

      <form action="main" method="post">
        <input type="hidden" name="local" value="en" />
        <input type="submit" value="English" />
      </form>

    </div>

    <div align="center">

      <form action="main" method="get">
        <input type="hidden" name="command" value="user" />
        <input type="hidden" name="action" value="authorization" />
        <input type="submit" value="${signInButtonName}" />
      </form>


      <form action="main" method="get">
        <input type="hidden" name="command" value="user" />
        <input type="hidden" name="action" value="registration" />
        <input type="submit" value="${signUpButtonName}" />
      </form>

    </div>

  </body>
</html>
