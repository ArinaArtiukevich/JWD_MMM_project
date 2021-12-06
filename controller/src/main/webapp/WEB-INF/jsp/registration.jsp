<%--
  Created by IntelliJ IDEA.
  User: arina
  Date: 9/5/21
  Time: 14:41
  To change this template use File | Settings | File Templates.
--%>
<%@page language="java" contentType="text\html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
    <head>
        <title>SignUp</title>
        <link rel="stylesheet" href="../${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.css"/>
        <link rel="stylesheet" href="../${pageContext.request.contextPath}/resources/css/style.css"/>

        <fmt:setLocale value="${sessionScope.language}"/>
        <fmt:setBundle basename="localization.local" var="loc"/>

        <fmt:message bundle="${loc}" key="registration.firstName" var="registration_firstName"/>
        <fmt:message bundle="${loc}" key="registration.lastName" var="registration_lastName"/>
        <fmt:message bundle="${loc}" key="registration.login" var="registration_login"/>
        <fmt:message bundle="${loc}" key="registration.password" var="registration_password"/>
        <fmt:message bundle="${loc}" key="registration.confirmPassword" var="registration_confirmPassword"/>
        <fmt:message bundle="${loc}" key="registration.gender" var="registration_gender"/>
        <fmt:message bundle="${loc}" key="registration.email" var="registration_email"/>
        <fmt:message bundle="${loc}" key="registration.city" var="registration_city"/>
        <fmt:message bundle="${loc}" key="registration.userRole" var="registration_userRole"/>
        <fmt:message bundle="${loc}" key="registration.gender.undefined" var="registration_gender_undefined"/>
        <fmt:message bundle="${loc}" key="registration.gender.male" var="registration_gender_male"/>
        <fmt:message bundle="${loc}" key="registration.gender.female" var="registration_gender_female"/>
        <fmt:message bundle="${loc}" key="registration.userRole.worker" var="registration_userRole_worker"/>
        <fmt:message bundle="${loc}" key="registration.userRole.client" var="registration_userRole_client"/>
        <fmt:message bundle="${loc}" key="registration.message.password" var="registration_message_password"/>
        <fmt:message bundle="${loc}" key="button.registration" var="button_registration"/>

    </head>
    <body>

        <header>
            <jsp:include page="header.jsp">
                <jsp:param name="page_path" value="registration"/>
            </jsp:include>
        </header>

        <div class="container">
            <div id="error">
                <c:out value="${sessionScope.errorRegistration}"/>
                <c:remove var="errorRegistration" scope="session"/>
            </div>
            <form method="POST" action="${pageContext.request.contextPath}/controller">
                <input type="hidden" name="command" value="registration"/>
                <div class="row">
                    <div class="input-group mb-3 col-6">
                        <div class="input-group-prepend">
                            <span class="input-group-text">${registration_firstName}</span>
                        </div>
                        <input type="text" class="form-control" name="firstName" id="firstNameInput" value=""/>
                    </div>
                </div>
                <div class="row">
                    <div class="input-group mb-3 col-6">
                        <div class="input-group-prepend">
                            <span class="input-group-text">${registration_lastName}</span>
                        </div>
                        <input type="text" class="form-control" name="lastName" id="lastNameInput" value=""/>
                    </div>
                </div>
                <div class="row">
                    <div class="input-group mb-3 col-6">
                        <div class="input-group-prepend">
                            <span class="input-group-text">${registration_login}</span>
                        </div>
                        <input type="text" class="form-control" name="login" id="loginInput" value=""/>
                    </div>
                </div>
                <div class="row">
                    <div class="input-group mb-3 col-6">
                        <div class="input-group-prepend">
                            <span class="input-group-text">${registration_email}</span>
                        </div>
                        <input type="text" class="form-control" name="email" id="emailInput" placeholder="name@example.com" value=""/>
                    </div>
                </div>
                <div class="row">
                    <div class="input-group mb-3 col-6">
                        <div class="input-group-prepend">
                            <span class="input-group-text">${registration_city}</span>
                        </div>
                        <input type="text" class="form-control" name="city" id="cityInput" value=""/>
                    </div>
                </div>
                <div class="row">
                    <div class="input-group mb-3 col-6">
                        <div class="input-group-prepend">
                            <span class="input-group-text">${registration_password}</span>
                        </div>
                        <input type="password" class="form-control" name="password" id="passwordInput" value=""/>
                        <span class="form-text">${registration_message_password}</span>
                    </div>
                </div>

                <div class="row">
                    <div class="input-group mb-3 col-6">
                        <div class="input-group-prepend">
                            <span class="input-group-text">${registration_confirmPassword}</span>
                        </div>
                        <input type="password" class="form-control" name="confirmPassword" id="confirmPasswordInput"
                               value=""/>
                        <span class="form-text">${registration_message_password}</span>
                    </div>
                </div>

                <div class="row">
                    <td>
                        <fieldset class="mb-3" name="gender">
                            <legend class="col-form-label pt-0 form-control-lg">${registration_gender}</legend>
                            <div class="col-2">
                                <div class="form-check">
                                    <input checked="checked" class="form-check-input" type="radio" name="gender"
                                           id="undefinedRadios" value="undefined"/>
                                    <label class="form-check-label" for="undefinedRadios">
                                            ${registration_gender_undefined}
                                    </label>
                                </div>
                                <div class="form-check">
                                    <input class="form-check-input" type="radio" name="gender" id="maleRadios"
                                           value="male"/>
                                    <label class="form-check-label" for="maleRadios">
                                            ${registration_gender_male}
                                    </label>
                                </div>
                                <div class="form-check">
                                    <input class="form-check-input" type="radio" name="gender" id="femaleRadios"
                                           value="female"/>
                                    <label class="form-check-label" for="femaleRadios">
                                            ${registration_gender_female}
                                    </label>
                                </div>
                            </div>
                        </fieldset>
                    </td>
                    <td>
                        <fieldset class="mb-3" name="userRole">
                            <legend class="col-form-label pt-0 form-control-lg">${registration_userRole}</legend>
                            <div class="col-2">
                                <div class="form-check">
                                    <input checked="checked" class="form-check-input" type="radio" name="userRole"
                                           id="clientRadios"
                                           value="client"/>
                                    <label class="form-check-label" for="clientRadios">
                                            ${registration_userRole_client}
                                    </label>
                                </div>
                                <div class="form-check">
                                    <input class="form-check-input" type="radio" name="userRole" id="workerRadios"
                                           value="worker"/>
                                    <label class="form-check-label" for="workerRadios">
                                            ${registration_userRole_worker}
                                    </label>
                                </div>
                            </div>
                        </fieldset>
                    </td>
                </div>
                <button type="submit" class="btn btn-light">${button_registration}</button
            </form>
        </div>
    </body>
</html>