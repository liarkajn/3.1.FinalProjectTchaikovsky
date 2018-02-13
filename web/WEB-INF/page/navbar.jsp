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

<header>
<nav class="navbar navbar-expand-lg navbar-dark bg-default no-paddings">
    <div class="container">
        <a class="navbar-brand" href="main?command=questions">LikeIT</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <c:if test="${sessionScope.id != null}">
                <ul class="navbar-nav">
                    <li class="nav-item active">
                        <a class="nav-link" href="main?command=profile&id=${sessionScope.id}"><c:out value="${profileBtnName}"/><span class="sr-only">(current)</span></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">My questions</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link " href="main?command=go_to_question_creation"><c:out value="${askBtnName}"/></a>
                    </li>
                </ul>
            </c:if>
            <form class="form-inline my-2 my-lg-0 ml-auto" action="main" method="get">
                <input type="hidden" name="command" value="questions"/>
                <input class="search-input mr-sm-2" type="search" placeholder="Search" aria-label="Search" name="search_string">
            </form>
            <ul class="navbar-nav">
                <c:choose>
                    <c:when test="${sessionScope.id == null}">
                        <li class="nav-item">
                            <a class="nav-link" href="main?command=go_to_authorization"><c:out value="${signInButtonName}"/></a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="main?command=go_to_registration"><c:out value="${signUpButtonName}"/></a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="nav-item">
                            <a class="nav-link" href="main?command=logout"><c:out value="${logOutBtnName}"/></a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </div>
</nav>
</header>