<%@page language="java" contentType="text\html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
    <head>
        <title>${title_find_worker_response}</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css"/>

        <fmt:setLocale value="${sessionScope.language}"/>
        <fmt:setBundle basename="localization.local" var="loc"/>

        <fmt:message bundle="${loc}" key="title.find.worker.response" var="title_find_worker_response"/>
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

    </head>
    <body>
        <header>
            <jsp:include page="header.jsp">
                <jsp:param name="page_path" value="/jsp/showUserOrder.jsp"/>
            </jsp:include>
        </header>

        <div>
            <h1 style="color:red;">id : ${sessionScope.userId}</h1>
            <h1 style="color:red;">login : ${sessionScope.login}</h1>
        </div>
        <form id="show_user_orders" method="GET" action="${pageContext.request.contextPath}/controller">
        <input type="hidden" name="command" value="show_user_orders">
            <div id="menu">
                <button>
                        ${show_user_orders}
                </button><br/>
            </div>
        </input>
        </form>
        <div>
            <table>
                <c:forEach var="order" items="${requestScope.pageable.elements}" >
                    <a href = "/controller?command=show_user_orders&idService=${order.idService}">
                        <tr>
                            <td>${idService} : ${order.idService}</td>
                            <td>${idClient} : ${order.idClient}</td>
                            <td>${description} : ${order.description}</td>
                            <td>${address} : ${order.address}</td>
                            <td>${serviceType} : ${order.serviceType}</td>
                            <td>${status} : ${order.status}</td>
                            <td>${orderCreationDate} : <fmt:formatDate value="${order.orderCreationDate}" pattern="yyyy.MM.dd" /></td>
                        </tr>
                        <input type="hidden" name="idService" id="idService" value="${order.idService}"/>
                    </a>
                </c:forEach>
            </table>
        </div>

        <div style="margin-left: center">
            <c:forEach begin="1" end="${Math.ceil(pageable.totalElements / pageable.limit)}" var="i">
                <c:if test="${i == pageable.pageNumber}">
                    <span>
                        <button style="color:red" form="show_user_orders" type="submit" name="currentPage" value="${i}">${i}</button>
                    </span>
                </c:if>
                <c:if test="${i != pageable.pageNumber}">
                    <span>
                        <button form="show_user_orders" type="submit" name="currentPage" value="${i}">${i}</button>
                    </span>
                </c:if>
            </c:forEach>
        </div>

    </body>
</html>
