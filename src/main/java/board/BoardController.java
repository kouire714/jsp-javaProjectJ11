package board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@SuppressWarnings("serial")
@WebServlet("*.bo")
public class BoardController extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BoardInterface command = null;
		String viewPage = "/WEB-INF/board/";
		
		String com = request.getRequestURI();
		com = com.substring(com.lastIndexOf("/"),com.lastIndexOf("."));
		
		HttpSession session = request.getSession();
		int level = session.getAttribute("sLevel")==null ? 999 : (int) session.getAttribute("sLevel");
		
//		리뷰 게시판 페이지
		if(com.equals("/boardList")) {
			command = new BoardListCommand();
			command.execute(request, response);
			viewPage += "/boardList.jsp";
		}
		
//		리뷰 게시판 글쓰기
		else if(com.equals("/boardInput")) {
			viewPage += "/boardInput.jsp";
		}
		
//		리뷰 게시판 글쓰기 확인
		else if(com.equals("/boardInputOk")) {
			command = new BoardInputOkCommand();
			command.execute(request, response);
			viewPage = "/include/message.jsp";
		}
		
//		리뷰 게시판 글 열람
		else if(com.equals("/boardContent")) {
			command = new BoardContentCommand();
			command.execute(request, response);
			viewPage += "/boardContent.jsp";
		}
		
//		좋아요 조회수 증가
		else if(com.equals("/boardGoodCheck")) {
			command = new BoardGoodCheckCommand();
			command.execute(request, response);
			return;
		}
		
		/* 아래 좋아요 +1/-1 은 중복되기에 통합처리했음
		else if(com.equals("/boardGoodCheckPlus")) {
			command = new BoardGoodCheckPlusCommand();
			command.execute(request, response);
			return;
		}
		else if(com.equals("/boardGoodCheckMinus")) {
			command = new BoardGoodCheckMinusCommand();
			command.execute(request, response);
			return;
		}
		*/
		
//		좋아요 조회수 증감
		else if(com.equals("/boardGoodCheckPlusMinus")) {
			command = new BoardGoodCheckPlusMinusCommand();
			command.execute(request, response);
			return;
		}
		
//		글 수정
		else if(com.equals("/boardUpdate")) {
			command = new BoardUpdateCommand();
			command.execute(request, response);
			viewPage += "/boardUpdate.jsp";
		}
		
//		글 수정 확인
		else if(com.equals("/boardUpdateOk")) {
			command = new BoardUpdateOkCommand();
			command.execute(request, response);
			viewPage = "/include/message.jsp";
		}
		
//		글 삭제
		else if(com.equals("/boardDelete")) {
			command = new BoardDeleteCommand();
			command.execute(request, response);
			viewPage = "/include/message.jsp";
		}
		
//		글 검색
		else if(com.equals("/boardSearch")) {
			command = new BoardSearchCommand();
			command.execute(request, response);
			viewPage += "/boardSearchList.jsp";
		}
		
//		댓글 작성확인
		else if(com.equals("/boardReplyInput")) {
			command = new BoardReplyInputCommand();
			command.execute(request, response);
			return;
		}
		
//		댓글 삭제하기
		else if(com.equals("/boardReplyDelete")) {
			command = new BoardReplyDeleteCommand();
			command.execute(request, response);
			return;
		}
		
		request.getRequestDispatcher(viewPage).forward(request, response);
	}
}