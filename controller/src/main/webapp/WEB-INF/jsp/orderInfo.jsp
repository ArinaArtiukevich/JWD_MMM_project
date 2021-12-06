<%@page language="java" contentType="text\html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<html>
    <head>
        <title>OrderInfo</title>
        <link rel="stylesheet" href="../${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.css"/>
        <link rel="stylesheet" href="../${pageContext.request.contextPath}/resources/css/style.css"/>


        <fmt:setLocale value="${sessionScope.language}"/>
        <fmt:setBundle basename="localization.local" var="loc"/>

        <fmt:message bundle="${loc}" key="button.take.order" var="button_take_order"/>
        <fmt:message bundle="${loc}" key="button.close.order" var="button_close_order"/>
        <fmt:message bundle="${loc}" key="button.approve.order" var="button_approve_order"/>
        <fmt:message bundle="${loc}" key="order.idService" var="idService"/>
        <fmt:message bundle="${loc}" key="order.idClient" var="idClient"/>
        <fmt:message bundle="${loc}" key="order.description" var="description"/>
        <fmt:message bundle="${loc}" key="order.address" var="address"/>
        <fmt:message bundle="${loc}" key="order.serviceType" var="serviceType"/>
        <fmt:message bundle="${loc}" key="order.status" var="status"/>
        <fmt:message bundle="${loc}" key="order.orderCreationDate" var="orderCreationDate"/>

        <fmt:message bundle="${loc}" key="user.firstName" var="user_firstName"/>
        <fmt:message bundle="${loc}" key="user.lastName" var="user_lastName"/>
        <fmt:message bundle="${loc}" key="user.email" var="user_email"/>
        <fmt:message bundle="${loc}" key="user.city" var="user_city"/>
        <fmt:message bundle="${loc}" key="user.gender" var="user_gender"/>
        <fmt:message bundle="${loc}" key="client.info" var="client_info"/>

    </head>
    <body>
        <header>
            <jsp:include page="header.jsp">

                <jsp:param name="page_path" value="orderInfo"/>
            </jsp:include>
        </header>

        <form method="GET" action="${pageContext.request.contextPath}/controller">
            <div align="container" style="padding-top: 50px;">
                <table class="table table-striped">
                    <tr>
                        <th scope="col">${idService}</th>
                        <th scope="col">${idClient}</th>
                        <th scope="col">${description}</th>
                        <th scope="col">${address}</th>
                        <th scope="col">${serviceType}</th>
                        <th scope="col">${status}</th>
                        <th scope="col">${orderCreationDate} </th>
                    </tr>
                    <tr>
                        <td>${sessionScope.order.idService}</td>
                        <td>${sessionScope.order.idClient}</td>
                        <td>${sessionScope.order.description}</td>
                        <td>${sessionScope.order.address}</td>
                        <td>${sessionScope.order.serviceType}</td>
                        <td>${sessionScope.order.status}</td>
                        <td><fmt:formatDate value="${sessionScope.order.orderCreationDate}" pattern="yyyy.MM.dd"/></td>
                    </tr>
                </table>
            </div>
            <c:if test="${sessionScope.userRole eq 'worker'}">
                <table class="table table-striped">
                    <div>
                        <b>${client_info}</b>
                    </div>
                    <tr>
                        <th scope="col">${user_firstName}</th>
                        <th scope="col">${user_lastName}</th>
                        <th scope="col">${user_gender}</th>
                        <th scope="col">${user_email}</th>
                        <th scope="col">${user_city}</th>
                    </tr>
                    <tr>
                        <td>${sessionScope.client.firstName}</td>
                        <td>${sessionScope.client.lastName}</td>
                        <td>${sessionScope.client.gender}</td>
                        <td>${sessionScope.client.email}</td>
                        <td>${sessionScope.client.city}</td>
                    </tr>
                </table>
                <c:if test="${sessionScope.order.status.toString() eq 'FREE'}">
                    <input type="hidden" name="command" value="take_order"/>
                    <input type="hidden" name="idService" value="${sessionScope.order.idService}"/>
                    <button type="submit" class="btn btn-light">${button_take_order}</button>
                </c:if>

                <c:if test="${sessionScope.order.status.toString() eq 'IN_PROCESS'}">
                    <input type="hidden" name="command" value="close_order"/>
                    <input type="hidden" name="idService" value="${sessionScope.order.idService}"/>
                    <input type="hidden" name="idUser" value="${sessionScope.userId}"/>
                    <button type="submit" class="btn btn-light">${button_close_order}</button>
                </c:if>
            </c:if>
            <c:if test="${sessionScope.userRole eq 'client'}">
                <c:if test="${sessionScope.userId eq sessionScope.order.idClient}">
                    <c:if test="${sessionScope.order.status.toString() eq 'DONE'}">
                        <input type="hidden" name="command" value="approve_order"/>
                        <input type="hidden" name="idService" value="${sessionScope.order.idService}"/>
                        <input type="hidden" name="idUser" value="${sessionScope.userId}"/>
                        <button type="submit" class="btn btn-light">${button_approve_order}</button>
                    </c:if>
                </c:if>
            </c:if>
        </form>
    </body>
</html>
