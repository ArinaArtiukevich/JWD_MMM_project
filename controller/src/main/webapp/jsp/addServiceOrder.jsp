<%@page language="java" contentType="text\html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title>${title_addServiceOrder}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css"/>

    <fmt:setLocale value="${sessionScope.language}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
    <fmt:message bundle="${loc}" key="title.addServiceOrder" var="title_addServiceOrder"/>

    <fmt:message bundle="${loc}" key="order.description" var="description" />
    <fmt:message bundle="${loc}" key="order.address" var="address" />
    <fmt:message bundle="${loc}" key="order.serviceType" var="serviceType" />
</head>
<body>

<header>
    <jsp:include page="header.jsp">
        <jsp:param name="page_path" value="/jsp/addServiceOrder.jsp"/>
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
                <input checked="checked" class="form-check-input" type="radio" name="serviceType" id="electricalRadios" value="electrical" />
                <label class="form-check-label" for="electricalRadios">
                    ELECTRICAL
                </label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="radio" name="serviceType" id="gasRadios" value="gas"/>
                <label class="form-check-label" for="gasRadios">
                    GAS
                </label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="radio" name="serviceType" id="roofingRadios" value="roofing"/>
                <label class="form-check-label" for="roofingRadios">
                    ROOFING
                </label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="radio" name="serviceType" id="paintingRadios" value="painting"/>
                <label class="form-check-label" for="paintingRadios">
                    PAINTING
                </label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="radio" name="serviceType" id="plumbingRadios" value="plumbing"/>
                <label class="form-check-label" for="plumbingRadios">
                    PLUMBING
                </label>
            </div>
        </div>
    </fieldset>
    <button type="submit" class="btn btn-primary">add</button>
</body>
</html>