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
        <fmt:message bundle="${loc}" key="work.find.worker.response" var="work_find_worker_response"/>
        <fmt:message bundle="${loc}" key="title.order" var="title_services" />
        <fmt:message bundle="${loc}" key="order.description" var="description" />
        <fmt:message bundle="${loc}" key="order.address" var="address" />
        <fmt:message bundle="${loc}" key="order.serviceType" var="serviceType" />
        <fmt:message bundle="${loc}" key="order.status" var="status" />
        <fmt:message bundle="${loc}" key="order.idWorker" var="idWorker" />
        <fmt:message bundle="${loc}" key="order.idClient" var="idClient" />
        <fmt:message bundle="${loc}" key="order.orderCreationDate" var="orderCreationDate" />
        <fmt:message bundle="${loc}" key="message.sort.by" var="message_sort_by" />
        <fmt:message bundle="${loc}" key="direction.change" var="direction_change" />

    </head>
    <body>
        <header>
            <jsp:include page="header.jsp">
                <jsp:param name="page_path" value="workerResponses"/>
            </jsp:include>
        </header>

        <form id="find_worker_response" method="GET" action="${pageContext.request.contextPath}/controller">
            <input type="hidden" name="command" value="find_worker_response">
            <input type="hidden" name="idWorker" value="${sessionScope.userId}">
                <div>
                    <button type="submit" name="find_worker_response">
                            ${work_find_worker_response}
                    </button>

                    <div id="sort_by">
                        <h7>${message_sort_by}</h7>
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
        <div>
            <c:if test="${sessionScope.userRole eq 'worker'}">
                <c:forEach var="response" items="${requestScope.pageable.elements}" >
                    <a href = "/controller?command=find_order_info&idService=${response.idService}">
                        <tr>
                            <td>${idClient}: ${response.idClient}</td>
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

