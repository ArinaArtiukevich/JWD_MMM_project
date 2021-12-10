<%@page language="java" contentType="text\html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<html>
    <head>
        <title>Responses</title>
        <link rel="stylesheet" href="../${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.css"/>
        <link rel="stylesheet" href="../${pageContext.request.contextPath}/resources/css/style.css"/>


        <fmt:setLocale value="${sessionScope.language}"/>
        <fmt:setBundle basename="localization.local" var="loc"/>
        <fmt:message bundle="${loc}" key="button.order.show.all" var="service_show_all"/>
        <fmt:message bundle="${loc}" key="work.find.worker.response" var="work_find_worker_response"/>
        <fmt:message bundle="${loc}" key="order.description" var="description"/>
        <fmt:message bundle="${loc}" key="order.address" var="address"/>
        <fmt:message bundle="${loc}" key="order.serviceType" var="serviceType"/>
        <fmt:message bundle="${loc}" key="order.status" var="status"/>
        <fmt:message bundle="${loc}" key="order.idWorker" var="idWorker"/>
        <fmt:message bundle="${loc}" key="order.idClient" var="idClient"/>
        <fmt:message bundle="${loc}" key="order.idService" var="idService"/>
        <fmt:message bundle="${loc}" key="order.orderCreationDate" var="orderCreationDate"/>
        <fmt:message bundle="${loc}" key="message.sort.by" var="message_sort_by"/>
        <fmt:message bundle="${loc}" key="direction.change" var="direction_change"/>

    </head>
    <body>
        <header>
            <jsp:include page="header.jsp">
                <jsp:param name="page_path" value="workerResponses"/>
            </jsp:include>
        </header>

        <div class="container">
            <form id="find_worker_response" method="GET" action="${pageContext.request.contextPath}/controller">
                <input type="hidden" name="command" value="find_worker_response">
                <input type="hidden" name="idWorker" value="${sessionScope.userId}">
                    <div class="btn-group-vertical" role="group">
                        <div>
                            <button class="btn btn-info" type="submit" name="find_worker_response">
                                    ${work_find_worker_response}
                            </button>
                        </div>
                        <div id="sort_by">
                                ${message_sort_by}
                            <select class="custom-select col-auto my-1" name="sort_by">
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
                </input>
            </form>
        </div>
        <div align="container" style="padding-top: 50px;">
            <table class="table table-striped">
                <c:if test="${sessionScope.userRole eq 'worker'}">
                    <c:if test="${requestScope.pageable.elements.size() > 0}">

                        <tr>
                            <th>${idService}</th>
                            <th>${description}</th>
                            <th>${serviceType}</th>
                            <th>${status}</th>
                            <th>${orderCreationDate} </th>
                        </tr>
                    </c:if>

                    <c:forEach var="response" items="${requestScope.pageable.elements}">
                        <tr>
                            <td>
                                <a href="/controller?command=find_order_info&idService=${response.idService}">${response.idService}</a>
                            </td>
                            <td>${response.description}</td>
                            <td>${response.serviceType}</td>
                            <td>${response.status}</td>
                            <td><fmt:formatDate value="${response.orderCreationDate}" pattern="yyyy.MM.dd"/></td>
                        </tr>
                    </c:forEach>
                </c:if>
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


