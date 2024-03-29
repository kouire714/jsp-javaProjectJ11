package member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import common.GetConn;

public class MemberDAO {
	//GetConn getConn = GetConn.getInstance();
	//private Connection conn = getConn.getConn();
	private Connection conn = GetConn.getConn();
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	private String sql = "";
	
	MemberVO vo = null;
	
	// pstmt 객체 반납
	public void pstmtClose() {
		if(pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {}
		}
	}
	
	// rs 객체 반납
	public void rsClose() {
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {}
			finally {
				pstmtClose();
			}
		}
	}

//	아이디 중복 체크
	public MemberVO getMemberMidCheck(String mid) {
		vo = new MemberVO();
		try {
			sql = "select * from member where mid = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mid);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				vo.setIdx(rs.getInt("idx"));
				vo.setMid(rs.getString("mid"));
				vo.setPwd(rs.getString("pwd"));
				vo.setNickName(rs.getString("nickName"));
				vo.setName(rs.getString("name"));
				vo.setGender(rs.getString("gender"));
				vo.setBirthday(rs.getString("birthday"));
				vo.setTel(rs.getString("tel"));
				vo.setAddress(rs.getString("address"));
				vo.setEmail(rs.getString("email"));
				vo.setUserDel(rs.getString("userDel"));
				vo.setLevel(rs.getInt("level"));
				vo.setVisitCnt(rs.getInt("visitCnt"));
				vo.setStartDate(rs.getString("startDate"));
				vo.setLastDate(rs.getString("lastDate"));
				vo.setTodayCnt(rs.getInt("todayCnt"));
			}
		} catch (SQLException e) {
			System.out.println("sql오류 : " + e.getMessage());
		} finally {
			rsClose();
		}
		return vo;
	}

//	닉네임 중복체크
	public MemberVO getMemberNickCheck(String nickName) {
		vo = new MemberVO();
		try {
			sql = "select * from member where nickName = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, nickName);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				vo.setIdx(rs.getInt("idx"));
				vo.setMid(rs.getString("mid"));
				vo.setPwd(rs.getString("pwd"));
				vo.setNickName(rs.getString("nickName"));
				vo.setName(rs.getString("name"));
				vo.setGender(rs.getString("gender"));
				vo.setBirthday(rs.getString("birthday"));
				vo.setTel(rs.getString("tel"));
				vo.setAddress(rs.getString("address"));
				vo.setEmail(rs.getString("email"));
				vo.setUserDel(rs.getString("userDel"));
				vo.setLevel(rs.getInt("level"));
				vo.setVisitCnt(rs.getInt("visitCnt"));
				vo.setStartDate(rs.getString("startDate"));
				vo.setLastDate(rs.getString("lastDate"));
				vo.setTodayCnt(rs.getInt("todayCnt"));
			}
		} catch (SQLException e) {
			System.out.println("sql오류 : " + e.getMessage());
		} finally {
			rsClose();
		}
		return vo;
	}

//	회원 가입
	public int setMemberJoinOk(MemberVO vo) {
		int res = 0;
		try {
			sql = "insert into member values (default,?,?,?,?,?,?,?,?,?,default,default,default,default,default,default)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getMid());
			pstmt.setString(2, vo.getPwd());
			pstmt.setString(3, vo.getNickName());
			pstmt.setString(4, vo.getName());
			pstmt.setString(5, vo.getGender());
			pstmt.setString(6, vo.getBirthday());
			pstmt.setString(7, vo.getTel());
			pstmt.setString(8, vo.getAddress());
			pstmt.setString(9, vo.getEmail());
			res = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("sql오류 : " + e.getMessage());
		} finally {
			pstmtClose();
		}
		return res;
	}

//	로그인 마지막 방문일, 오늘 방문횟수, 전체 방문횟수 갱신
	public void setLoginUpdate(MemberVO vo) {
		try {
			sql = "update member set lastDate=now(), todayCnt=?, visitCnt=visitCnt+1 where mid = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, vo.getTodayCnt());
			pstmt.setString(2, vo.getMid());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("sql구문 오류 : " + e.getMessage());
		} finally {
			pstmtClose();
		}
	}

//	이메일로 아이디 검색하기
	public ArrayList<String> getEmailSearch(String email) {
		ArrayList<String> vos = new ArrayList<String>();
		try {
			sql = "select mid from member where email = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				vos.add(rs.getString("mid"));
			}
		} catch (SQLException e) {
			System.out.println("sql구문 오류 : " + e.getMessage());
		} finally {
			rsClose();
		}
		return vos;
	}

//	아이디와 이메일로 비밀번호 검색하기
	public int getMemberMidEmailSearch(String mid, String email) {
		int res = 0;
		try {
			sql = "select idx from member where mid = ? and email = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mid);
			pstmt.setString(2, email);
			rs = pstmt.executeQuery();
			
			if(rs.next()) res = 1;
		} catch (SQLException e) {
			System.out.println("sql구문 오류 : " + e.getMessage());
		} finally {
			rsClose();
		}
		return res;
	}

//	비밀번호 변경
	public void setMemberPwdUpdate(String mid, String pwdUid) {
		try {
			sql = "update member set pwd = ? where mid = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, pwdUid);
			pstmt.setString(2, mid);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("sql구문 오류 : " + e.getMessage());
		} finally {
			pstmtClose();
		}
	}

//	회원 정보수정 확인
	public int setMemberUpdateOk(MemberVO vo) {
		int res = 0;
		try {
			sql = "update member set nickName=?, name=?, gender=?, birthday=?,"
					+ "tel=?, address=?, email=?, homePage=?, job=?, hobby=?,"
					+ "photo=?, content=?, userInfor=? where mid=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getNickName());
			pstmt.setString(2, vo.getName());
			pstmt.setString(3, vo.getGender());
			pstmt.setString(4, vo.getBirthday());
			pstmt.setString(5, vo.getTel());
			pstmt.setString(6, vo.getAddress());
			pstmt.setString(7, vo.getEmail());
			pstmt.setString(8, vo.getHomePage());
			pstmt.setString(9, vo.getJob());
			pstmt.setString(10, vo.getHobby());
			pstmt.setString(11, vo.getPhoto());
			pstmt.setString(12, vo.getContent());
			pstmt.setString(13, vo.getUserInfor());
			pstmt.setString(14, vo.getMid());
			res = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("sql구문 오류 : " + e.getMessage());
		} finally {
			pstmtClose();
		}
		return res;
	}

//	레벨 구분 회원 전체 리스트
	public ArrayList<MemberVO> getMemberList(int startIndexNo, int pageSize, int level) {
		ArrayList<MemberVO> vos = new ArrayList<MemberVO>();
		try {
			if(level != 99 && level > 4) {
				sql = "select *, timestampdiff(day, lastDate, now()) as deleteDiff from member order by idx desc limit ?,?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, startIndexNo);
				pstmt.setInt(2, pageSize);
			}
			else {
				sql = "select *, timestampdiff(day, lastDate, now()) as deleteDiff from member where level = ? order by idx desc limit ?,?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, level);
				pstmt.setInt(2, startIndexNo);
				pstmt.setInt(3, pageSize);
			}
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				vo = new MemberVO();
				vo.setIdx(rs.getInt("idx"));
				vo.setMid(rs.getString("mid"));
				vo.setPwd(rs.getString("pwd"));
				vo.setNickName(rs.getString("nickName"));
				vo.setName(rs.getString("name"));
				vo.setGender(rs.getString("gender"));
				vo.setBirthday(rs.getString("birthday"));
				vo.setTel(rs.getString("tel"));
				vo.setAddress(rs.getString("address"));
				vo.setEmail(rs.getString("email"));
				vo.setHomePage(rs.getString("homePage"));
				vo.setJob(rs.getString("job"));
				vo.setHobby(rs.getString("hobby"));
				vo.setPhoto(rs.getString("photo"));
				vo.setContent(rs.getString("content"));
				vo.setUserInfor(rs.getString("userInfor"));
				vo.setUserDel(rs.getString("userDel"));
				vo.setPoint(rs.getInt("point"));
				vo.setLevel(rs.getInt("level"));
				vo.setVisitCnt(rs.getInt("visitCnt"));
				vo.setStartDate(rs.getString("startDate"));
				vo.setLastDate(rs.getString("lastDate"));
				vo.setTodayCnt(rs.getInt("todayCnt"));
				
				vo.setDeleteDiff(rs.getInt("deleteDiff"));
				
				vos.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("sql구문 오류 : " + e.getMessage());
		} finally {
			rsClose();
		}
		return vos;
	}

//	회원 등급변경
	public int setMemberLevelChange(int idx, int level) {
		int res = 0;
		try {
			// sql = "update member set level = ? where idx = ?";
			sql = "update member set level = ?, userDel = 'NO' where idx = ?";	// DB설계에서 userDel필드를 추가해 두었기에 level=99가 들어올때는 관리자가 변경한 level로 지정하고 탈퇴처리를 해제한다.(다시 설계시는 userDel필드는 필요없다. level=99를 탈퇴신청회원으로 두었기에..)
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, level);
			pstmt.setInt(2, idx);
			res = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("sql구문 오류 : " + e.getMessage());
		} finally {
			pstmtClose();
		}
		return res;
	}

//	전체(각 레벨별) 회원 인원수 구하기
	public int getTotRecCnt(int level) {
		int totRecCnt = 0;
		try {
			if(level != 99 && level > 4) {
				sql = "select count(*) as cnt from member";
				pstmt = conn.prepareStatement(sql);
			}
			else {
				sql = "select count(*) as cnt from member where level = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, level);
			}
			rs = pstmt.executeQuery();
			rs.next();
			totRecCnt = rs.getInt("cnt");
		} catch (SQLException e) {
			System.out.println("sql구문 오류 : " + e.getMessage());
		} finally {
			rsClose();
		}
		return totRecCnt;
	}

//	회원 탈퇴 신청(userDel필드의 값을 NO -> Ok 로 변경처리, level을 99번으로 변경, 즉 userDel필드는 필요없어짐. 다음설계시는 뺄것..)
	public int setMemberDeleteCheck(String mid) {
		int res = 0;
		try {
			sql = "update member set userDel = 'OK', level = 99 where mid = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mid);
			res = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("sql구문 오류 : " + e.getMessage());
		} finally {
			pstmtClose();
		}
		return res;
	}

//	회원 정보 삭제
	public int setMemberDeleteOk(int idx) {
		int res = 0;
		try {
			sql = "delete from member where idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			res = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("sql구문 오류 : " + e.getMessage());
		} finally {
			pstmtClose();
		}
		return res;
	}

//	회원 idx로 검색처리
	public MemberVO getMemberIdxSearch(int idx) {
		vo = new MemberVO();
		try {
			sql = "select * from member where idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				vo.setIdx(rs.getInt("idx"));
				vo.setMid(rs.getString("mid"));
				vo.setPwd(rs.getString("pwd"));
				vo.setNickName(rs.getString("nickName"));
				vo.setName(rs.getString("name"));
				vo.setGender(rs.getString("gender"));
				vo.setBirthday(rs.getString("birthday"));
				vo.setTel(rs.getString("tel"));
				vo.setAddress(rs.getString("address"));
				vo.setEmail(rs.getString("email"));
				vo.setHomePage(rs.getString("homePage"));
				vo.setJob(rs.getString("job"));
				vo.setHobby(rs.getString("hobby"));
				vo.setPhoto(rs.getString("photo"));
				vo.setContent(rs.getString("content"));
				vo.setUserInfor(rs.getString("userInfor"));
				vo.setUserDel(rs.getString("userDel"));
				vo.setPoint(rs.getInt("point"));
				vo.setLevel(rs.getInt("level"));
				vo.setVisitCnt(rs.getInt("visitCnt"));
				vo.setStartDate(rs.getString("startDate"));
				vo.setLastDate(rs.getString("lastDate"));
				vo.setTodayCnt(rs.getInt("todayCnt"));
			}
		} catch (SQLException e) {
			System.out.println("sql오류 : " + e.getMessage());
		} finally {
			rsClose();
		}
		return vo;
	}
	
}