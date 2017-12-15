<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="local" var="loc"/>
<fmt:message bundle="${loc}" key="local.signinbutton.name" var="signInButtonName"/>
<fmt:message bundle="${loc}" key="local.signupbutton.name" var="signUpButtonName"/>
<fmt:message bundle="${loc}" key="local.question.askbutton.name" var="askBtnName"/>


<div align="center">
    <div class="nav">
        <div class="logo">
            <form action="main" method="get">
                <input type="submit" class="min-btn" value="LOGO"/>
            </form>
        </div>
        <c:if test="${sessionScope.login == null}">
            <div class="tab">
                <form action="main" method="get">
                    <input type="hidden" name="command" value="signIn"/>
                    <input type="submit" class="min-btn" value="${signInButtonName}"/>
                </form>
            </div>
            <div class="tab">
                <form action="main" method="get">
                    <input type="hidden" name="command" value="signUp"/>
                    <input type="submit" class="min-btn" value="${signUpButtonName}"/>
                </form>
            </div>
        </c:if>
        <c:if test="${sessionScope.login != null}">
            <div class="tab">
                <form action="main" method="get">
                    <input type="hidden" name="command" value="logout"/>
                    <input type="submit" class="min-btn tab" value="Log out"/>
                </form>
            </div>
            <div class="tab">
                <form action="main" method="get">
                    <input type="hidden" name="command" value="profile"/>
                    <input type="hidden" name="id" value="${sessionScope.login}"/>
                    <input type="submit" class="min-btn tab" value="Profile"/>
                </form>
            </div>
            <div class="tab">
                <form action="main" method="get">
                    <input type="hidden" name="command" value="question_creation"/>
                    <input type="submit" class="min-btn" value="${askBtnName}"/>
                </form>
            </div>
        </c:if>
    </div>
</div>

