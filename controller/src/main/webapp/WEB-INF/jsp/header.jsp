<%@page language="java" contentType="text\html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
    <head>
        <link rel="stylesheet" href="../../resources/bootstrap/css/bootstrap.css"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <fmt:setLocale value="${sessionScope.language}"/>
        <fmt:setBundle basename="localization.local" var="loc"/>
        <fmt:message bundle="${loc}" key="local.locbutton.name.en" var="en_btn"/>
        <fmt:message bundle="${loc}" key="local.locbutton.name.ru" var="ru_btn"/>
        <fmt:message bundle="${loc}" key="button.logout" var="button_logout"/>
        <fmt:message bundle="${loc}" key="button.main" var="button_main"/>
        <fmt:message bundle="${loc}" key="button.order" var="button_service"/>
        <fmt:message bundle="${loc}" key="button.authorization" var="button_authorization"/>
        <fmt:message bundle="${loc}" key="button.user.account" var="button_user_account"/>
    </head>
    <body>

        <div class="container">
            <div class="row">
                <div class="col-10">
                    <p class="h2" style="margin: 10px 0;">MULTITOOL</p>
                </div>
                <div class="col-2">
                    <form class="mb-0" name="change_language" method="POST"
                          action="${pageContext.request.contextPath}/controller">
                        <input type="hidden" name="command" value="change_language">
                            <input type="hidden" name="parent_page" value="${param.page_path}">

                                <div>
                                    <button type="submit" class="btn btn-link btn-sm" name="change_language" value="en">
                                        <c:out value="${en_btn}"/>
                                    </button>

                                    <button type="submit" class="btn btn-link btn-sm" name="change_language" value="ru">
                                        <c:out value="${ru_btn}"/>
                                    </button>
                                </div>
                            </input>
                        </input>
                    </form>
                    <div style="display:inline-block">
                        <c:if test="${sessionScope.userId > 0}">
                            <a class="btn btn-light btn-sm" href="/controller?command=logout">
                                    ${button_logout}
                            </a>
                            id:${sessionScope.userId}
                            login:${sessionScope.login}
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
        <div align="container" style="padding-bottom: 50px;">
            <nav class="navbar navbar-expand-lg navbar-light" style="background-color:a0e8e3;">
                <div class="container-fluid">
                    <div class="collapse navbar-collapse justify-content-center" id="navbarNav">
                        <ul class="navbar-nav">
                            <li class="nav-item">
                                <a class="nav-link" href="/controller?command=go_to_page&path=index"><c:out
                                        value="${button_main}"/></a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="/controller?command=go_to_page&path=services"><c:out
                                        value="${button_service}"/></a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="/controller?command=go_to_page&path=authorization"><c:out
                                        value="${button_authorization}"/></a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="/controller?command=go_to_page&path=work"><c:out
                                        value="${button_user_account}"/></a>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
        </div>
    </body>
</html>