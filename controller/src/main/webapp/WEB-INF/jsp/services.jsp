<%@page language="java" contentType="text\html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<html>
    <head>
        <title>Orders</title>
        <link rel="stylesheet" href="../${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.css"/>
        <link rel="stylesheet" href="../${pageContext.request.contextPath}/resources/css/style.css"/>


        <fmt:setLocale value="${sessionScope.language}"/>
        <fmt:setBundle basename="localization.local" var="loc"/>
        <fmt:message bundle="${loc}" key="button.order.show.all" var="service_show_all"/>
        <fmt:message bundle="${loc}" key="button.order.by.service.type" var="show_orders_by_service_type"/>

        <fmt:message bundle="${loc}" key="order.idService" var="idService"/>
        <fmt:message bundle="${loc}" key="order.idClient" var="idClient"/>
        <fmt:message bundle="${loc}" key="order.description" var="description"/>
        <fmt:message bundle="${loc}" key="order.address" var="address"/>
        <fmt:message bundle="${loc}" key="order.serviceType" var="serviceType"/>
        <fmt:message bundle="${loc}" key="order.status" var="status"/>
        <fmt:message bundle="${loc}" key="order.orderCreationDate" var="orderCreationDate"/>
        <fmt:message bundle="${loc}" key="message.sort.by" var="message_sort_by"/>
        <fmt:message bundle="${loc}" key="message.filter.by" var="message_filter_by"/>
        <fmt:message bundle="${loc}" key="direction.change" var="direction_change"/>
        <fmt:message bundle="${loc}" key="service.type.electrical" var="service_type_electrical"/>
        <fmt:message bundle="${loc}" key="service.type.gas" var="service_type_gas"/>
        <fmt:message bundle="${loc}" key="service.type.roofing" var="service_type_roofing"/>
        <fmt:message bundle="${loc}" key="service.type.painting" var="service_type_painting"/>
        <fmt:message bundle="${loc}" key="service.type.plumbing" var="service_type_plumbing"/>
        <fmt:message bundle="${loc}" key="service.all" var="all_services"/>

    </head>
    <body>
        <header>
            <jsp:include page="header.jsp">
                <jsp:param name="page_path" value="services"/>
            </jsp:include>
        </header>
        <div class="container">
            <form id="show_orders_by_service_type" method="GET" action="${pageContext.request.contextPath}/controller">
                <input type="hidden" name="command" value="show_orders_by_service_type">
                    <div class="btn-group-vertical" role="group">
                        <div>
                            <button type="submit" class="btn btn-info">${show_orders_by_service_type}
                            </button>
                        </div>
                        <div id="service_type">
                                ${message_filter_by}
                            <select class="custom-select col-auto my-1" name="service_type">
                                <option value="ALL" ${"ALL" == requestScope.selected_service_type  ? 'selected':''} >${all_services}</option>
                                <option value="ELECTRICAL" ${"ELECTRICAL" == requestScope.selected_service_type ? 'selected':''}> ${service_type_electrical}</option>
                                <option value="GAS" ${"GAS" == requestScope.selected_service_type ? 'selected':''}> ${service_type_gas}</option>
                                <option value="ROOFING" ${"ROOFING" == requestScope.selected_service_type ? 'selected':''}> ${service_type_roofing}</option>
                                <option value="PAINTING" ${"PAINTING" == requestScope.selected_service_type ? 'selected':''}> ${service_type_painting}</option>
                                <option value="PLUMBING" ${"PLUMBING" == requestScope.selected_service_type ? 'selected':''}> ${service_type_plumbing}</option>
                            </select>
                        </div>
                        <div id="sort_by">
                                ${message_sort_by}
                            <select class="custom-select col-auto my-1" name="sort_by" id="sort_by">
                                <option value="order_creation_date" ${"order_creation_date" == requestScope.selected_sort_by_parameter ? 'selected':''} >${orderCreationDate}</option>
                                <option value="address" ${"address" == requestScope.selected_sort_by_parameter ? 'selected':''}>${address}</option>
                                <option value="service_type" ${"service_type" == requestScope.selected_sort_by_parameter ? 'selected':''}>${serviceType}</option>
                                <option value="service_status" ${"service_status" == requestScope.selected_sort_by_parameter ? 'selected':''}>${status}</option>
                            </select>
                        </div>
                        <div id="direction">
                            <div class="form-check">
                                <input class="form-check-input" type="radio" id="direction" name="direction"
                                       value="DESC" ${"DESC" == requestScope.selected_direction_parameter ? 'checked':''}/>
                                <label class="form-check-label" for="direction">
                                        ${direction_change}
                                </label>
                            </div>
                        </div>
                    </div>
                </input>
            </form>
        </div>
        <div align="container" style="padding-top: 50px;">
            <table class="table table-striped">
                <c:if test="${requestScope.pageable.elements.size() > 0}">
                    <tr>
                        <th scope="col">${idService}</th>
                        <th scope="col">${description}</th>
                        <th scope="col">${serviceType}</th>
                        <th scope="col">${status}</th>
                        <th scope="col">${orderCreationDate} </th>
                    </tr>
                </c:if>

                <c:forEach var="order" items="${requestScope.pageable.elements}">
                    <tr>
                        <td>
                            <a href="/controller?command=find_order_info&idService=${order.idService}"> ${order.idService}</a>
                        </td>
                        <td>${order.description}</td>
                        <td>${order.serviceType}</td>
                        <td>${order.status}</td>
                        <td><fmt:formatDate value="${order.orderCreationDate}" pattern="yyyy.MM.dd"/></td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <div style="margin-left: center">
            <c:forEach begin="1" end="${Math.ceil(pageable.totalElements / pageable.limit)}" var="i">
                <c:if test="${i == pageable.pageNumber}">
                    <span>
                        <button class="btn btn-light btn-sm" style="color:red" form="${requestScope.last_command}"
                                type="submit" name="currentPage"
                                value="${i}">${i}</button>
                    </span>
                </c:if>
                <c:if test="${i != pageable.pageNumber}">
                    <span>
                        <button class="btn btn-light btn-sm" form="${requestScope.last_command}" type="submit"
                                name="currentPage"
                                value="${i}">${i}</button>
                    </span>
                </c:if>
            </c:forEach>
        </div>
    </body>
</html>
