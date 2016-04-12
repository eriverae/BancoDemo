<%-- 
    Document   : error
    Created on : 02-may-2014, 20:17:42
    Author     : jmartinez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error Page</title>
    </head>
    <body>
        <h1>Error técnico en el sistema</h1>
        <p><%= request.getAttribute("errorMsg") %></p>
    </body>
</html>
