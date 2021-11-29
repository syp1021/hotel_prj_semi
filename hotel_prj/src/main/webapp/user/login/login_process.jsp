<%@page import="org.springframework.dao.DataAccessException"%>
<%@page import="kr.co.sist.util.cipher.DataDecrypt"%>
<%@page import="kr.co.sist.user.login.MemberDAO"%>
<%@page import="kr.co.sist.util.cipher.DataEncrypt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
     <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../../favicon.ico">

    <title>Hotel_Ritz_Seoul</title>
    <!-- 메인 CSS -->
	<link rel="stylesheet" type="text/css"
	href="http://211.63.89.141/main/main.css">
    
	<style type = "text/css">
	/* 		div {margin: 0px auto; width: 1130px;} */

	</style>

    <!-- Bootstrap core CSS -->
	<!-- jQuery CDN -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
	

	<!-- Bootstrap CDN -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
	
    <script src="http://211.63.89.141/common/bootstrap/holder.js"></script>

    <script src="http://211.63.89.141/common/bootstrap/ie-emulation-modes-warning.js"></script>

    <link href="http://211.63.89.141/common/bootstrap/carousel.css" rel="stylesheet">
  </head>

<!-- NAVBAR
================================================== -->
  <body>
 <jsp:useBean id="loginVO" class="kr.co.sist.user.login.memberVO" scope="page"/>
<jsp:setProperty property="*" name="loginVO"/><!--  입력정보-->
<%
request.setCharacterEncoding("UTF-8");
String id = request.getParameter("id");
String pass = request.getParameter("pass");

//DBMS비밀번호 컬럼은 SHA 암호화된 값이므로 입력된 Plain Text를 
//cipher Text로 변환하여 비교하는 코드를 수행해야한다.
loginVO.setPass(DataEncrypt.messageDigest("SHA-512", loginVO.getPass()));

//로그인 수행
MemberDAO mDAO=new MemberDAO();
boolean flag=false;
try{
String kname=mDAO.selectLogin(loginVO);

//이름을 복호화한다.
DataDecrypt dd=new DataDecrypt("AbcdEfgHiJkLmnOpQ");
kname=dd.decryption(kname);

//로그인 정보를 세션에 할당 
//=>비연결성인 웹에서 로그인 정보를 모든 페이지에서 사용하기 위해
session.setAttribute("kname", kname);
session.setAttribute("pass", pass);
session.setAttribute("id", id);
flag=true;
}catch(DataAccessException dae){
}
pageContext.setAttribute("loginFlag", flag);
%>
		 <jsp:include page="../../main/main_header_nav.jsp"/>
 <div class="wrapper" >
 
	<br/><br/><br/>
	<div class="container marketing">
  <br/><br/>
  <div style="width: 300px;text-align: center; margin:0px auto;">
  <br>
  <h2>로그인</h2>
  </div>
  <hr style="width: 500px"/>
  <div style="width: 300px;text-align: center;margin:0px auto;">
  

<script type="text/javascript">

</script>
<br/><br/>
<c:choose>
<c:when  test="${ loginFlag }"><h4>안녕하세요</h4><br/>
<h4><c:out value="${ param.id }"/>(으)로 로그인 하셨습니다.</h4><br/>
</c:when>
<c:otherwise>
아이디/비밀번호를 다시 확인해주세요
<br/><br/><br/>
<input type="button"  value="로그인" class="btn btn-default" onclick="location.href='login.jsp'">
</c:otherwise>
</c:choose>
</div>


</div>

  <br/><br/>
 <div style="width:150px; text-align: center;margin:0px auto;">
  <input type="button" class="btn btn-default" style="width: 100px;" value="홈으로" onclick="location.href='http://211.63.89.141/main/Hotel_Ritz_Seoul.jsp'">

  </div>
</div>
    <br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
      <!-- FOOTER -->
   <jsp:include page="../../main/main_footer.jsp"/>

    <!-- Bootstrap core JavaScript
    ================================================== -->
    
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="http://211.63.89.141/common/bootstrap/ie10-viewport-bug-workaround.js"></script>
  </body>
</html>