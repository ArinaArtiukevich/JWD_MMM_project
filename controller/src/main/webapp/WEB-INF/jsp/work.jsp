<%@page language="java" contentType="text\html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
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

        <fmt:message bundle="${loc}" key="user.firstName" var="user_firstName" />
        <fmt:message bundle="${loc}" key="user.lastName" var="user_lastName" />
        <fmt:message bundle="${loc}" key="user.email" var="user_email" />
        <fmt:message bundle="${loc}" key="user.city" var="user_city" />
        <fmt:message bundle="${loc}" key="user.gender" var="user_gender" />

    </head>
    <body>
        <header>
            <jsp:include page="header.jsp">
                <jsp:param name="page_path" value="work"/>
            </jsp:include>
        </header>

        <div>
            <h1>id : ${sessionScope.userId}</h1>
            <h1>login : ${sessionScope.login}</h1>
        </div>

        <div id="error">
            <h1 style="color:red;"> ${errorWorkMessage}</h1>
        </div>
        <div id="message">
            <h1> ${requestScope.message}</h1>
        </div>

        <table>
            <div>User info :</div>
            <tr>
                <td>${user_firstName} : ${requestScope.firstName}<br/></td>
                <td>${user_lastName} : ${requestScope.lastName}<br/></td>
                <td>${user_email} : ${requestScope.email}<br/></td>
                <td>${user_city} : ${requestScope.city}<br/></td>
            </tr>
        </table>

        <c:if test="${sessionScope.userId ne null}">
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
                        <button type="submit" form="work" name="work_action" value="addService">
                                ${work_add_service}
                        </button><br/>

                        <button type="submit" form="work" name="work_action" value="showUserOrder">
                                ${work_show_user_all_order}
                        </button><br/>
                        <div>
                            <a class="btn btn-light" href="/controller?command=work&work_action=find_client_response">
                                    ${work_find_client_response}
                            </a><br/>
                        </div>

                    </c:when>

                    <c:when test="${sessionScope.userRole eq 'worker'}">
                        <div>
                            <a class="btn btn-light" href="/controller?command=work&work_action=find_worker_response">
                                    ${work_find_worker_response}
                            </a><br/>
                        </div>
                    </c:when>
                </c:choose>

                    <%--            UPDATE_USER                --%>

                <c:choose>
                    <c:when test="${requestScope.last_command ne 'find_user_information'}">
                        <button form="find_user_information" type="submit" name="find_user_information">
                                ${work_go_to_update_user}
                        </button><br/>
                    </c:when>

                    <c:when test="${requestScope.last_command eq 'find_user_information'}">

                        <div class="row mb-3">
                            <label for="firstNameInput" class="col-sm-2 col-form-label form-control-lg">${registration_firstName}</label>
                            <div class="col-sm-10">
                                <input form="update_user" type="text" class="form-control" id="firstNameInput" name="firstName" value="${requestScope.firstName}" />
                            </div>
                        </div>
                        <div class="row mb-3">
                            <label for="lastNameInput" class="col-sm-2 col-form-label form-control-lg">${registration_lastName}</label>
                            <div class="col-sm-10">
                                <input form="update_user" type="text" class="form-control" id="lastNameInput" name="lastName" value="${requestScope.lastName}"/>
                            </div>
                        </div>
                        <div class="row mb-3">
                            <label for="emailInput" class="col-sm-2 col-form-label form-control-lg">${registration_email}</label>
                            <div class="col-sm-10">
                                <input form="update_user" type="text" class="form-control" id="emailInput" name="email" value="${requestScope.email}"/>
                            </div>
                        </div>
                        <div class="row mb-3">
                            <label for="cityInput" class="col-sm-2 col-form-label form-control-lg">${registration_city}</label>
                            <div class="col-sm-10">
                                <input form="update_user" type="text" class="form-control" id="cityInput" name="city" value="${requestScope.city}"/>
                            </div>
                        </div>
                        <div class="row mb-3">
                            <label for="passwordInput" class="col-sm-2 col-form-label form-control-lg">${registration_password}</label>
                            <div class="col-sm-10">
                                <input form="update_user" type="password" class="form-control" id="passwordInput" name="password" value=""/>
                                <span class="form-text">Enter 1-100 symbols.</span>
                            </div>
                        </div>
                        <div class="row mb-3">
                            <label for="confirmPasswordInput" class="col-sm-2 col-form-label form-control-lg">${registration_confirmPassword}</label>
                            <div class="col-sm-10">
                                <input form="update_user" type="confirmPassword" class="form-control" id="confirmPasswordInput" name="confirmPassword" value=""/>
                                <span class="form-text">Enter 1-100 symbols.</span>
                            </div>
                        </div>

                        <button form="update_user" type="submit" name="update_user">
                                ${work_update_user}
                        </button><br/>
                    </c:when>
                </c:choose>

            </div>
        </c:if>
        <c:if test="${sessionScope.userId eq null}">
            <a href="/controller?command=go_to_page&path=authorization">
                <h2>Please login or register</h2>
            </a>
        </c:if>

    </body>
</html>



