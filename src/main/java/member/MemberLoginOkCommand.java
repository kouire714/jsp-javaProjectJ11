package member;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.SecurityUtil;

public class MemberLoginOkCommand implements MemberInterface {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mid = request.getParameter("mid")==null ? "" : request.getParameter("mid");
		String pwd = request.getParameter("pwd")==null ? "" : request.getParameter("pwd");
		
		MemberDAO dao = new MemberDAO();
		
//		아이디 중복체크
		MemberVO vo = dao.getMemberMidCheck(mid);
		
//		아이디 존재여부 / 탈퇴신청 / 입력값과 조회값이 일치하는지 검사
		if(vo.getMid() == null || vo.getUserDel().equals("OK") || !vo.getMid().equals(mid)) {
			request.setAttribute("msg", "아이디를 확인하세요");
			request.setAttribute("url", "memberLogin.mem");
			return;
		}
		
//		비밀번호 암호화
		SecurityUtil security = new SecurityUtil();
		pwd = security.encryptSHA256(pwd);
		
//		비밀번호 입력값과 조회이 일치하는지 검사
		if(!vo.getPwd().equals(pwd)) {
			request.setAttribute("msg", "비밀번호를 확인하세요");
			request.setAttribute("url", "memberLogin.mem");
			return;
		}
		
//		로그인 성공시 처리할 내용(로그인 세션저장, 아이디기억 쿠키저장, 최종접속일 갱신, 
//		방문횟수(오늘방문횟수, 총방문횟수) 갱신, 방문 포인트 지급, 자동 등업처리(정회원))
		
//		현재날짜 포멧 설정
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String strToday = sdf.format(today);
		// System.out.println("strToday : " + strToday);
		
//		현재 날짜와 마지막 접속날짜가 같은 경우 방문카운트 증가
		if(strToday.equals(vo.getLastDate().substring(0,10))) {
			vo.setTodayCnt(vo.getTodayCnt()+1);
		}
//		오늘 처음 방문한 경우는 방문카운트를 1로 초기화
		else {
			vo.setTodayCnt(1);
		}
		
//		변경된 내용들을 DB에 저장(갱신))
		dao.setLoginUpdate(vo);
		
//		로그인(mid, nickName, level)세션 처리
		String strLevel = "";
		if(vo.getLevel() == 0) strLevel = "관리자";
		else if(vo.getLevel() == 1) strLevel = "회원";
		else if(vo.getLevel() == 2) strLevel = "운영자";
		
		HttpSession session = request.getSession();
		session.setAttribute("sMid", mid);
		session.setAttribute("sNickName", vo.getNickName());
		session.setAttribute("sLevel", vo.getLevel());
		session.setAttribute("strLevel", strLevel);
//		session.setAttribute("sPoint", vo.getPoint());
//		session.setAttribute("sLastDate", vo.getLastDate());
//		session.setAttribute("sTodayCnt", vo.getTodayCnt());
		
		
//		아이디기억하기(idSave) 쿠키저장
		String idSave = request.getParameter("idSave")==null ? "off" : "on";
		Cookie cookieMid = new Cookie("cMid", mid);
		cookieMid.setPath("/");
		if(idSave.equals("on")) {
			cookieMid.setMaxAge(60*60*24*7);
		}
		else {
			cookieMid.setMaxAge(0);
		}
		response.addCookie(cookieMid);
		
//		처리 완료후 메세지 출력후 회원 메인창으로 전송한다.
		request.setAttribute("msg", mid+"님 로그인 되었습니다.");
		request.setAttribute("url", "memberMain.mem");
	}

}