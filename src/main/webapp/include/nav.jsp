<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />

<script>
  function memberDelcheck() {
	  let ans = confirm("회원 탈퇴 하시겠습니까?");
	  if(ans) {
		  let ans2 = confirm("탈퇴후 같은 아이디로는 1개월간 재가입하실수 없습니다.\n그래도 탈퇴 하시겠습니까?");
		  if(!ans2) return false; 
	  }
	  else return false;
	  
	  // 회원 탈퇴(ajax처리)
	  $.ajax({
		  url  : "memberDelelteCheck.mem",
		  type : "post",
		  success:function(res) {
			  if(res != '1') alert("회원 탈퇴 실패~~");
			  else location.href = 'memberLogout.mem';
		  },
		  error : function() {
			  alert("전송오류");
		  }
	  });
  }
</script>

<%
	/* 로그인시 얻은 세션 레벨 불러오기 */
	int level = session.getAttribute("sLevel")==null ? 99 : (int) session.getAttribute("sLevel");
	pageContext.setAttribute("level", level);  
%>

<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
	<!-- <a class="navbar-brand" href="http://192.168.50.55:9090/javaProject">Home</a> -->
	<a class="navbar-brand" href="http://localhost:9090/javaProjectJ11">Home</a>
	<div class="collapse navbar-collapse" id="collapsibleNavbar">
		<ul class="navbar-nav">
			
			<!-- 펜션소개 -->
			<li class="nav-item">
				<a class="nav-link" href="introduce.intro">introduce</a>
			</li>
			
			<!-- 펜션소개2 -->
			<!-- 
			<li class="nav-item">
	        	<a class="nav-link" href="pdsList.pds">Pds</a>
	      	</li>    
			 -->
			 
			<!-- 펜션 예약 -->
			<li class="nav-item">
				<c:if test="${level <= 4}"><a class="nav-link" href="roomReservation.room">Room</a></c:if>
			</li>
			
			<!-- 리뷰 게시판 -->
			<li class="nav-item">
	        	<a class="nav-link" href="boardList.bo">review</a>
	        </li>
	        
	        <!-- 로그인 / 로그아웃 페이지 -->
			<li class="nav-item">
				<c:if test="${level > 4}"><a class="nav-link" href="memberLogin.mem">logIn</a></c:if>
				<c:if test="${level <= 4}"><a class="nav-link" href="memberLogout.mem">logOut</a></c:if>
			</li>
			
			<!-- 회원 정보 -->
			<c:if test="${level <= 4}">
			<li class="nav-item ml-2 mr-2">
				<div class="dropdown">
					<button type="button" class="btn btn-secondary dropdown-toggle" data-toggle="dropdown">MyPage</button>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="memberMain.mem">myInfo</a>
						<a class="dropdown-item" href="memberPwdCheck.mem">infoEdit</a>
						<a class="dropdown-item" href="javascript:memberDelcheck()">leave</a>
						<c:if test="${sLevel == 0}"><a class="dropdown-item" href="adminMain.ad">admin</a></c:if>
					</div>
				</div>
			</li>
			</c:if>
		</ul>
	</div>  
</nav>