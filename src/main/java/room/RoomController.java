package room;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.MemberIdCheckCommand;

@WebServlet("*.room")
public class RoomController extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RoomInterface command = null;
		String viewPage = "/WEB-INF/room/";
		
		String com = request.getRequestURI();
		com = com.substring(com.lastIndexOf("/"), com.lastIndexOf("."));
		
		if(com.equals("/roomReservation")) {
			command = new RoomReservationCommand();
			command.execute(request, response);
			viewPage += "/roomReservation.jsp";
		}

		request.getRequestDispatcher(viewPage).forward(request, response);
	}
}