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

        <fmt:message bundle="${loc}" key="work.add.order" var="work_add_service"/>
        <fmt:message bundle="${loc}" key="show.user.orders" var="show_user_orders"/>

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
        <fmt:message bundle="${loc}" key="button.find.client.order.by.status" var="button_find_client_order_by_status"/>
        <fmt:message bundle="${loc}" key="service.status.free" var="service_status_free"/>
        <fmt:message bundle="${loc}" key="service.status.in_process" var="service_status_in_process"/>
        <fmt:message bundle="${loc}" key="service.status.done" var="service_status_done"/>
        <fmt:message bundle="${loc}" key="service.status.approved" var="service_status_approved"/>
        <fmt:message bundle="${loc}" key="button.delete.order.by.id" var="button_delete_order_by_id"/>
        <fmt:message bundle="${loc}" key="service.all" var="all_services"/>

    </head>
    <body>
        <header>
            <jsp:include page="header.jsp">
                <jsp:param name="page_path" value="showUserOrder"/>
            </jsp:include>
        </header>

        <div class="container">
            <form id="find_client_order_by_status" method="GET" action="${pageContext.request.contextPath}/controller">
                <input type="hidden" name="command" value="find_client_order_by_status"/>
                <input type="hidden" name="idClient" value="${sessionScope.userId}"/>
                <div id="menu" class="btn-group-vertical" role="group">
                    <div id="button">
                        <div>
                            <button class="btn btn-info" type="submit">
                                    ${button_find_client_order_by_status}
                            </button>
                        </div>
                    </div>
                    <div id="requested_service_type">
                            ${message_filter_by}
                        <select class="custom-select col-auto my-1" name="service_status">
                            <option value="ALL" ${"ALL" == requestScope.selected_service_status ? 'selected':''}>${all_services}</option>
                            <option value="FREE" ${"FREE" == requestScope.selected_service_status ? 'selected':''}>${service_status_free}</option>
                            <option value="IN_PROCESS" ${"IN_PROCESS" == requestScope.selected_service_status ? 'selected':''}>${service_status_in_process}</option>
                            <option value="DONE" ${"DONE" == requestScope.selected_service_status ? 'selected':''}>${service_status_done}</option>
                            <option value="APPROVED" ${"APPROVED" == requestScope.selected_service_status ? 'selected':''}>${service_status_approved}</option>
                        </select>
                        <br/>
                    </div>

                    <div id="sort_by">
                            ${message_sort_by}
                        <select class="custom-select col-auto my-1" name="sort_by" id="sort_by">
                            <option value="order_creation_date" ${"order_creation_date" == requestScope.selected_sort_by_parameter ? 'selected':''} >${orderCreationDate}</option>
                            <option value="address" ${"address" == requestScope.selected_sort_by_parameter ? 'selected':''}>${address}</option>
                            <option value="service_type" ${"service_type" == requestScope.selected_sort_by_parameter ? 'selected':''}>${serviceType}</option>
                            <option value="service_status" ${"service_status" == requestScope.selected_sort_by_parameter ? 'selected':''}>${status}</option>
                            <option value="description" ${"description" == requestScope.selected_sort_by_parameter ? 'selected':''}>${description}</option>
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

            </form>
        </div>

        <div align="container" style="padding-top: 50px;">
            <table class="table table-striped">
                <c:if test="${requestScope.pageable.elements.size() > 0}">
                    <tr>
                        <th scope="col">${idService}</th>
                        <th scope="col">${idClient}</th>
                        <th scope="col">${description}</th>
                        <th scope="col">${address}</th>
                        <th scope="col">${serviceType}</th>
                        <th scope="col">${status}</th>
                        <th scope="col">${orderCreationDate} </th>
                        <th scope="col">Actions</th>
                    </tr>
                </c:if>

                <c:forEach var="order" items="${requestScope.pageable.elements}">
                    <tr>
                        <td>
                            <a href="/controller?command=find_order_info&idService=${order.idService}"> ${order.idService}</a>
                        </td>
                        <td>${order.idClient}</td>
                        <td>${order.description}</td>
                        <td>${order.address}</td>
                        <td>${order.serviceType}</td>
                        <td>${order.status}</td>
                        <td><fmt:formatDate value="${order.orderCreationDate}" pattern="yyyy.MM.dd"/></td>
                        <td>
                            <c:if test="${order.status eq 'FREE'}">
                                <div id="button">
                                    <form id="delete_order_by_id" method="POST"
                                          action="${pageContext.request.contextPath}/controller">
                                        <input type="hidden" name="command" value="delete_order_by_id"/>
                                        <input type="hidden" name="idClient" value="${sessionScope.userId}"/>
                                        <input type="hidden" name="idService" id="idService"
                                               value="${order.idService}"/>
                                        <button class="btn btn-light" type="submit" name="delete_order_by_id">
                                                ${button_delete_order_by_id}
                                        </button>
                                    </form>
                                </div>
                            </c:if>
                        </td>
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
