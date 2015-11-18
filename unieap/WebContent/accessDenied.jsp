<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="org.springframework.security.core.Authentication" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<html>
  <head>
    <title>Access Denied</title>
  </head>

<body>
<h1>Sorry, access is denied</h1>
<p>
<%= request.getAttribute("SPRING_SECURITY_403_EXCEPTION")%>
</p>
<p>
<%      
Enumeration<String> names = request.getAttributeNames();
while (names.hasMoreElements()){
    Object o= names.nextElement();
    System.out.println(o.toString());
};        

Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) { %>
        Authentication object as a String: <%= auth.toString() %><br /><br />
<%      } %>
</p>
</body>
</html>
