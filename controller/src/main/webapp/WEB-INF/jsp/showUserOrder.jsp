<%@page language="java" contentType="text\html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
    <head>
        <title>${title_showUserOrders}</title>
        <link rel="stylesheet" href="../${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.css"/>
        <link rel="stylesheet" href="../${pageContext.request.contextPath}/resources/css/style.css"/>

        <fmt:setLocale value="${sessionScope.language}"/>
        <fmt:setBundle basename="localization.local" var="loc"/>

        <fmt:message bundle="${loc}" key="title.showUserOrders" var="title_showUserOrders"/>
        <fmt:message bundle="${loc}" key="work.add.order" var="work_add_service"/>
        <fmt:message bundle="${loc}" key="show.user.orders" var="show_user_orders"/>

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
        <fmt:message bundle="${loc}" key="button.find.client.order.by.status" var="button_find_client_order_by_status" />
        <fmt:message bundle="${loc}" key="service.status.free" var="service_status_free" />
        <fmt:message bundle="${loc}" key="service.status.in_process" var="service_status_in_process" />
        <fmt:message bundle="${loc}" key="service.status.done" var="service_status_done" />
        <fmt:message bundle="${loc}" key="service.status.approved" var="service_status_approved" />
        <fmt:message bundle="${loc}" key="button.delete.order.by.id" var="button_delete_order_by_id" />

    </head>
    <body>
        <header>
            <jsp:include page="header.jsp">
                <jsp:param name="page_path" value="showUserOrder"/>
            </jsp:include>
        </header>

        <div>
            <h1 style="color:red;">id : ${sessionScope.userId}</h1>
            <h1 style="color:red;">login : ${sessionScope.login}</h1>
        </div>
        <form id="show_user_orders" method="GET" action="${pageContext.request.contextPath}/controller">
        <input type="hidden" name="command" value="show_user_orders">
            <div id="menu">
                <div id="button">
                    <button type="submit" name="show_user_orders">
                            ${show_user_orders}
                    </button><br/>
                </div>
                <div id="menu">
                    <h5>${message_sort_by}</h5>
                    <select class="custom-select custom-select-lg col-md-4 mb-2" name="sort_by">
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

        <form id="find_client_order_by_status" method="GET" action="${pageContext.request.contextPath}/controller">
            <input type="hidden" name="command" value="find_client_order_by_status"/>
            <input type="hidden" name="idClient" value="${sessionScope.userId}"/>
            <div id="menu">
                <div id="button">
                    <button type="submit" name="find_client_order_by_status">
                            ${button_find_client_order_by_status}
                    </button>
                </div>
                <div id="sort_by">
                    <h5>${message_sort_by}</h5>
                    <select class="custom-select custom-select-lg col-md-4 mb-2" name="sort_by">
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
                <div id="requested_service_type" class="form-row">
                    <div class="col-auto my-1">
                        <select class="custom-select mr-sm-2" name="service_status">
                            <option value="FREE" ${"FREE" == requestScope.selected_service_status ? 'selected':''}>${service_status_free}</option>
                            <option value="IN_PROCESS" ${"IN_PROCESS" == requestScope.selected_service_status ? 'selected':''}>${service_status_in_process}</option>
                            <option value="DONE" ${"DONE" == requestScope.selected_service_status ? 'selected':''}>${service_status_done}</option>
                            <option value="APPROVED" ${"APPROVED" == requestScope.selected_service_status ? 'selected':''}>${service_status_approved}</option>
                        </select> <br/>
                    </div>
                </div>
            </div>
        </form>



        <div>
            <table>
                <c:if test="${requestScope.pageable.elements.size() > 0}">
                    <table class="table">
                        <tr>
                            <td>${idService}</td>
                            <td>${idClient}</td>
                            <td>${description}</td>
                            <td>${address}</td>
                            <td>${serviceType}</td>
                            <td>${status}</td>
                            <td>${orderCreationDate} </td>
                        </tr>
                    </table>
                </c:if>
                <c:forEach var="order" items="${requestScope.pageable.elements}" >
                    <a href = "/controller?command=find_order_info&idService=${order.idService}">
                        <table class="table">
                            <tr>
                                <td>${order.idService}</td>
                                <td>${order.idClient}</td>
                                <td>${order.description}</td>
                                <td>${order.address}</td>
                                <td>${order.serviceType}</td>
                                <td>${order.status}</td>
                                <td><fmt:formatDate value="${order.orderCreationDate}" pattern="yyyy.MM.dd" /></td>
                            </tr>
                        </table>
                    </a>
                <tr>
                    <td>
                        <c:if test="${requestScope.selected_service_status.toString() eq 'FREE'}">
                        <div id="button">
                            <form id="delete_order_by_id" method="POST" action="${pageContext.request.contextPath}/controller">
                            <input type="hidden" name="command" value="delete_order_by_id"/>
                            <input type="hidden" name="idClient" value="${sessionScope.userId}"/>
                            <input type="hidden" name="idService" id="idService" value="${order.idService}"/>
                            <button type="submit" name="delete_order_by_id">
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
