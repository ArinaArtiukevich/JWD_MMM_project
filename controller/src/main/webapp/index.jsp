<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false"%>

<html>
<head>
    <title>NAME</title>
    <link rel="stylesheet" href="resources/bootstrap/css/bootstrap.css"/>
    <link rel="stylesheet" href="resources/css/style.css"/>
</head>
<body>

<p class="page-header">Name</p>
<div align="center">
    <table>
        <tr>
            <th><a class="eight">Main</a></th>
            <th><a class="eight" href="jsp/services.jsp">Service</a></th>
            <th><a class="eight" href="jsp/authorization.jsp">Authorization</a></th>
        </tr>
    </table>
</div>

<div>
    <h1 style="color:red;"> ${sessionScope.firstName}</h1>
    <h1 style="color:red;"> ${sessionScope.login}</h1>
</div>

<div id="error">
    <h1 style="color:red;">${internalError}</h1>
</div>

</body>
</html>

