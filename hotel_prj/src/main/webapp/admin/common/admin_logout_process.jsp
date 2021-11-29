<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
session.invalidate();
response.sendRedirect("http://211.63.89.141/admin/common/admin_logout_view.jsp");
%>
