<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="local" var="loc"/>
<fmt:message bundle="${loc}" key="local.adminpanelbutton.name" var="adminPanel"/>
<fmt:message bundle="${loc}" key="local.searchinput.placeholder" var="searchPlaceholder"/>
<fmt:message bundle="${loc}" key="local.searchbutton.name" var="searchBtn"/>
<fmt:message bundle="${loc}" key="local.signinbutton.name" var="signInButtonName"/>
<fmt:message bundle="${loc}" key="local.signupbutton.name" var="signUpButtonName"/>
<fmt:message bundle="${loc}" key="local.question.askbutton.name" var="askBtnName"/>
<fmt:message bundle="${loc}" key="local.profilebutton.name" var="profileBtnName"/>
<fmt:message bundle="${loc}" key="local.logoutbutton.name" var="logOutBtnName"/>

<div align="center">
    <div class="nav">
        <div class="logo">
            <form action="main" method="get">
                <input type="submit" class="min-btn" value="LOGO"/>
            </form>
        </div>
        <c:if test="${sessionScope.id == null}">
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
        <c:if test="${sessionScope.admin != null}">
            <div class="tab">
                <form action="main" method="get">
                    <input type="hidden" name="command" value="administration_panel"/>
                    <input type="submit" class="min-btn tab" value="${adminPanel}"/>
                </form>
            </div>
        </c:if>
        <c:if test="${sessionScope.id != null}">
            <div class="tab">
                <form action="main" method="get">
                    <input type="hidden" name="command" value="logout"/>
                    <input type="submit" class="min-btn tab" value="${logOutBtnName}"/>
                </form>
            </div>
            <div class="tab">
                <form action="main" method="get">
                    <input type="hidden" name="command" value="profile"/>
                    <input type="hidden" name="id" value="${sessionScope.id}"/>
                    <input type="submit" class="min-btn tab" value="${profileBtnName}"/>
                </form>
            </div>
            <div class="tab">
                <form action="main" method="get">
                    <input type="hidden" name="command" value="question_creation"/>
                    <input type="submit" class="min-btn" value="${askBtnName}"/>
                </form>
            </div>
        </c:if>
        <div class="tab">
            <form action="main" method="get">
                <input type="hidden" name="command" value="questions"/>
                <input type="text" name="search_string" placeholder="${searchPlaceholder}"/>
                <input type="submit" class="min-btn" value="${searchBtn}" />
            </form>
        </div>
    </div>
    <div>
        <form action="main" method="post">
            <input type="hidden" name="local" value="ru"/>
            <input type="submit" class="min-btn" value="Русский"/>
        </form>
        <form action="main" method="post">
            <input type="hidden" name="local" value="en"/>
            <input type="submit" class="min-btn" value="English"/>
        </form>
    </div>
</div>

