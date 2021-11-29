<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" info = "Hotel Ritz Seoul"%>
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

    <!-- 메인 CSS -->
	<link rel="stylesheet" type="text/css"
	href="http://211.63.89.141/main/main.css">
     <title>Hotel_Ritz_Seoul</title>
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
    <script type="text/javascript">
  $(function() {
		$("#btn").click(function() {
			if($("#id").val()==""){
				alert("아이디를 입력해 주세요.");
				return;
		}//end if
	
			if($("#pass").val()==""){
				alert("비밀번호를 입력해 주세요.");
				return;
		}//end if
			
			
			$("#frm").submit();
		});//click	
		

  });
  </script>
  
  </head>

<!-- NAVBAR
================================================== -->
	<jsp:include page="../../main/main_header_nav.jsp"/>
  <body>
	
	
	<!-- Standard button -->
	
	
	
      	<br/><br/><br/><br/><br/>
    <div class="container">	
    
    <h2 style="text-align: center;">로그인</h2>
    <hr/>
    <br/><br/>
    <form action="login_process.jsp"  method="post" id="frm" name="frm">
    <table style="margin: 0px auto;">
    	<tr>
    		<td style="padding: 5px"><input type="text" placeholder="아이디를 입력하세요" name="id" id="id" class="form-control"> </td>
    		<td rowspan="2"><input type="button"  class="btn btn-default" style="width:90px; height:40px;" value="로그인"  id="btn" ></td>
    	</tr>
    	<tr>
    		<td style="padding: 5px"><input type="password" placeholder="비밀번호를 입력하세요"  name="pass" id="pass" class="form-control"> </td>
    	</tr>
    	<tr >
    	
    		<td><a href="http://211.63.89.141/user/login/signIn.jsp" style="color: #C0C0C0">&nbsp;&nbsp;회원가입&nbsp;&nbsp;</a>
    		<a href="http://211.63.89.141/user/login/find.jsp" style="color: #C0C0C0">아이디/비밀번호 찾기</a></td>
    	</tr>
    </table>
    </form>
    
    </div>
    
    
    <br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
      <!-- FOOTER -->
    <jsp:include page="../../main/main_footer.jsp"/>
    <!-- Bootstrap core JavaScript
    ================================================== -->
    
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="http://211.63.89.141/common/bootstrap/ie10-viewport-bug-workaround.js"></script>
    <script>

   		
    	
    </script>
  </body>
</html>
