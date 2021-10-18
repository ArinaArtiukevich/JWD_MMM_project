<%--
  Created by IntelliJ IDEA.
  User: arina
  Date: 9/5/21
  Time: 15:46
  To change this template use File | Settings | File Templates.
--%>
<%@page language="java" contentType="text\html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title>${title_login}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css"/>

    <fmt:setLocale value="${sessionScope.language}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>

    <fmt:message bundle="${loc}" key="title.login" var="title_login"/>
    <fmt:message bundle="${loc}" key="login.menu.login" var="login_menu_login"/>
    <fmt:message bundle="${loc}" key="login.menu.password" var="login_menu_password"/>
    <fmt:message bundle="${loc}" key="login.button.enter" var="login_button_enter"/>


</head>
<body>
<header>
    <jsp:include page="header.jsp">
        <jsp:param name="page_path" value="/jsp/login.jsp"/>
    </jsp:include>
</header>

    <div id="error">
        <h1 style="color:red;"> ${errorLoginMessage}</h1>
    </div>
<form method="POST" action="${pageContext.request.contextPath}/controller">
    <input type="hidden" name="command" value="login"/>
    <div class="mb-3">
        <label for="loginInput" class="form-label form-control-lg">${login_menu_login}</label>
        <input type="text" class="form-control" name="login" id="loginInput" aria-describedby="loginHelp" value=""/>
    </div>
    <div class="mb-3">
        <label for="passwordInput" class="form-label form-control-lg">${login_menu_password}</label>
        <input type="password" name="password" class="form-control" id="passwordInput" value=""/>
    </div>

<button type="submit" name="loginAction" class="btn btn-primary" value="enter">${login_button_enter}</button>

</form>
</body>
</html>
