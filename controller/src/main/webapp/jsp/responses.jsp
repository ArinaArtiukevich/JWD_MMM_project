<%@page language="java" contentType="text\html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<html>
    <head>
        <title>${title_services}</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css"/>


        <fmt:setLocale value="${sessionScope.language}"/>
        <fmt:setBundle basename="localization.local" var="loc"/>
        <fmt:message bundle="${loc}" key="button.order.show.all" var="service_show_all" />
        <fmt:message bundle="${loc}" key="work.find.worker.response" var="work_find_worker_response"/>
        <fmt:message bundle="${loc}" key="work.find.client.response" var="work_find_client_response"/>
        <fmt:message bundle="${loc}" key="title.order" var="title_services" />
        <fmt:message bundle="${loc}" key="order.idService" var="idService" />
        <fmt:message bundle="${loc}" key="order.idClient" var="idClient" />
        <fmt:message bundle="${loc}" key="order.description" var="description" />
        <fmt:message bundle="${loc}" key="order.address" var="address" />
        <fmt:message bundle="${loc}" key="order.serviceType" var="serviceType" />
        <fmt:message bundle="${loc}" key="order.status" var="status" />

    </head>
    <body>
        <header>
            <jsp:include page="header.jsp">
                <jsp:param name="page_path" value="/jsp/responses.jsp"/>
            </jsp:include>
        </header>

        <form method="GET" action="${pageContext.request.contextPath}/controller">
            <div id="menu">
                <c:if test="${sessionScope.userRole eq 'worker'}">
                    <div>
                        <a class="btn btn-primary" href="/controller?command=find_worker_response&idWorker=${sessionScope.userId}">
                                ${work_find_worker_response}
                        </a><br/>
                    </div>
                </c:if>
                <c:if test="${sessionScope.userRole eq 'client'}">
                    <div>
                        <a class="btn btn-primary" href="/controller?command=find_client_response&idClient=${sessionScope.userId}">
                                ${work_find_client_response}
                        </a><br/>
                    </div>
                </c:if>
            </div>
        </form>
            <div>
                <c:forEach var="response" items="${sessionScope.responses}" >
                    <a href = "/controller?command=find_order_info&idService=${response.idService}">
                        <tr>
                            <td>${description} : ${response.description}</td>
                            <td>${serviceType} : ${response.serviceType}</td>
                            <td>${status} : ${response.status}</td>
                        </tr>
                        <input type="hidden" name="idService" id="idService" value="${response.idService}"/>
                    </a>
                </c:forEach>
            </div>
    </body>
</html>
