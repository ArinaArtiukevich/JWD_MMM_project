<%--
  Created by IntelliJ IDEA.
  User: arina
  Date: 9/6/21
  Time: 15:47
  To change this template use File | Settings | File Templates.
--%>
<%@page language="java" contentType="text\html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<html>
    <head>
        <title>${title_authorization}</title>
        <link rel="stylesheet" href="../../resources/bootstrap/css/bootstrap.css"/>
        <fmt:setLocale value="${sessionScope.language}"/>
        <fmt:setBundle basename="localization.local" var="loc"/>

        <fmt:message bundle="${loc}" key="button.login" var="button_login"/>
        <fmt:message bundle="${loc}" key="button.registration" var="button_registration"/>
        <fmt:message bundle="${loc}" key="title.authorization" var="title_authorization"/>

    </head>
    <body>

        <header>
            <jsp:include page="header.jsp">
                <jsp:param name="page_path" value="authorization"/>
            </jsp:include>
        </header>

        <div id="error">
            <h1 style="color:red;">${internalError}</h1>
        </div>

        <div class="container">
            <h3>${title_authorization}</h3>
            <hr/>
            <form name="startForm" action="${pageContext.request.contextPath}/controller" method="POST">
                <div class="btn-group" role="group">
                    <a href="/controller?command=go_to_page&path=login" class="btn btn-light" role="button"><c:out
                            value="${button_login}"/></a>
                    <br/>
                    <a href="/controller?command=go_to_page&path=registration" class="btn btn-info" role="button"><c:out
                            value="${button_registration}"/></a>
                </div>
            </form>
        </div>
    </body>
</html>

