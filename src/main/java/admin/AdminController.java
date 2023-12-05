package admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import admin.complaint.BoardComplaintInputCommand;
import admin.complaint.BoardComplaintListCommand;
import admin.member.AdminMemberInforCommand;
import admin.member.MemberLevelChangeCommand;
//	import admin.review.ReviewInputCommand;
import member.MemberListCommand;

@SuppressWarnings("serial")
@WebServlet("*.ad")
public class AdminController extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AdminInterface command = null;
		String viewPage = "/WEB-INF/admin";
		
		String com = request.getRequestURI();
		com = com.substring(com.lastIndexOf("/"),com.lastIndexOf("."));
		
		HttpSession session = request.getSession();
		int level = session.getAttribute("sLevel")==null ? 999 : (int) session.getAttribute("sLevel");
		
//		***
		if(com.equals("/main")) {
			command = new MainCommand();
			command.execute(request, response);
			viewPage = "/WEB-INF/main/main.jsp";
		}
		
//		else if(com.equals("/reviewInput")) {
//			command = new ReviewInputCommand();
//			command.execute(request, response);
//			return;
//		}
		
//		신고 접수
		else if(com.equals("/boardComplaintInput")) {
			command = new BoardComplaintInputCommand();
			command.execute(request, response);
			return;
		}
		
//		관리자 외 접속 방지
		else if(level > 0) {
			request.getRequestDispatcher("/").forward(request, response);
		}
		
//		관리자 메인페이지
		else if(com.equals("/adminMain")) {
			viewPage += "/adminMain.jsp";
		}
		
//		관리자 메인페이지(왼쪽)
		else if(com.equals("/adminLeft")) {
			viewPage += "/adminLeft.jsp";
		}
		
//		관리자 메인페이지(중앙)
		else if(com.equals("/adminContent")) {
			command = new AdminContentCommand();
			command.execute(request, response);
			viewPage += "/adminContent.jsp";
		}
		
//		회원리스트
		else if(com.equals("/adminMemberList")) {
			command = new MemberListCommand();
			command.execute(request, response);
			viewPage += "/member/adminMemberList.jsp";
		}
		
//		회원 레벨 변경
		else if(com.equals("/adminMemberLevelChange")) {
			command = new MemberLevelChangeCommand();
			command.execute(request, response);
			return;
		}
		
//		회원 정보
		else if(com.equals("/adminMemberInfor")) {
			command = new AdminMemberInforCommand();
			command.execute(request, response);
			viewPage += "/member/adminMemberInfor.jsp";
		}
		
//		신고글 리스트
		else if(com.equals("/boardComplaintList")) {
			command = new BoardComplaintListCommand();
			command.execute(request, response);
			viewPage += "/complaint/boardComplaintList.jsp";
		}
		
		
		request.getRequestDispatcher(viewPage).forward(request, response);
	}
}