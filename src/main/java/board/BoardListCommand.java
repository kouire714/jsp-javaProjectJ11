package board;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BoardListCommand implements BoardInterface {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BoardDAO dao = new BoardDAO();
		
		// 페이징처리
		int pag = request.getParameter("pag")==null ? 1 : Integer.parseInt(request.getParameter("pag")); // 현재 페이지
		int pageSize = request.getParameter("pageSize")==null ? 5 : Integer.parseInt(request.getParameter("pageSize")); // 페이지에 들어갈 게시판의 양
		int totRecCnt = dao.getTotRecCnt("",""); // 전체 게시판 글의 수
		int totPage = (totRecCnt % pageSize)==0 ? (totRecCnt / pageSize) : (totRecCnt / pageSize) + 1 ; // 페이지의 개수
		int startIndexNo = (pag - 1) * pageSize; // 페이지에서 첫번째 글의 인덱스 넘버
		int curScrStartNo = totRecCnt - startIndexNo; // 페이지에서 첫번째 글의 인덱스 넘버의 역순
		
		int blockSize = 3; // 페이지(블록) 군의 사이즈
		int curBlock = (pag - 1) / blockSize; // 현재 블록(블록은 0부터 시작)
		int lastBlock = (totPage - 1) / blockSize; // 마지막 블록
		
//		게시판 리스트 처리(전체 조회)
		ArrayList<BoardVO> vos = dao.getBoardList(startIndexNo, pageSize,"","");
		
		request.setAttribute("vos", vos);
		request.setAttribute("pag", pag);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("totPage", totPage);
		request.setAttribute("curScrStartNo", curScrStartNo);
		request.setAttribute("blockSize", blockSize);
		request.setAttribute("curBlock", curBlock);
		request.setAttribute("lastBlock", lastBlock);
	}

}