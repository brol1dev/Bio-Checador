<%-- 
    Document   : userinfo
    Created on : 29/10/2010, 12:11:38 PM
    Author     : Ironbit
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <div id="footer">
             <div id="footer-frame">
                  <div id="footer-content">
                       <p>
                            Usuario: <sec:authentication property="principal.username" /> | <a href="/bio-checador/j_spring_security_logout">Cerrar Sesión</a>
                       </p>
                  </div>
             </div>
        </div>
    </body>
</html>
