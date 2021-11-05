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

        <fmt:message bundle="${loc}" key="title.order" var="title_services" />
        <fmt:message bundle="${loc}" key="order.idService" var="idService" />
        <fmt:message bundle="${loc}" key="order.idClient" var="idClient" />
        <fmt:message bundle="${loc}" key="order.description" var="description" />
        <fmt:message bundle="${loc}" key="order.address" var="address" />
        <fmt:message bundle="${loc}" key="order.serviceType" var="serviceType" />
        <fmt:message bundle="${loc}" key="order.status" var="status" />

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

        <div>
            <c:forEach var="order" items="${requestScope.pageable.elements}" >
                <a href = "/controller?command=find_order_info&idService=${order.idService}">
                    <tr>
                        <td>${description} : ${order.description}</td>
                        <td>${serviceType} : ${order.serviceType}</td>
                        <td>${status} : ${order.status}</td>
                    </tr>
                    <input type="hidden" name="idService" id="idService" value="${order.idService}"/>
                </a>
            </c:forEach>
        </div>

        <div style="margin-left: center">
            <c:forEach begin="1" end="${Math.ceil(pageable.totalElements / pageable.limit)}" var="i">
                <c:if test="${i == pageable.pageNumber}">
                    <span>
                        <button style="color:red" form="show_all_services" type="submit" name="currentPage" value="${i}">${i}</button>
                    </span>
                </c:if>
                <c:if test="${i != pageable.pageNumber}">
                    <span>
                        <button form="show_all_services" type="submit" name="currentPage" value="${i}">${i}</button>
                    </span>
                </c:if>
            </c:forEach>
        </div>
    </body>
</html>
