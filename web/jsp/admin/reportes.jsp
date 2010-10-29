<%-- 
    Document   : reportes
    Created on : 20/10/2010, 12:02:37 PM
    Author     : Ironbit
--%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="/bio-checador/ext-3.2.1/resources/css/ext-all.css" />
        <link rel="stylesheet" type="text/css" href="/bio-checador/ext-3.2.1/resources/css/xtheme-gray.css" />
        <link rel="stylesheet" type="text/css" href="/bio-checador/css/estilo-checador.css" />
        <script type="text/javascript" src="/bio-checador/ext-3.2.1/adapter/ext/ext-base.js"></script>
        <script type="text/javascript" src="/bio-checador/ext-3.2.1/ext-all.js"></script>
        <script type="text/javascript" src="/bio-checador/ext-3.2.1/GridPrinter.js"></script>
        <script type="text/javascript" src="/bio-checador/ext-3.2.1/Exporter-all.js"></script>
        <script type="text/javascript" src="/bio-checador/js/reportes.js"></script>
        <script type="text/javascript" src="/bio-checador/js/admin_nav.js"></script>
        <title>Bio-Checador | Administración</title>
    </head>
    <body>
         <%@include file="/jsp/userinfo.jsp" %>
         
              <div id="body-content">
         <div id="admin_nav" class="reportes"></div>
         <div id="panelReportes"></div>
              </div>
         
    </body>
</html>
