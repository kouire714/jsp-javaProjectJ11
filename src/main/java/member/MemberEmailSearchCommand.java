package member;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MemberEmailSearchCommand implements MemberInterface {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email")==null ? "" : request.getParameter("email");
		
		MemberDAO dao = new MemberDAO();
		
//		이메일로 아이디 검색하기
		ArrayList<String> vos = dao.getEmailSearch(email);
		
//		검색한 아이디를 문자열로 변환
		if(vos.size() == 0) {
			response.getWriter().write("검색된 이메일이 없습니다.");
		}
		else {
			String str = "";
			for(String vo : vos) {
				str += vo + "/";
			}
			str = str.substring(0,str.lastIndexOf("/"));
			
			response.getWriter().write(str);
		}
	}

}