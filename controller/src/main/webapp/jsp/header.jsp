<%@page language="java" contentType="text\html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
    <head>
        <title>NAME</title>
        <link rel="stylesheet" href="../resources/bootstrap/css/bootstrap.css"/>
        <link rel="stylesheet" href="../resources/css/style.css"/>

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

        <p class="page-header" >Name</p>
        <div align="center">
            <table>
                <tr>
                    <th><a href="../index.jsp" class="eight"> <c:out value="${button_main}"/></a></th>
                    <th><a href="../jsp/services.jsp" class="eight"> <c:out value="${button_service}"/></a></th>
                    <th><a href="../jsp/authorization.jsp" class="eight"> <c:out value="${button_authorization}"/></a></th>
                    <th><a href="../jsp/work.jsp" class="eight"> <c:out value="${button_user_account}"/></a></th>
                </tr>
            </table>
        </div>

        <form name="change_language" method="POST"
              action="${pageContext.request.contextPath}/controller">
            <input type="hidden" name="command" value="change_language">
            <input type="hidden" name="parent_page" value="${param.page_path}">

            <div>
                <button type="submit" class="linkgb" name="change_language" value="en">
                    <c:out value="${en_btn}"/>
                </button>

                <button type="submit" class="linkrus" name="change_language" value="ru">
                    <c:out value="${ru_btn}"/>
                </button>
            </div>
            </input>
            </input>
        </form>

        <div>
            <c:choose>
                <c:when test="${sessionScope.userRole eq 'client'}">
                    <a class="btn btn-light" href="/controller?command=logout">
                            ${button_logout}
                    </a>
                </c:when>
                <c:when test="${sessionScope.userRole eq 'worker'}">
                    <a class="btn btn-light" href="/controller?command=logout">
                            ${button_logout}
                    </a>
                </c:when>
            </c:choose>
        </div>

    </body>
</html>