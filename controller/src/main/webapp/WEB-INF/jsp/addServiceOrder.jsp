<%@page language="java" contentType="text\html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title>${title_addServiceOrder}</title>
    <link rel="stylesheet" href="../${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.css"/>
    <link rel="stylesheet" href="../${pageContext.request.contextPath}/resources/css/style.css"/>

    <fmt:setLocale value="${sessionScope.language}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
    <fmt:message bundle="${loc}" key="title.addServiceOrder" var="title_addServiceOrder"/>

    <fmt:message bundle="${loc}" key="order.description" var="description" />
    <fmt:message bundle="${loc}" key="order.address" var="address" />
    <fmt:message bundle="${loc}" key="order.serviceType" var="serviceType" />
    <fmt:message bundle="${loc}" key="service.type.electrical" var="service_type_electrical"/>
    <fmt:message bundle="${loc}" key="service.type.gas" var="service_type_gas"/>
    <fmt:message bundle="${loc}" key="service.type.roofing" var="service_type_roofing"/>
    <fmt:message bundle="${loc}" key="service.type.painting" var="service_type_painting"/>
    <fmt:message bundle="${loc}" key="service.type.plumbing" var="service_type_plumbing"/>
</head>
<body>

<header>
    <jsp:include page="header.jsp">
        <jsp:param name="page_path" value="addServiceOrder"/>
    </jsp:include>
</header>

    <div>
        <h1 style="color:red;"> id : ${sessionScope.userId}</h1>
        <h1 style="color:red;"> login : ${sessionScope.login}</h1>
    </div>

    <form method="POST" action="${pageContext.request.contextPath}/controller"/>
    <input type="hidden" name="command" value="add_service_order"/>

    <div id="error">
        <h1 style="color:red;"> ${errorAddServiceOrder}</h1>
    </div>

    <div class="row mb-3">
        <label for="descriptionInput" class="col-sm-2 col-form-label form-control-lg">${description}</label>
        <div class="col-sm-10">
            <input type="text" class="form-control" id="descriptionInput" name="description" value=""/>
        </div>
    </div>
    <div class="row mb-3">
        <label for="addressInput" class="col-sm-2 col-form-label form-control-lg">${address}</label>
        <div class="col-sm-10">
            <input type="text" class="form-control" id="addressInput" name="address" value=""/>
        </div>
    </div>

    <fieldset class="row mb-3" name="gender">
        <legend class="col-form-label col-sm-2 pt-0 form-control-lg">${serviceType}</legend>
        <div class="col-sm-10">
            <div class="form-check">
                <input checked="checked" class="form-check-input" type="radio" name="service_type" id="electricalRadios" value="electrical" />
                <label class="form-check-label" for="electricalRadios">
                    ${service_type_electrical}
                </label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="radio" name="service_type" id="gasRadios" value="gas"/>
                <label class="form-check-label" for="gasRadios">
                    ${service_type_gas}
                </label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="radio" name="service_type" id="roofingRadios" value="roofing"/>
                <label class="form-check-label" for="roofingRadios">
                    ${service_type_roofing}
                </label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="radio" name="service_type" id="paintingRadios" value="painting"/>
                <label class="form-check-label" for="paintingRadios">
                    ${service_type_painting}
                </label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="radio" name="service_type" id="plumbingRadios" value="plumbing"/>
                <label class="form-check-label" for="plumbingRadios">
                    ${service_type_plumbing}
                </label>
            </div>
        </div>
    </fieldset>
    <button type="submit" class="btn btn-light">add</button>
</body>
</html>