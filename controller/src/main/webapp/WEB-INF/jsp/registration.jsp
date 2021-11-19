<%--
  Created by IntelliJ IDEA.
  User: arina
  Date: 9/5/21
  Time: 14:41
  To change this template use File | Settings | File Templates.
--%>
<%@page language="java" contentType="text\html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title>${title_registration}</title>
    <link rel="stylesheet" href="../${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.css"/>
    <link rel="stylesheet" href="../${pageContext.request.contextPath}/resources/css/style.css"/>

    <fmt:setLocale value="${sessionScope.language}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>

    <fmt:message bundle="${loc}" key="title.registration" var="title_registration"/>
    <fmt:message bundle="${loc}" key="registration.firstName" var="registration_firstName"/>
    <fmt:message bundle="${loc}" key="registration.lastName" var="registration_lastName"/>
    <fmt:message bundle="${loc}" key="registration.login" var="registration_login"/>
    <fmt:message bundle="${loc}" key="registration.password" var="registration_password"/>
    <fmt:message bundle="${loc}" key="registration.confirmPassword" var="registration_confirmPassword"/>
    <fmt:message bundle="${loc}" key="registration.gender" var="registration_gender"/>
    <fmt:message bundle="${loc}" key="registration.email" var="registration_email"/>
    <fmt:message bundle="${loc}" key="registration.city" var="registration_city"/>
    <fmt:message bundle="${loc}" key="registration.userRole" var="registration_userRole"/>
</head>
<body>

<header>
    <jsp:include page="header.jsp">
        <jsp:param name="page_path" value="registration"/>
    </jsp:include>
</header>

    <div id="error">
        <h1 style="color:red;">${errorRegistration}</h1>
    </div>

<form method="POST" action="${pageContext.request.contextPath}/controller">
    <input type="hidden" name="command" value="registration"/>
<%--    <input type="hidden" name="userId" value="${sessionScope.userId}"/>--%>
    <div class="row mb-3">
        <label for="firstNameInput" class="col-sm-2 col-form-label form-control-lg">${registration_firstName}</label>
        <div class="col-sm-10">
            <input type="text" class="form-control" id="firstNameInput" name="firstName" value="" />
        </div>
    </div>
    <div class="row mb-3">
        <label for="lastNameInput" class="col-sm-2 col-form-label form-control-lg">${registration_lastName}</label>
        <div class="col-sm-10">
            <input type="text" class="form-control" id="lastNameInput" name="lastName" value=""/>
        </div>
    </div>
    <div class="row mb-3">
        <label for="loginInput" class="col-sm-2 col-form-label form-control-lg">${registration_login}</label>
        <div class="col-sm-10">
            <input type="text" class="form-control" id="loginInput" name="login" value=""/>
        </div>
    </div>
    <div class="row mb-3">
        <label for="emailInput" class="col-sm-2 col-form-label form-control-lg">${registration_email}</label>
        <div class="col-sm-10">
            <input type="text" class="form-control" id="emailInput" name="email" value=""/>
        </div>
    </div>
    <div class="row mb-3">
        <label for="cityInput" class="col-sm-2 col-form-label form-control-lg">${registration_city}</label>
        <div class="col-sm-10">
            <input type="text" class="form-control" id="cityInput" name="city" value=""/>
        </div>
    </div>
    <div class="row mb-3">
        <label for="passwordInput" class="col-sm-2 col-form-label form-control-lg">${registration_password}</label>
        <div class="col-sm-10">
            <input type="password" class="form-control" id="passwordInput" name="password" value=""/>
            <span class="form-text">Enter 5-20 symbols.</span>
        </div>
    </div>
    <div class="row mb-3">
        <label for="confirmPasswordInput" class="col-sm-2 col-form-label form-control-lg">${registration_confirmPassword}</label>
        <div class="col-sm-10">
            <input type="confirmPassword" class="form-control" id="confirmPasswordInput" name="confirmPassword" value=""/>
            <span class="form-text">Enter 5-20 symbols.</span>
        </div>
    </div>
    <fieldset class="row mb-3" name="gender">
        <legend class="col-form-label col-sm-2 pt-0 form-control-lg">${registration_gender}</legend>
        <div class="col-sm-10">
            <div class="form-check">
                <input checked="checked" class="form-check-input" type="radio" name="gender" id="undefinedRadios" value="undefined" />
                <label class="form-check-label" for="undefinedRadios">
                    Undefind
                </label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="radio" name="gender" id="maleRadios" value="male"/>
                <label class="form-check-label" for="maleRadios">
                    Male
                </label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="radio" name="gender" id="femaleRadios" value="female"/>
                <label class="form-check-label" for="femaleRadios">
                    Female
                </label>
            </div>
        </div>
    </fieldset>
    <fieldset class="row mb-3" name="userRole">
        <legend class="col-form-label col-sm-2 pt-0 form-control-lg">${registration_userRole}</legend>
        <div class="col-sm-10">
            <div class="form-check">
                <input checked="checked" class="form-check-input" type="radio" name="userRole" id="clientRadios" value="client" />
                <label class="form-check-label" for="clientRadios">
                    Client
                </label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="radio" name="userRole" id="workerRadios" value="worker"/>
                <label class="form-check-label" for="workerRadios">
                    Worker
                </label>
            </div>
        </div>
    </fieldset>

    <button type="submit" class="btn btn-light">Sign in</button>

</form>
</body>
</html>