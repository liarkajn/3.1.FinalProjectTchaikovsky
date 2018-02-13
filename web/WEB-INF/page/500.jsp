<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>500</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="css/normalize.css">
    <link rel="stylesheet" type="text/css" href="css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="css/questionnaire.css">
</head>
<body>

<jsp:include page="navbar.jsp"/>

<main class="content">

    <div class="container" align="center">
        <h1 class="error-code">500</h1>
        <h3 class="text-muted">Internal Server Error</h3>
        <p>There was an error. Please try again later.</p>
        <a href="main?command=questions" class="btn btn-danger">Back to home</a>
    </div>

</main>

<script type="text/javascript" src="js/questionnaire.js"></script>
<script type="text/javascript" src="js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="js/popper.js"></script>
<script type="text/javascript" src="js/bootstrap.js"></script>
</body>
</html>
