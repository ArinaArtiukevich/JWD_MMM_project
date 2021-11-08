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
        <fmt:message bundle="${loc}" key="button.order.by.service.type" var="show_orders_by_service_type" />

        <fmt:message bundle="${loc}" key="title.order" var="title_services" />
        <fmt:message bundle="${loc}" key="order.idService" var="idService" />
        <fmt:message bundle="${loc}" key="order.idClient" var="idClient" />
        <fmt:message bundle="${loc}" key="order.description" var="description" />
        <fmt:message bundle="${loc}" key="order.address" var="address" />
        <fmt:message bundle="${loc}" key="order.serviceType" var="serviceType" />
        <fmt:message bundle="${loc}" key="order.status" var="status" />
        <fmt:message bundle="${loc}" key="order.orderCreationDate" var="orderCreationDate" />

    </head>
    <body>
        <header>
            <jsp:include page="header.jsp">

                <jsp:param name="page_path" value="/jsp/services.jsp"/>
            </jsp:include>
        </header>

        <form id="show_all_services" method="GET" action="${pageContext.request.contextPath}/controller">
            <input type="hidden" name="command" value="show_all_services">
                <div id="menu">
                    <button type="submit" name="show_all_services">
                            ${service_show_all}
                    </button><br/>
                </div>
            </input>
        </form>
        <form id="show_orders_by_service_type" method="POST" action="${pageContext.request.contextPath}/controller">
            <input type="hidden" name="command" value="show_orders_by_service_type">
                <div id="menu">
                    <div>
                        <button type="submit" name="show_orders_by_service_type">${show_orders_by_service_type}</button>
                    </div>
                    <div class="form-row">
                        <div class="col-auto my-1">
                            <select class="custom-select mr-sm-2" name="service_type">
                                <option value="ELECTRICAL" ${"ELECTRICAL" == selected_service_type ? 'selected':''}> ELECTRICAL</option>
                                <option value="GAS" ${"GAS" == selected_service_type ? 'selected':''}> GAS</option>
                                <option value="ROOFING" ${"ROOFING" == selected_service_type ? 'selected':''}> ROOFING</option>
                                <option value="PAINTING" ${"PAINTING" == selected_service_type ? 'selected':''}> PAINTING</option>
                                <option value="PLUMBING" ${"PLUMBING" == selected_service_type ? 'selected':''}> PLUMBING</option>
                            </select> <br/>
                        </div>
                    </div>

                <%--                <input type="radio" name="service_type" value="ELECTRICAL"> ELECTRICAL</input><br/>--%>
<%--                <input type="radio" name="service_type" value="GAS"> GAS</input><br/>--%>
<%--                <input type="radio" name="service_type" value="ROOFING"> ROOFING</input><br/>--%>
<%--                <input type="radio" name="service_type" value="PAINTING"> PAINTING</input><br/>--%>
<%--                <input type="radio" name="service_type" value="PLUMBING"> PLUMBING</input><br/>--%>
<%--                <button type="submit" name="show_orders_by_service_type">${show_orders_by_service_type}</button>--%>

                </div>
            </input>
        </form>

        <div>
            <c:forEach var="order" items="${requestScope.pageable.elements}" >
                <a href = "/controller?command=find_order_info&idService=${order.idService}">
                    <tr>
                        <td>${description} : ${order.description}</td>
                        <td>${serviceType} : ${order.serviceType}</td>
                        <td>${status} : ${order.status}</td>
                        <td>${orderCreationDate} : <fmt:formatDate value="${order.orderCreationDate}" pattern="yyyy.MM.dd" /></td>
                    </tr>
                    <input type="hidden" name="idService" id="idService" value="${order.idService}"/>
                </a>
            </c:forEach>
        </div>
        <div style="margin-left: center">
            <c:forEach begin="1" end="${Math.ceil(pageable.totalElements / pageable.limit)}" var="i">
                <c:if test="${i == pageable.pageNumber}">
                    <span>
                        <button style="color:red" form="${requestScope.last_command}" type="submit" name="currentPage" value="${i}">${i}</button>
                    </span>
                </c:if>
                <c:if test="${i != pageable.pageNumber}">
                    <span>
                        <button form="${requestScope.last_command}" type="submit" name="currentPage" value="${i}">${i}</button>
                    </span>
                </c:if>
            </c:forEach>
        </div>
    </body>
</html>
