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

    <form method="POST" action="${pageContext.request.contextPath}/controller">
    <input type="hidden" name="command" value="show_all_services">

    <div id="menu">
        <button type="submit" name="show_all_services">
            ${service_show_all}
        </button><br/>
    </div>


    <div>
        <table>
            <c:forEach var="order" items="${sessionScope.services}" >
                <tr>
                    <td>${idService} : ${order.idService}</td>
                    <td>${idClient} : ${order.idClient}</td>
                    <td>${description} : ${order.description}</td>
                    <td>${address} : ${order.address}</td>
                    <td>${serviceType} : ${order.serviceType}</td>
                    <td>${status} : ${order.status}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
    </input>
    </form>

</body>
</html>
