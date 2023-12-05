package introduce;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet("*.intro")
public class IntroduceController extends HttpServlet  {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String viewPage = "/WEB-INF/introduce";
		
		String com = request.getRequestURI();
		com = com.substring(com.lastIndexOf("/"), com.lastIndexOf("."));
		
//		펜션 소개 페이지
		if(com.equals("/introduce")) {
			viewPage += "/introduce.jsp";
		}
		
		request.getRequestDispatcher(viewPage).forward(request, response);
	}
}
