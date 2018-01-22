<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<header>
    <div align="center">
        <form action="administration" method="get">
            <input type="hidden" name="command" value="questions" />
            <input type="submit" value="Questions"/>
        </form>
        <form action="administration" method="get">
            <input type="hidden" name="command" value="users" />
            <input type="submit" value="Users"/>
        </form>
        <form action="administration" method="get">
            <input type="hidden" name="command" value="log_out" />
            <input type="submit" value="Log out"/>
        </form>
    </div>
</header>

