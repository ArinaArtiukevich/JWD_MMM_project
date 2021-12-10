<%@page language="java" contentType="text\html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
    <head>
        <title>MULTITOOL</title>
        <link rel="stylesheet" href="resources/bootstrap/css/bootstrap.css"/>

        <fmt:setLocale value="${sessionScope.language}"/>
        <fmt:setBundle basename="localization.local" var="loc"/>
        <fmt:message bundle="${loc}" key="main.description" var="main_description"/>
        <fmt:message bundle="${loc}" key="main.invitation" var="main_invitation"/>
    </head>

    <header>
        <jsp:include page="/WEB-INF/jsp/header.jsp">
            <jsp:param name="page_path" value="index"/>
        </jsp:include>
    </header>

    <body>

        <div class="container">
            <h5>${main_description}</h5>
            <h5>${main_invitation}</h5>
        </div>

        <div id="error">
            <h4>${requestScope.internalError}</h4>
        </div>

    </body>
</html>

