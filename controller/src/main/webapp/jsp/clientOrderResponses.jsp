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
        <fmt:message bundle="${loc}" key="order.description" var="description" />
        <fmt:message bundle="${loc}" key="order.address" var="address" />
        <fmt:message bundle="${loc}" key="order.serviceType" var="serviceType" />
        <fmt:message bundle="${loc}" key="order.status" var="status" />
        <fmt:message bundle="${loc}" key="order.idWorker" var="idWorker" />
        <fmt:message bundle="${loc}" key="order.idClient" var="idClient" />
        <fmt:message bundle="${loc}" key="order.orderCreationDate" var="orderCreationDate" />

    </head>
    <body>
        <header>
            <jsp:include page="header.jsp">
                <jsp:param name="page_path" value="/jsp/clientOrderResponses.jsp"/>
            </jsp:include>
        </header>

        <form id="find_client_response" method="GET" action="${pageContext.request.contextPath}/controller">
            <input type="hidden" name="command" value="find_client_response">
                <div>
                    <a type="submit" name="find_client_response" class="btn btn-light" href="/controller?command=find_client_response&idClient=${sessionScope.userId}">
                            ${work_find_client_response}
                    </a>
                </div>
            </input>
        </form>
        <div>
            <c:if test="${sessionScope.userRole eq 'client'}">
                <c:forEach var="response" items="${requestScope.pageable.elements}" >
                    <a href = "/controller?command=find_order_info&idService=${response.idService}">
                        <tr>
                            <td>${idClient} : ${response.idClient}</td>
                            <td>${idWorker} : ${response.idWorker}</td>
                            <td>${description} : ${response.description}</td>
                            <td>${serviceType} : ${response.serviceType}</td>
                            <td>${status} : ${response.status}</td>
                            <td>${orderCreationDate} : <fmt:formatDate value="${response.orderCreationDate}" pattern="yyyy.MM.dd" /></td>
                        </tr>
                        <input type="hidden" name="idService" id="idService" value="${response.idService}"/>
                    </a>
                </c:forEach>
            </c:if>
        </div>

        <div style="margin-left: center">
            <c:forEach begin="1" end="${Math.ceil(pageable.totalElements / pageable.limit)}" var="i">
                <c:if test="${i == pageable.pageNumber}">
                    <span>
                        <button style="color:red" form="find_client_response" type="submit" name="currentPage" value="${i}">${i}</button>
                    </span>
                </c:if>
                <c:if test="${i != pageable.pageNumber}">
                    <span>
                        <button form="find_client_response" type="submit" name="currentPage" value="${i}">${i}</button>
                    </span>
                </c:if>
            </c:forEach>
        </div>
    </body>
</html>



