<%--
    Document   : main
    Created on : 5/10/2010, 02:00:57 PM
    Author     : Ironbit
--%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="/checador/ext-3.2.1/resources/css/ext-all.css" />
        <link rel="stylesheet" type="text/css" href="/checador/ext-3.2.1/resources/css/xtheme-gray.css" />
        <link rel="stylesheet" type="text/css" href="/checador/css/estilo-checador.css" />
        <script type="text/javascript" src="/checador/ext-3.2.1/adapter/ext/ext-base.js"></script>
        <script type="text/javascript" src="/checador/ext-3.2.1/ext-all.js"></script>
        <script type="text/javascript" src="/checador/ext-3.2.1/remoteComponent.js"></script>
        <script type="text/javascript" src="/checador/js/main.js"></script>
        <script type="text/javascript" src="/checador/js/admin_nav.js"></script>
        <title>Checador</title>
    </head>
    <body>
         <%@include file="/jsp/userinfo.jsp" %>

         
         <div id="body-content">
              <!-- Solamente los usuarios de tipo admin verán la barra de navegación -->
              <sec:authorize url="/admin">
                   <div id="admin_nav" class="codigo"></div>
              </sec:authorize>
             <div id="panelCodigo"></div>
             <div id="panel"></div>
         </div>
        
    </body>
</html>
