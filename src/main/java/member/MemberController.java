package member;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@SuppressWarnings("serial")
@WebServlet("*.mem")
public class MemberController extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MemberInterface command = null;
		String viewPage = "/WEB-INF/member";
		
		String com = request.getRequestURI();
		com = com.substring(com.lastIndexOf("/"),com.lastIndexOf("."));
		
//		로그인시 얻은 세션 레벨 불러오기
		HttpSession session = request.getSession();
		int level = session.getAttribute("sLevel")==null ? 999 : (int) session.getAttribute("sLevel");
		
//		회원가입 페이지
		if(com.equals("/memberJoin")) {
			viewPage += "/memberJoin.jsp";
		}
		
//		아이디 중복 체크
		else if(com.equals("/memberIdCheck")) {
			command = new MemberIdCheckCommand();
			command.execute(request, response);
			viewPage += "/memberIdCheck.jsp";
		}
		
//		닉네임 중복 체크
		else if(com.equals("/memberNickCheck")) {
			command = new MemberNickCheckCommand();
			command.execute(request, response);
			viewPage += "/memberNickCheck.jsp";
		}
		
//		회원가입 확인
		else if(com.equals("/memberJoinOk")) {
			command = new MemberJoinOkCommand();
			command.execute(request, response);
			viewPage = "/include/message.jsp";
		}
		
//		로그인 페이지
		else if(com.equals("/memberLogin")) {
			viewPage += "/memberLogin.jsp";
		}
		
//		로그인 확인
		else if(com.equals("/memberLoginOk")) {
			command = new MemberLoginOkCommand();
			command.execute(request, response);
			viewPage = "/include/message.jsp";
		}
		
//		아이디 찾기
		else if(com.equals("/memberEmailSearch")) {
			command = new MemberEmailSearchCommand();
			command.execute(request, response);
			return;
		}
		
//		비밀번호 찾기
		else if(com.equals("/memberPasswordSearch")) {
			command = new MemberPasswordSearchCommand();
			command.execute(request, response);
			return;
		}
		
//		비회원인경우(세션이 끊어진경우) 홈으로 보낸다.
		else if(level > 4) {
			request.getRequestDispatcher("/").forward(request, response);
		}
		
//		로그아웃 확인
		else if(com.equals("/memberLogout")) {
			command = new MemberLogoutCommand();
			command.execute(request, response);
			viewPage = "/include/message.jsp";
		}
		
//		회원 정보 변경
		else if(com.equals("/memberPwdCheck")) {
			viewPage += "/memberPwdCheck.jsp";
		}
		
//		회원정보 수정(비밀번호 확인)
		else if(com.equals("/memberPwdCheckOk")) {
			command = new MemberPwdCheckOkCommand();
			command.execute(request, response);
			viewPage = "/include/message.jsp";
		}
		
//		회원정보 수정
		else if(com.equals("/memberUpdateForm")) {
			command = new MemberUpdateFormCommand();
			command.execute(request, response);
			viewPage += "/memberUpdateForm.jsp";
		}
		
//		회원정보 수정 확인
		else if(com.equals("/memberUpdateOk")) {
			command = new MemberUpdateOkCommand();
			command.execute(request, response);
			viewPage = "/include/message.jsp";
		}
		
//		비밀번호 변경(비밀번호 확인)
		else if(com.equals("/memberPwdCheckAjax")) {
			command = new MemberPwdCheckAjaxCommand();
			command.execute(request, response);
			return;
		}
		
//		비밀번호 변경
		else if(com.equals("/memberPwdChangeOk")) {
			command = new MemberPwdChangeOkCommand();
			command.execute(request, response);
			return;
		}
		
//		회원 탈퇴 신청
		else if(com.equals("/memberDelelteCheck")) {
			command = new MemberDelelteCheckCommand();
			command.execute(request, response);
			return;
		}
		
		else if(com.equals("/memberDelelteOk")) {
			command = new MemberDelelteOkCommand();
			command.execute(request, response);
			return;
		}
		
		request.getRequestDispatcher(viewPage).forward(request, response);
	}
}