<%@page language="java" contentType="text\html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<html>
    <head>
        <title>${title_services}</title>
        <link rel="stylesheet" href="../${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.css"/>
        <link rel="stylesheet" href="../${pageContext.request.contextPath}/resources/css/style.css"/>


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
        <fmt:message bundle="${loc}" key="message.sort.by" var="message_sort_by" />
        <fmt:message bundle="${loc}" key="direction.change" var="direction_change" />
        <fmt:message bundle="${loc}" key="service.type.electrical" var="service_type_electrical"/>
        <fmt:message bundle="${loc}" key="service.type.gas" var="service_type_gas"/>
        <fmt:message bundle="${loc}" key="service.type.roofing" var="service_type_roofing"/>
        <fmt:message bundle="${loc}" key="service.type.painting" var="service_type_painting"/>
        <fmt:message bundle="${loc}" key="service.type.plumbing" var="service_type_plumbing"/>


    </head>
    <body>
        <header>
            <jsp:include page="header.jsp">

                <jsp:param name="page_path" value="services"/>
            </jsp:include>
        </header>

        <form id="show_all_services" method="GET" action="${pageContext.request.contextPath}/controller">
            <input type="hidden" name="command" value="show_all_services">
                <div id="menu" class="custom-control-inline">
                    <button type="submit" name="show_all_services">
                            ${service_show_all}
                    </button>
                    <div id="sort_by">
                        <h7>${message_sort_by}</h7>
                        <select class="custom-select custom-select-lg col-md-4 mb-2" name="sort_by" id="sort_by">
                            <option value="order_creation_date" ${"order_creation_date" == requestScope.selected_sort_by_parameter ? 'selected':''} >${orderCreationDate}</option>
                            <option value="address" ${"address" == requestScope.selected_sort_by_parameter ? 'selected':''}>${address}</option>
                            <option value="service_type" ${"service_type" == requestScope.selected_sort_by_parameter ? 'selected':''}>${serviceType}</option>
                            <option value="service_status" ${"service_status" == requestScope.selected_sort_by_parameter ? 'selected':''}>${status}</option>
                            <option value="description" ${"description" == requestScope.selected_sort_by_parameter ? 'selected':''}>${description}</option>
                        </select>
                    </div>
                    <div id="direction">
                        <div class="form-check">
                            <input class="form-check-input" type="radio" id="direction" name="direction" value="DESC" ${"DESC" == requestScope.selected_direction_parameter ? 'checked':''}/>
                            <label class="form-check-label" for="direction">
                                    ${direction_change}
                            </label>
                        </div>
                    </div>
                </div>
            </input>
        </form>
        <br/><br/><br/><br/>
        <form id="show_orders_by_service_type" method="POST" action="${pageContext.request.contextPath}/controller">
            <input type="hidden" name="command" value="show_orders_by_service_type">
                <div id="menu">
                    <div class="custom-control-inline">
                        <div>
                            <button type="submit" name="show_orders_by_service_type">${show_orders_by_service_type}</button>
                        </div>
                        <div id="service_type" class="form-row">
                            <div class="col-auto my-1">
                                <select class="custom-select mr-sm-2" name="service_type">
                                    <option value="ELECTRICAL" ${"ELECTRICAL" == requestScope.selected_service_type ? 'selected':''}> ${service_type_electrical}</option>
                                    <option value="GAS" ${"GAS" == requestScope.selected_service_type ? 'selected':''}> ${service_type_gas}</option>
                                    <option value="ROOFING" ${"ROOFING" == requestScope.selected_service_type ? 'selected':''}> ${service_type_roofing}</option>
                                    <option value="PAINTING" ${"PAINTING" == requestScope.selected_service_type ? 'selected':''}> ${service_type_painting}</option>
                                    <option value="PLUMBING" ${"PLUMBING" == requestScope.selected_service_type ? 'selected':''}> ${service_type_plumbing}</option>
                                </select> <br/>
                            </div>
                        </div>
                        <div id="sort_by">
                            <h7>${message_sort_by}</h7>
                            <select class="custom-select custom-select-lg col-md-4 mb-2" name="sort_by" id="sort_by">
                                <option value="order_creation_date" ${"order_creation_date" == requestScope.selected_sort_by_parameter ? 'selected':''} >${orderCreationDate}</option>
                                <option value="address" ${"address" == requestScope.selected_sort_by_parameter ? 'selected':''}>${address}</option>
                                <option value="service_type" ${"service_type" == requestScope.selected_sort_by_parameter ? 'selected':''}>${serviceType}</option>
                                <option value="service_status" ${"service_status" == requestScope.selected_sort_by_parameter ? 'selected':''}>${status}</option>
                                <option value="description" ${"description" == requestScope.selected_sort_by_parameter ? 'selected':''}>${description}</option>
                            </select>
                        </div>
                        <div id="direction">
                            <div class="form-check">
                                <input class="form-check-input" type="radio" id="direction" name="direction" value="DESC" ${"DESC" == requestScope.selected_direction_parameter ? 'checked':''}/>
                                <label class="form-check-label" for="direction">
                                        ${direction_change}
                                </label>
                            </div>
                        </div>
                    </div>
                </div>
            </input>
        </form>

        <div>
                <c:if test="${requestScope.pageable.elements.size() > 0}">
                    <table class="table">
                        <tr>
                            <td>${description}</td>
                            <td>${serviceType}</td>
                            <td>${status}</td>
                            <td>${orderCreationDate} </td>
                        </tr>
                    </table>
                </c:if>
                <c:forEach var="order" items="${requestScope.pageable.elements}" >
                    <table class="table">
                        <a href="/controller?command=find_order_info&idService=${order.idService}">
                            <tr>
                                <td>${order.description}</td>
                                <td>${order.serviceType}</td>
                                <td>${order.status}</td>
                                <td><fmt:formatDate value="${order.orderCreationDate}" pattern="yyyy.MM.dd" /></td>
                            </tr>
                        </a>
                    </table>
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
