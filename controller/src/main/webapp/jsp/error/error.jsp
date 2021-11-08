<%@page language="java" contentType="text\html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
    <head>
        <title>${title_error}</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css"/>

        <fmt:setLocale value="${sessionScope.language}"/>
        <fmt:setBundle basename="localization.local" var="loc"/>

        <fmt:message bundle="${loc}" key="title.error" var="title_error"/>
        <fmt:message bundle="${loc}" key="error.message" var="error_message"/>
    </head>
    <body>
        <header>
            <jsp:include page="../header.jsp">
                <jsp:param name="page_path" value="/jsp/error/error.jsp"/>
            </jsp:include>
        </header>

        <div>
            <h1>${sessionScope.error}</h1>
            <h2>${error_message}</h2>
        </div>

    </body>
</html>
