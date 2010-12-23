<%-- 
    Document   : reportes
    Created on : 20/10/2010, 12:02:37 PM
    Author     : Ironbit
--%>
<!-- Librería de spring para usar los tags de spring security -->
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
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
        <script type="text/javascript" src="/checador/ext-3.2.1/GridPrinter.js"></script>
        <script type="text/javascript" src="/checador/ext-3.2.1/Exporter-all.js"></script>
        <script type="text/javascript" src="/checador/js/reportes.js"></script>
        <script type="text/javascript" src="/checador/js/admin_nav.js"></script>
        <title>Checador | Administración</title>
    </head>
    <body>
         <%@include file="/jsp/userinfo.jsp" %>

         <!-- Body-content contiene todo el cuerpo de la página -->
         <div id="body-content">
              <!-- Barra de navegación para administradores -->
              <div id="admin_nav" class="reportes"></div>
              <!-- Panel que muestra lo relacionado a los reportes -->
              <div id="panelReportes"></div>
         </div>
         
    </body>
</html>
