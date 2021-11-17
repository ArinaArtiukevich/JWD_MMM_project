<%@page language="java" contentType="text\html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title>NAME</title>
    <link rel="stylesheet" href="resources/bootstrap/css/bootstrap.css"/>
    <link rel="stylesheet" href="resources/css/style.css"/>

    <fmt:setLocale value="${sessionScope.language}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
</head>

<header>
    <jsp:include page="/jsp/header.jsp">
        <jsp:param name="page_path" value="index"/>
    </jsp:include>
</header>

<body>

<div>
    <h1 style="color:red;"> ${sessionScope.firstName}</h1>
    <h1 style="color:red;"> ${sessionScope.login}</h1>
</div>

<div id="error">
    <h1 style="color:red;">${internalError}</h1>
</div>

</body>
</html>

