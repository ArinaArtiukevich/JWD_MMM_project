<%@page language="java" contentType="text\html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
    <head>
        <title>${work_title}</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css"/>

        <fmt:setLocale value="${sessionScope.language}"/>
        <fmt:setBundle basename="localization.local" var="loc"/>

        <fmt:message bundle="${loc}" key="work.title" var="work_title"/>
        <fmt:message bundle="${loc}" key="work.add.order" var="work_add_service"/>
        <fmt:message bundle="${loc}" key="work.show.user.all.order" var="work_show_user_all_order"/>
        <fmt:message bundle="${loc}" key="show.user.orders" var="show_user_orders"/>

    </head>
    <body>
        <header>
            <jsp:include page="header.jsp">
                <jsp:param name="page_path" value="/jsp/work.jsp"/>
            </jsp:include>
        </header>

        <div>
            <h1 style="color:red;">id : ${sessionScope.userId}</h1>
            <h1 style="color:red;">login : ${sessionScope.login}</h1>
        </div>

        <form name="userForm" method="POST" action="${pageContext.request.contextPath}/controller">
            <input type="hidden" name="command" value="work"/>

            <div id="menu">
                <button type="submit" name="work_action" value="addService">
                    ${work_add_service}
                </button><br/>
                <button type="submit" name="work_action" value="showUserOrder">
                    ${work_show_user_all_order}
                </button><br/>
            </div>
        </form>
    </body>
</html>



