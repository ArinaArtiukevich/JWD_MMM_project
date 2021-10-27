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
        <fmt:message bundle="${loc}" key="work.find.worker.response" var="work_find_worker_response"/>
        <fmt:message bundle="${loc}" key="work.find.client.response" var="work_find_client_response"/>
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

        <div id="error">
            <h1 style="color:red;"> ${errorWorkMessage}</h1>
        </div>
        <c:if test="${sessionScope.userId ne null}">
            <form name="userForm" method="POST" action="${pageContext.request.contextPath}/controller">
                <input type="hidden" name="command" value="work"/>

                <div id="menu">

                    <c:if test="${sessionScope.userRole eq 'client'}">
                        <button type="submit" name="work_action" value="addService">
                                ${work_add_service}
                        </button><br/>

                        <button type="submit" name="work_action" value="showUserOrder">
                                ${work_show_user_all_order}
                        </button><br/>
                        <div>
                            <a class="btn btn-primary" href="/controller?command=find_client_response&idClient=${sessionScope.userId}">
                                    ${work_find_client_response}
                            </a><br/>
                        </div>
                    </c:if>

                    <c:if test="${sessionScope.userRole eq 'worker'}">
                        <div>
                            <a class="btn btn-primary" href="/controller?command=find_worker_response&idWorker=${sessionScope.userId}">
                                    ${work_find_worker_response}
                            </a><br/>
                        </div>
                    </c:if>

                </div>
            </form>
        </c:if>
        <c:if test="${sessionScope.userId eq null}">
            <a href="../jsp/authorization.jsp">
                <h1>Please login or register</h1>>
            </a>
        </c:if>

    </body>
</html>



