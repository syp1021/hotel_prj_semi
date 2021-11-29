<%@page import="kr.co.sist.admin.room.RoomModify"%>
<%@page import="kr.co.sist.admin.reservation.ReserveModify"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" info="예약 건 삭제"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Insert title here</title>

<!-- jQuery CDN -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>

</head>
<body>

<c:catch var="e">
<%
//부적절한 페이지 이동시 redirect
if (request.getParameter("statusRNo") == null) {
	response.sendRedirect("admin_room_main.jsp");
	return;
}//end if

//파라미터 받고 삭제 method 호출
String roomNum = request.getParameter("statusRNo");
String rStatus = request.getParameter("rStatus");
	
RoomModify rm = new RoomModify();
int cnt = rm.UpdateRoomStatus(roomNum, rStatus);
//1 이라면 객실 상태 변경 성공!
if(cnt == 1){
%>
<script type="text/javascript">
	alert("객실 상태 변경이 정상적으로 수행되었습니다.");
	location.href="http://211.63.89.141/admin/admin_room/admin_room_main.jsp";
</script>
<%} else {%>
<script type="text/javascript">
	alert("죄송합니다. 잠시 후 다시 시도해주십시오.");
	location.href="http://211.63.89.141/admin/admin_room/admin_room_main.jsp";
</script>
<%}%>
</c:catch>

<c:if test="${not empty e}">
	<strong>죄송합니다. 삭제 작업 중 문제가 발생했습니다.</strong><br/>
	<strong>잠시 후 다시 시도해주세요.</strong><br/>
	<a href="http://211.63.89.141/admin/admin_room/admin_room_main.jsp">뒤로 가기</a>
</c:if>
</body>
</html>