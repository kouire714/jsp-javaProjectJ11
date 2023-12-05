package board;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BoardContentCommand implements BoardInterface {

	//@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int idx = request.getParameter("idx")==null ? 0 : Integer.parseInt(request.getParameter("idx"));
		int pag = request.getParameter("pag")==null ? 1 : Integer.parseInt(request.getParameter("pag"));
		int pageSize = request.getParameter("pageSize")==null ? 5 : Integer.parseInt(request.getParameter("pageSize"));
		String flag = request.getParameter("flag")==null ? "" : request.getParameter("flag");
		String search = request.getParameter("search")==null ? "" : request.getParameter("search");
		String searchString = request.getParameter("searchString")==null ? "" : request.getParameter("searchString");
		
		BoardDAO dao = new BoardDAO();
		
//		게시글 조회수 1 증가시키기(중복 불허) f5x
//		***
		HttpSession session = request.getSession();
		ArrayList<String> boardContentIdx = (ArrayList) session.getAttribute("sBoardContentIdx");
		if(boardContentIdx == null) {
			boardContentIdx = new ArrayList<String>();
		}
		// 고유네임번호 방문한적없으면 증가후 객체에 저장 증가기준은 로그인했을때일회 로그아웃시 세션종료
		String imsiContextIdx = "board" + idx;
		if(!boardContentIdx.contains(imsiContextIdx)) {
			dao.setBoardReadNumPlus(idx);
			boardContentIdx.add(imsiContextIdx);
		}
		// 없으면 추가하고, 이미존재하면 그냥 덮어씌우는것..
		session.setAttribute("sBoardContentIdx", boardContentIdx);
		
//		게시물 1건 상세보기
		BoardVO vo = dao.getBoardContent(idx);
		
		request.setAttribute("vo", vo);
		request.setAttribute("pag", pag);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("flag", flag);
		request.setAttribute("search", search);
		request.setAttribute("searchString", searchString);
		
//		이전글 / 다음글 처리
		BoardVO preVo = dao.getPreNexSearch(idx, "preVo");
		BoardVO nextVo = dao.getPreNexSearch(idx, "nextVo");
		request.setAttribute("preVo", preVo);
		request.setAttribute("nextVo", nextVo);
		
//		게시글의 댓글 불러오기
		ArrayList<BoardReplyVO> replyVos = dao.getBoardReply(idx);
		request.setAttribute("replyVos", replyVos);
	}

}