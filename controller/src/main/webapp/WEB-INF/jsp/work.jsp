<%@page language="java" contentType="text\html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
    <head>
        <title>${work_title}</title>
        <link rel="stylesheet" href="../${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.css"/>
        <link rel="stylesheet" href="../${pageContext.request.contextPath}/resources/css/style.css"/>

        <fmt:setLocale value="${sessionScope.language}"/>
        <fmt:setBundle basename="localization.local" var="loc"/>

        <fmt:message bundle="${loc}" key="work.title" var="work_title"/>
        <fmt:message bundle="${loc}" key="work.add.order" var="work_add_service"/>
        <fmt:message bundle="${loc}" key="work.show.user.all.order" var="work_show_user_all_order"/>
        <fmt:message bundle="${loc}" key="work.find.worker.response" var="work_find_worker_response"/>
        <fmt:message bundle="${loc}" key="work.find.client.response" var="work_find_client_response"/>
        <fmt:message bundle="${loc}" key="work.go.to.update.user" var="work_go_to_update_user"/>
        <fmt:message bundle="${loc}" key="work.update.user" var="work_update_user"/>
        <fmt:message bundle="${loc}" key="show.user.orders" var="show_user_orders"/>


        <fmt:message bundle="${loc}" key="registration.firstName" var="registration_firstName"/>
        <fmt:message bundle="${loc}" key="registration.lastName" var="registration_lastName"/>
        <fmt:message bundle="${loc}" key="registration.password" var="registration_password"/>
        <fmt:message bundle="${loc}" key="registration.confirmPassword" var="registration_confirmPassword"/>
        <fmt:message bundle="${loc}" key="registration.email" var="registration_email"/>
        <fmt:message bundle="${loc}" key="registration.city" var="registration_city"/>

        <fmt:message bundle="${loc}" key="user.firstName" var="user_firstName"/>
        <fmt:message bundle="${loc}" key="user.lastName" var="user_lastName"/>
        <fmt:message bundle="${loc}" key="user.email" var="user_email"/>
        <fmt:message bundle="${loc}" key="user.city" var="user_city"/>
        <fmt:message bundle="${loc}" key="user.gender" var="user_gender"/>
        <fmt:message bundle="${loc}" key="user.info" var="user_info"/>
        <fmt:message bundle="${loc}" key="message.authorization" var="message_authorization"/>

    </head>
    <body>
        <header>
            <jsp:include page="header.jsp">
                <jsp:param name="page_path" value="work"/>
            </jsp:include>
        </header>

        <div class="container">
            <div id="error">
                <c:out value="${sessionScope.errorWorkMessage}"/>
                <c:remove var="errorWorkMessage" scope="session"/>
            </div>
            <div id="message">
                <c:out value="${sessionScope.message}"/>
                <c:remove var="message" scope="session"/>
            </div>
            <c:if test="${sessionScope.userId ne null}">
            <table>
                <div>
                    <h5>${user_info}</h5>
                </div>
                <tr>
                    <td>${user_firstName}: ${sessionScope.user.firstName}
                    </td>
                </tr>
                <tr>
                    <td>${user_lastName}: ${sessionScope.user.lastName}
                    </td>
                </tr>
                <tr>
                    <td>${user_email}: ${sessionScope.user.email}
                    </td>
                </tr>
                <tr>
                    <td>${user_city}: ${sessionScope.user.city}
                    </td>
                </tr>
            </table>
            <form id="work" method="POST" action="${pageContext.request.contextPath}/controller">
                <input type="hidden" name="command" value="work"/>
            </form>

            <form id="find_user_information" method="GET" action="${pageContext.request.contextPath}/controller">
                <input type="hidden" name="command" value="find_user_information"/>
                <input type="hidden" name="userId" value="${sessionScope.userId}"/>
            </form>
            <form id="update_user" method="POST" action="${pageContext.request.contextPath}/controller">
                <input type="hidden" name="command" value="update_user"/>
                <input type="hidden" name="userId" value="${sessionScope.userId}"/>
            </form>
            <div id="menu">
                <c:choose>
                    <c:when test="${sessionScope.userRole eq 'client'}">
                        <button class="btn btn-light" type="submit" form="work" name="work_action" value="addService">
                                ${work_add_service}
                        </button>
                        <br/>

                        <button class="btn btn-light" type="submit" form="work" name="work_action"
                                value="showUserOrder">
                                ${work_show_user_all_order}
                        </button>
                        <br/>
                        <div>
                            <a class="btn btn-light" href="/controller?command=work&work_action=find_client_response">
                                    ${work_find_client_response}
                            </a>
                            <br/>
                        </div>

                    </c:when>

                    <c:when test="${sessionScope.userRole eq 'worker'}">
                        <div>
                            <a class="btn btn-light" href="/controller?command=work&work_action=find_worker_response">
                                    ${work_find_worker_response}
                            </a>
                            <br/>
                        </div>
                    </c:when>
                </c:choose>
            </div>

                <%--            UPDATE_USER                --%>

            <c:choose>
                <c:when test="${requestScope.last_command ne 'find_user_information'}">
                    <button class="btn btn-light" form="find_user_information" type="submit"
                            name="find_user_information">
                            ${work_go_to_update_user}
                    </button>
                    <br/>
                </c:when>

                <c:when test="${requestScope.last_command eq 'find_user_information'}">

                    <div class="row">
                        <div class="input-group mb-3 col-6">
                            <div class="input-group-prepend">
                                <span class="input-group-text">${registration_firstName}</span>
                            </div>
                            <input form="update_user" type="text" class="form-control" name="firstName"
                                   id="firstNameInput" value="${sessionScope.user.firstName}"/>
                        </div>
                    </div>

                    <div class="row">
                        <div class="input-group mb-3 col-6">
                            <div class="input-group-prepend">
                                <span class="input-group-text">${registration_lastName}</span>
                            </div>
                            <input form="update_user" type="text" class="form-control" name="lastName"
                                   id="lastNameInput" value="${sessionScope.user.lastName}"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-group mb-3 col-6">
                            <div class="input-group-prepend">
                                <span class="input-group-text">${registration_email}</span>
                            </div>
                            <input form="update_user" type="text" class="form-control" name="email" id="emailInput"
                                   value="${sessionScope.user.email}"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-group mb-3 col-6">
                            <div class="input-group-prepend">
                                <span class="input-group-text">${registration_city}</span>
                            </div>
                            <input form="update_user" type="text" class="form-control" name="city" id="cityInput"
                                   value="${sessionScope.user.city}"/>
                        </div>
                    </div>

                    <div class="row">
                        <div class="input-group mb-3 col-6">
                            <div class="input-group-prepend">
                                <span class="input-group-text">${registration_password}</span>
                            </div>
                            <input form="update_user" type="password" class="form-control" name="password"
                                   id="passwordInput" value=""/>
                            <span class="form-text">Enter 5-20 symbols.</span>
                        </div>
                    </div>

                    <div class="row">
                        <div class="input-group mb-3 col-6">
                            <div class="input-group-prepend">
                                <span class="input-group-text">${registration_confirmPassword}</span>
                            </div>
                            <input form="update_user" type="password" class="form-control" name="confirmPassword"
                                   id="confirmPasswordInput"
                                   value=""/>
                            <span class="form-text">Enter 5-20 symbols.</span>
                        </div>
                    </div>

                    <button class="btn btn-light" form="update_user" type="submit" name="update_user">
                            ${work_update_user}
                    </button>
                    <br/>
                </c:when>
            </c:choose>

        </div>
        </c:if>
        <c:if test="${sessionScope.userId eq null}">
            <a class="btn btn-light" href="/controller?command=go_to_page&path=authorization">
                    ${message_authorization}
            </a>
        </c:if>

    </body>
</html>



