<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ page import="org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="/bio-checador/css/estilo-login.css" />

    <title>Bio-Checador Login</title>     
  </head>

  <body>
    <div id="stylized" class="myform">
      <form id="form" name="form" method="POST" action="j_spring_security_check">
      <h1>Ingreso al Sistema</h1>
      <p>Conocer | Bio-Checador</p>

      <label for="j_username">Usuario:</label>
      <input type="text" name="j_username" id="j_username" <c:if test="${not empty param.login_error}">value="<%= session.getAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_LAST_USERNAME_KEY) %>"</c:if> />

      <label for="j_password">Contraseña:</label>
      <input type="password" name="j_password" id="j_password" />

      <button type="submit">Ingresar</button>
      <button type="reset">Restablecer</button>
      </form>
    </div>

  </body>
</html>
