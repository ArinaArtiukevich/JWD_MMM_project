<%@page language="java" contentType="text\html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<html>
    <head>
        <title>${title_order_information}</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css"/>


        <fmt:setLocale value="${sessionScope.language}"/>
        <fmt:setBundle basename="localization.local" var="loc"/>

        <fmt:message bundle="${loc}" key="button.take.order" var="button_take_order" />
        <fmt:message bundle="${loc}" key="button.close.order" var="button_close_order" />
        <fmt:message bundle="${loc}" key="button.approve.order" var="button_approve_order" />
        <fmt:message bundle="${loc}" key="title.orderInformation" var="title_order_information" />
        <fmt:message bundle="${loc}" key="order.idService" var="idService" />
        <fmt:message bundle="${loc}" key="order.idClient" var="idClient" />
        <fmt:message bundle="${loc}" key="order.description" var="description" />
        <fmt:message bundle="${loc}" key="order.address" var="address" />
        <fmt:message bundle="${loc}" key="order.serviceType" var="serviceType" />
        <fmt:message bundle="${loc}" key="order.status" var="status" />
        <fmt:message bundle="${loc}" key="order.orderCreationDate" var="orderCreationDate" />

        <fmt:message bundle="${loc}" key="user.firstName" var="user_firstName" />
        <fmt:message bundle="${loc}" key="user.lastName" var="user_lastName" />
        <fmt:message bundle="${loc}" key="user.email" var="user_email" />
        <fmt:message bundle="${loc}" key="user.city" var="user_city" />
        <fmt:message bundle="${loc}" key="user.gender" var="user_gender" />

    </head>
    <body>
        <header>
            <jsp:include page="header.jsp">

                <jsp:param name="page_path" value="orderInfo"/>
            </jsp:include>
        </header>

        <form method="GET" action="${pageContext.request.contextPath}/controller">

            <div>
                <table>
                    <tr>
                        <td>${idService} : ${sessionScope.order.idService}<br/></td>
                        <td>${idClient} : ${sessionScope.order.idClient}<br/></td>
                        <td>${description} : ${sessionScope.order.description}<br/></td>
                        <td>${address} : ${sessionScope.order.address}<br/></td>
                        <td>${serviceType} : ${sessionScope.order.serviceType}<br/></td>
                        <td>${status} : ${sessionScope.order.status}<br/></td>
                        <td>${orderCreationDate} : <fmt:formatDate value="${sessionScope.order.orderCreationDate}" pattern="yyyy.MM.dd" /><br/></td>
                    </tr>
                </table>
            </div>
            <c:if test="${sessionScope.userRole eq 'worker'}">
                <table>
                    <div>Client info :</div>
                    <tr>
                        <td>${user_firstName} : ${sessionScope.client.firstName}<br/></td>
                        <td>${user_lastName} : ${sessionScope.client.lastName}<br/></td>
                        <td>${user_gender} : ${sessionScope.client.gender}<br/></td>
                        <td>${user_email} : ${sessionScope.client.email}<br/></td>
                        <td>${user_city} : ${sessionScope.client.city}<br/></td>
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
                    <button type="submit" class="btn btn-light">${button_close_order}</button>
                </c:if>
            </c:if>
            <c:if test="${sessionScope.userRole eq 'client'}">
                <c:if test="${sessionScope.userId eq sessionScope.order.idClient}">
                    <c:if test="${sessionScope.order.status.toString() eq 'DONE'}">
                        <input type="hidden" name="command" value="approve_order"/>
                        <input type="hidden" name="idService" value="${sessionScope.order.idService}"/>
                        <button type="submit" class="btn btn-light">${button_approve_order}</button>
                    </c:if>
                </c:if>
            </c:if>
        </form>
    </body>
</html>
