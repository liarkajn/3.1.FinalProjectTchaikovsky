<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="../css/normalize.css">
    <link rel="stylesheet" type="text/css" href="../css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="../css/questionnaire.css">
</head>
<body>

<main class="content">
    <div class="row">
        <div class="col-3 card m-auto">
            <form method="get" action="/administration">
                <input type="hidden" name="command" value="authorization"/>
                <div class="title-row title-row-administration">
                    <h1>ADMINISTRATION PANEL</h1>
                </div>
                <c:if test="${requestScope.error_message != null}">
                    <div class="row">
                        <div class="col-12">
                            <div class="alert alert-danger">
                                <c:out value="${requestScope.error_message}"/>
                            </div>
                        </div>
                    </div>
                </c:if>
                <div class="form-row">
                    <div class="col-12 mb-3">
                        <label for="login">Login</label>
                        <input type="text" class="form-control" id="login" name="login" placeholder="Login"
                               onfocus="isValid(event)" onkeyup="isValid(event)"
                               pattern="^[A-Za-z][A-Za-z0-9_]{4,16}$" required>
                        <div class="invalid-feedback">
                            Login must contain latin, numbers and special symbol ( _ )
                        </div>
                    </div>
                </div>
                <div class="form-row">
                    <div class="col-12 mb-3">
                        <label for="password">Password</label>
                        <input type="password" class="form-control" id="password" name="password"
                               placeholder="Password" onfocus="isValid(event)" onkeyup="isValid(event)"
                               pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{6,18}$" required>
                        <div class="invalid-feedback">
                            The password must contain at least 1 small, 1 large Latin letters and 1 number
                        </div>
                    </div>
                </div>
                <div class="form-row">
                    <button class="btn btn-danger" type="submit">Authorization</button>
                </div>
                <br>
            </form>
        </div>
    </div>
</main>

<script type="text/javascript" src="js/questionnaire.js"></script>
<script type="text/javascript" src="js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="js/popper.js"></script>
<script type="text/javascript" src="js/bootstrap.js"></script>
</body>
</html>
