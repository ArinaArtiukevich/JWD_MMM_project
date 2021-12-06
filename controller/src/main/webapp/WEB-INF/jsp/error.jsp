<%@page language="java" contentType="text\html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
    <head>
        <title>Error</title>
        <link rel="stylesheet" href="../${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.css"/>
        <link rel="stylesheet" href="../${pageContext.request.contextPath}/resources/css/style.css"/>

        <fmt:setLocale value="${sessionScope.language}"/>
        <fmt:setBundle basename="localization.local" var="loc"/>

        <fmt:message bundle="${loc}" key="error.message" var="error_message"/>
    </head>
    <body>
        <header>
            <jsp:include page="header.jsp">
                <jsp:param name="page_path" value="error"/>
            </jsp:include>
        </header>

        <div class="container" style="padding-top: 50px;">
            <div id="errorMessage">
                <h4>
                    <c:out value="${sessionScope.error}"/>
                </h4>
                <c:remove var="error" scope="session"/>
            </div>
            <h4>${error_message}</h4>
        </div>

    </body>
</html>
