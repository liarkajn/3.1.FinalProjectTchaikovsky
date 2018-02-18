<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>
        <c:choose>
            <c:when test="${requestScope.user.name != null && requestScope.user.surname != null}">
                <c:out value="${requestScope.user.name} ${requestScope.user.surname} (${requestScope.user.login})"/>
            </c:when>
            <c:otherwise>
                <c:out value="${requestScope.user.login}"/>
            </c:otherwise>
        </c:choose>
    </title>
    <link rel="stylesheet" type="text/css" href="css/normalize.css">
    <link rel="stylesheet" type="text/css" href="css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="css/questionnaire.css">
</head>
<body>


<jsp:include page="navbar.jsp"/>

<main class="content">
    <div class="container">
        <div class="row">
            <div class="col-11 col-md-11 col-lg-8 card m-auto">
                <form method="get" action="administration">
                    <br>
                    <div class="row">
                        <c:set var="user" scope="request" value="${requestScope.user}"/>
                        <input type="hidden" name="command" value="user_editing"/>
                        <input type="hidden" name="id" value="${user.id}"/>
                        <div class="col-12 col-md-6">
                            <h5 class="text-muted">Contact information: </h5>
                            <div class="form-row">
                                <div class="col-12 mb-3">
                                    <label for="login">Login</label>
                                    <input type="text" class="form-control" id="login" name="login"
                                           value="${user.login}"
                                           placeholder="Login" onfocus="isValid(event)" onkeyup="isValid(event)"
                                           pattern="^[A-Za-z][A-Za-z0-9_]{4,16}$"
                                           required>
                                    <div class="invalid-feedback">
                                        Login must contain latin, numbers and special symbol ( _ )
                                    </div>
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="col-12 mb-3">
                                    <label for="email">Email</label>
                                    <input type="email" class="form-control" id="email" name="email"
                                           value="${user.email}"
                                           placeholder="Email" onfocus="isValid(event)" onkeyup="isValid(event)"
                                           pattern='[A-Za-z0-9.]+@[a-z0-9.-]+.[a-z]{2,3}'
                                           required>
                                    <div class="invalid-feedback">
                                        Email must contain symbol @ and .
                                    </div>
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="col-12 mb-3">
                                    <label for="role">Role</label>
                                    <select class="form-control" id="role" name="role" required>
                                        <c:choose>
                                            <c:when test="${user.role.toString().toLowerCase() == 'admin'}">
                                                <option selected>Admin</option>
                                                <option>Moderator</option>
                                                <option>User</option>
                                            </c:when>
                                            <c:when test="${user.role.toString().toLowerCase() == 'moderator'}">
                                                <option>Admin</option>
                                                <option selected>Moderator</option>
                                                <option>User</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option>Admin</option>
                                                <option>Moderator</option>
                                                <option selected>User</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="col-12 col-md-6">
                            <h5 class="text-muted">Personal information:</h5>
                            <div class="form-row">
                                <div class="col-12 mb-3">
                                    <label for="name">Name</label>
                                    <input type="text" class="form-control" id="name" name="name" value="${user.name}"
                                           placeholder="Name">
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="col-12 mb-3">
                                    <label for="surname">Surname</label>
                                    <input type="text" class="form-control" id="surname" name="surname"
                                           value="${user.surname}"
                                           placeholder="Surname">
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group col-12">
                                    <label for="gender">Gender</label>
                                    <select class="form-control" id="gender" name="gender" required>
                                        <c:choose>
                                            <c:when test="${user.gender.toString().toLowerCase() == 'male'}">
                                                <option selected>Male</option>
                                                <option>Female</option>
                                                <option>Other</option>
                                            </c:when>
                                            <c:when test="${user.gender.toString().toLowerCase() == 'female'}">
                                                <option>Male</option>
                                                <option selected>Female</option>
                                                <option>Other</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option>Male</option>
                                                <option>Female</option>
                                                <option selected>Other</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="col-12 mb-3">
                            <label for="bio">About me</label>
                            <textarea id="bio" name="bio" class="form-control" placeholder="Bio"
                                      minlength="10"><c:out value="${user.bio}"/></textarea>
                        </div>
                    </div>
                    <div class="form-row">
                        <button class="btn btn-info" type="submit">Edit</button>
                        <c:choose>
                            <c:when test="${requestScope.user.banned == false}">
                                <a class="btn btn-danger" href="?command=ban&ban=true&id=<c:out value="${user.id}"/>">ban</a>
                            </c:when>
                            <c:otherwise>
                                <a class="btn btn-accept" href="?command=ban&ban=false&id=<c:out value="${user.id}"/>">unban</a>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <br>
                </form>
            </div>
        </div>
    </div>
</main>

<script type="text/javascript" src="js/questionnaire.js"></script>
<script type="text/javascript" src="js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="js/popper.js"></script>
<script type="text/javascript" src="js/bootstrap.js"></script>
</body>
</html>
