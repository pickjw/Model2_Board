package board.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;

import board.dto.BoardCommentDTO;
import board.dto.BoardDTO;
import sqlmap.MybatisManager;

public class BoardDAO {

	// 게시물 목록 리턴
	public List<BoardDTO> getList(int start, int end) {
		/*
		 * SqlSession session = MybatisManager.getInstance().openSession();
		 * List<BoardDTO> list = session.selectList("board.list"); session.close();
		 * return list;
		 */
		List<BoardDTO> list = null;
		SqlSession session = null;
		try {
			session = MybatisManager.getInstance().openSession();
			//int start, int end 개의 값을 넣어 줘야 하기에 HashMap 사용
			Map<String,Object> map = new HashMap<>();
			map.put("start", start);
			map.put("end", end);
			// namespace.id
			System.out.println("dao => dto 게시물 목록 리턴:" + map);// 연결확인
			list = session.selectList("board.list" , map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) session.close();
		}
		return list;
	}

	// insert.do
	// 게시물 저장
	public void getInsert(BoardDTO bdto) {
		SqlSession session = null;
		try {
			session = MybatisManager.getInstance().openSession();
			// namespace.id
			System.out.println("dao => dto 게시물 저장:" + bdto);// 연결확인
			session.insert("board.insert", bdto);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) session.close();
		}
	}

	// 첨부파일 이름 찾기
	public String getFileName(int num) {
		String result = "";
		SqlSession session = null;
		try {
			session = MybatisManager.getInstance().openSession();
			// namespace.id
			System.out.println("dao => dto 첨부파일 이름:" + num);// 연결확인
			result = session.selectOne("board.getFileName", num);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) session.close();
		}
		return result;
	}
	
	//다운로드 회수 증가 처리
	public void getPlusDown (int num) {
		SqlSession session = null;
		try {
			session = MybatisManager.getInstance().openSession();
			// namespace.id
		    System.out.println("dao => dto 다운로드 회수 증가:" + num);// 연결확인
			session.update("board.plusDown", num);
			session.commit();
			
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			if(session != null) session.close();
		}
		
		
	}
	//클릭시 상세화면 "view.do
	//줄바꿈 처리 메서드
	public BoardDTO getViewReplace (int num) {
		BoardDTO bdto = null;
		SqlSession session = null;
		try {
			session = MybatisManager.getInstance().openSession();
			// namespace.id
		    System.out.println("dao => dto 상세화면:" + num);// 연결확인
			bdto= session.selectOne("board.view", num);
			//줄바꿈처리
			String content = bdto.getContent();
			content = content.replace("\n", "<br>");
			bdto.setContent(content);
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			if (session != null) session.close();
		}
		return bdto;
		
	}
	
	//수정 삭제 화면 reply.do에서도 호출
	public BoardDTO getView (int num) {
		BoardDTO bdto = null;
		SqlSession session = null;
		try {
			session = MybatisManager.getInstance().openSession();
			// namespace.id
		    System.out.println("dao => dto 상세화면:" + num);// 연결확인
			bdto= session.selectOne("board.view", num);
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			if (session != null) session.close();
		}
		return bdto;
		
	}
	
	//조회수 증가 처리
	public void getPlusReadCount (int num , HttpSession count_session) {
		SqlSession session = null;
		try {
			long read_time=0; //ex) 1번글 열람 14:10 열람 => update_time_1 , 14:10
			if (count_session.getAttribute("read_time_"+num) !=null) {
				read_time = (long) count_session.getAttribute("read_time_"+num);
			}
			long current_time=System.currentTimeMillis();
			session = MybatisManager.getInstance().openSession();
			if(current_time-read_time > 5*1000) { //5초
				// namespace.id
			    System.out.println("dao => dto 조회수 증가:" + num);// 연결확인
				session.update("board.plusReadCount", num);
				session.commit();
				//최근 열람시각 업데이트
				count_session.setAttribute("read_time_"+ num, current_time);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			if( session != null) session.close();
		}
		
		
	}
	
	//commentList.do 댓글 게시물 목록 리턴
	public List<BoardCommentDTO> getCommentList(int num) {
		List<BoardCommentDTO> list = null;
		SqlSession session = null;
		try {
			session = MybatisManager.getInstance().openSession();
			// namespace.id
			System.out.println("dao => dto 댓글 게시물 목록 리턴:" + list);// 연결확인
			list = session.selectList("board.commentList", num);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(session != null) session.close();
		}
		return list;
	}
	
	//comment_add.do 댓글추가
	public void getCommentAdd(BoardCommentDTO bdto) {
		SqlSession session = null;
		try {
			session = MybatisManager.getInstance().openSession();
			// namespace.id
			System.out.println("dao => dto 댓글추가 :" + bdto);// 연결확인
			session.insert("board.commentAdd", bdto);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (session != null) session.close();
		}
	}
	
	//답글 순서 조정
	public void getUpdateStep(int ref, int re_step) {
		SqlSession session = null;
		try {
			session = MybatisManager.getInstance().openSession();
			BoardDTO bdto = new BoardDTO();
			bdto.setRef(ref);
			bdto.setRe_step(re_step);
			// namespace.id
			System.out.println("dao => dto 답글 순서 조정:" + bdto);// 연결확인
			session.update("board.updateStep", bdto);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if( session != null) session.close();
		}
	}
	
	//답글쓰기
	public void getReply (BoardDTO bdto) {
		SqlSession session = null;
		try {
			session = MybatisManager.getInstance().openSession();
			// namespace.id
			System.out.println("dao => dto 답글쓰기:" + bdto);// 연결확인
			session.insert("board.reply", bdto);
			session.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(session != null) session.close();
		}
	}
	
	//pass_check.do 비밀번호 체크 
	public String getPassCheck (int num, String passwd) {
		
		String result = null;
		SqlSession session = null;
		try {
			session = MybatisManager.getInstance().openSession();
			Map<String,Object> map = new HashMap<>();
			map.put("num", num);
			map.put("passwd", passwd);
			// namespace.id
			System.out.println("dao => dto 비밀번호 체크:" + map);// 연결확인
			result = session.selectOne("board.pass_check", map);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(session != null) session.close();
		}
		return result;
	}
	
	
	//update.do -첨부파일 수정
	public void getUpdate(BoardDTO bdto) {
		SqlSession session = null;
		try {
			session = MybatisManager.getInstance().openSession();
			// namespace.id
			System.out.println("dao => dto 첨부파일 수정:" + bdto);// 연결확인
			session.update("board.update", bdto);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ( session != null) session.close();
		}
	}
	
	//delete -삭제
	public void getDelete (int num) {
		SqlSession session = null;
		try {
			session = MybatisManager.getInstance().openSession();
			// namespace.id
			System.out.println("dao => dto 삭제:" + num);// 연결확인
			session.update("board.delete", num);
			session.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(session != null) session.close();
		}
		
	}
	
	//search.do 검색 기능
	public List<BoardDTO> getSearchList (String search_option, String keyword) {
		List<BoardDTO> list = null;
		//SqlSession session = null;
		//try ~ with 구문 사용 try(리소스) : java 1.7부터 사용가능 =>장점 finally 사용안해도됨 자동 리소스 정리
		try( SqlSession session = MybatisManager.getInstance().openSession()) {
			//session = MybatisManager.getInstance().openSession();
			Map<String,String> map = new HashMap<>();
			map.put("search_option", search_option);
			map.put("keyword", "%"+keyword+"%");
			// namespace.id
			System.out.println("dao => dto 검색 기능:" + map);// 연결확인
			list = session.selectList("board.searchList",map);
			
		} catch (Exception e) {
			e.printStackTrace();
		} /*finally {
			if (session != null) session.close();
		}*/
		return list;
		
	}
	
	//페이지 나누기 레코드 갯수 계산
	public int getCount () {
		int result = 0;
		SqlSession session = null;
		try {
			session = MybatisManager.getInstance().openSession();
			// namespace.id
			System.out.println("dao => dto 레코드 갯수 계산:" + result);// 연결확인
			result = session.selectOne("board.count");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(session != null) session.close();
		}
		return result;
	}
	
	
	
}
