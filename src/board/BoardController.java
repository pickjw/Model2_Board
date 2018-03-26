package board;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import board.dao.BoardDAO;
import board.dto.BoardCommentDTO;
import board.dto.BoardDTO;
import common.Constants;
import page.Pager;

/*
web.xml	사용시 어노테이션 기능 사용안함
@WebServlet("/BoardController")
*/ 
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		actionDo(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		actionDo(request, response);
	}
	protected void actionDo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	    request.setCharacterEncoding("UTF-8");//한글처리
	  /*getRequestURI() : 주소 값이 컨택스패스부터 나옴(http://loaclhost(포트번호) 생략되어서 나옴) ,String
		getRequestURL() : 전체 주소값 나옴, StringBuffer*/
		String url = request.getRequestURL().toString();//사용자가 요청한 주소 uri 및  DAO생성-클라이언트에서 요청한 주소
		String contextPath = request.getContextPath();//컨텍스트 패스 (웹프로젝트의 식별자)
		BoardDAO bdao = new BoardDAO();//dao인스턴스 생성
		
		if(url.indexOf("list.do") != -1) {
			System.out.println("list.do 호출...ok!");
			//레코드 갯수 계산 
			int count = bdao.getCount();
			//페이지 나누기를 위한 처리
			int curPage = 1;
			if(request.getParameter("curPage") != null) {
				curPage = Integer.parseInt(request.getParameter("curPage"));
			}
			Pager pager = new Pager(count, curPage);
			int start = pager.getPageBegin();
			int end = pager.getPageEnd();
			
			List<BoardDTO> list = bdao.getList(start, end);//방명록 리스트 dao에서 메서드 가져오기
			request.setAttribute("list", list);//request 영역에 저장
			request.setAttribute("pager", pager);//페이지 네비게이션 출력을 위한 정보 전달
			System.out.println("request 저장 list 확인 o:"+list);//연결확인
			System.out.println("request 저장 page 확인 o:"+pager);//연결확인
			String page = "/board/list.jsp";
			//forwarding
			RequestDispatcher dis = request.getRequestDispatcher(page);
			dis.forward(request, response);
			System.out.println("list.do => list.jsp 포워딩 o");//연결확인  
			
			
		}else if(url.indexOf("insert.do") != -1) {
			System.out.println("insert.do 호출...ok!");
			//파일 업로드 처리
			//디렉토리가 없으면 생성
			File uploadDir = new File(Constants.UPLOAD_PATH);
			if(!uploadDir.exists()) {
				uploadDir.mkdir();
			}
			//request를 확장시킨 MultipartRequest 생성
			//new MultipartRequest(request , 파일업로드 디렉토리,업로드 용량지정
			//                      ,인코딩방식지정, 파일이름 중복 정책(DefaultFileRenamePolicy)
			MultipartRequest multi = new MultipartRequest(
					request, Constants.UPLOAD_PATH, Constants.MAX_UPLOAD
					, "utf-8", new DefaultFileRenamePolicy());
			
			String writer = multi.getParameter("writer");//tag의 name 값에서 받아옴
			String subject = multi.getParameter("subject");//tag의 name 값에서 받아옴
			String content = multi.getParameter("content");//tag의 name 값에서 받아옴
			String passwd = multi.getParameter("passwd");//tag의 name 값에서 받아옴
			String ip = request.getRemoteAddr();//클라이언트의 ip주소

			String filename=" ";//공백1개
			int filesize= 0;
			
			try {
				//첨부파일의 집합
				Enumeration files = multi.getFileNames();
				//다음요소가 있으면
				while(files.hasMoreElements()) {
					//첨부파일의 이름
					String file1 = (String) files.nextElement();
					filename = multi.getFilesystemName(file1);
					File f1=multi.getFile(file1);
					if(f1 != null) {
						filesize=(int)f1.length();//파일 사이즈 저장
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}			
			
			BoardDTO bdto = new BoardDTO();
			bdto.setWriter(writer);
			bdto.setSubject(subject);
			bdto.setContent(content);
			bdto.setPasswd(passwd);
			bdto.setIp(ip);
			//파일첨부를 하지 않을 경우
			//trim() 문자열의 좌우 공백 제거
			if(filename == null || filename.trim().equals("")) {
				filename="-";
			}
			bdto.setFilename(filename);
			bdto.setFilesize(filesize);
			
			bdao.getInsert(bdto);
			String page = "/board_servlet/list.do";
			//forwarding
		/*	RequestDispatcher dis = request.getRequestDispatcher(page);
			dis.forward(request, response);
			System.out.println("insert.do => list.do  포워딩 o");//연결확인  */	
			response.sendRedirect(contextPath+page);
			System.out.println("insert.do =>list.do sendRedirect o");//연결확인  
			
			
		}else if(url.indexOf("download.do") != -1) {
			System.out.println("download.do 호출...ok!");
			int num = Integer.parseInt(request.getParameter("num"));
			//dao호출
			String filename = bdao.getFileName(num);
			System.out.println("첨부파일 이름 확인:"+filename);
			//다운로드 할 파일 경로
			String path=Constants.UPLOAD_PATH+filename;
			byte b[] = new byte[4096]; //바이트배열 생성
			//서버에 저장된 파일을 읽기 위한 스트림 생성
			FileInputStream fis = new FileInputStream(path);
			//mimeType(파일의종류)
			String mimeType = getServletContext().getMimeType(path);
			if(mimeType == null) {
				mimeType="application/octet-stream; charset=utf-8";	//octet-stream; 범용적인 
			}
			//파일 이름에 한글이 포함된 경우  header의 경우 한글이나 특수문자 사용금지이기에
			//new String (바이트배열, 변환할 인코딩) 8859_1 서유럽언어
			filename = new String (filename.getBytes("utf-8"), "8859_1");
			//http header 
			response.setHeader("Content-Disposition", "attachment; filename="+filename);//첨부파일은 이것입니다!
			//http body
			//OutputStream 생성 (서버에서 클라이언트에 쓰기)
			ServletOutputStream out = response.getOutputStream();
			int numRead;
			while(true) {
				numRead = fis.read(b,0,b.length);//데이터 읽음
				if(numRead == -1) break;//더이상 내용이 없으면
				out.write(b, 0, numRead);//데이터 쓰기
			}
			//파일 처리 관련 리소스 정리
			out.flush();
			out.close();
			fis.close();
			//다운로드 횟수 증가 처리
			bdao.getPlusDown(num);
			
			
		}else if (url.indexOf("view.do") != -1) {
			System.out.println("view.do 호출...ok!");
			int num = Integer.parseInt(request.getParameter("num"));
			//조회수 증가 처리
			HttpSession session = request.getSession();
			bdao.getPlusReadCount(num , session);//(num ,request.getSession());
			BoardDTO bdto = bdao.getViewReplace(num);//번호에 해당하는 게시물 리턴
			request.setAttribute("bdto", bdto);//request 영역에 저장
			System.out.println("request 저장 bdto 확인 o:"+bdto);//연결확인
			//화면전환 -forwarding
			String page = "/board/view.jsp";
			RequestDispatcher dis = request.getRequestDispatcher(page);
			dis.forward(request, response);
			System.out.println("view.do => view.jsp 포워딩 o");//연결확인  
		
			
		}else if (url.indexOf("commentList.do") != -1) {
			System.out.println("commentList.do 호출...ok!");
			int num = Integer.parseInt(request.getParameter("num"));
			System.out.println("댓글을 위한 게시물 번호:"+num);
			//댓글 목록 리턴 dao 메서드 호출
			List<BoardCommentDTO> list = bdao.getCommentList(num);
			//requst영역에 저장
			request.setAttribute("list", list);
			System.out.println("request 저장 list 확인 o:"+list);//연결확인
			//출력페이지로 이동
			String page = "/board/comment_list.jsp";
			RequestDispatcher dis = request.getRequestDispatcher(page);
			dis.forward(request, response);
			System.out.println("commentList.do => comment_list.jsp 포워딩 o");//연결확인  
			
			
		}else if (url.indexOf("comment_add.do") != -1) {
			System.out.println("comment_add.do 호출...ok!");
			BoardCommentDTO bdto = new BoardCommentDTO();
			//게시물 번호 -ajax로 백그라운드에서 실행 하였기에 화면전환 할필요가 없다
			int board2_num = Integer.parseInt(request.getParameter("board2_num"));
			String writer = request.getParameter("writer");
			String content = request.getParameter("content");
			bdto.setBoard2_num(board2_num);
			bdto.setWriter(writer);
			bdto.setContent(content);
			bdao.getCommentAdd(bdto);
			
			
		}else if (url.indexOf("reply.do") != -1) {
			System.out.println("reply.do 호출...ok!");
			int num = Integer.parseInt(request.getParameter("num"));
			BoardDTO bdto =bdao.getView(num);
			bdto.setContent("=====게시물의 내용=====\n"+bdto.getContent());
			request.setAttribute("bdto", bdto);//request 영역에 저장
			System.out.println("request 저장 bdto 확인 o:"+bdto);//연결확인
			//화면전환 -forwarding
			String page = "/board/reply.jsp";
			RequestDispatcher dis = request.getRequestDispatcher(page);
			dis.forward(request, response);
			System.out.println("reply.do => reply.jsp 포워딩 o");//연결확인  
			
		}else if (url.indexOf("insertReply.do") != -1) {
			System.out.println("insertReply.do 호출...ok!");
			int num = Integer.parseInt(request.getParameter("num"));//게시물번호
			BoardDTO bdto =bdao.getView(num);
			int ref=bdto.getRef();//답변 그룹번호
			int re_level=bdto.getRe_level()+1;//답변 단계
			int re_step=bdto.getRe_step()+1;//출력순번
			String writer = request.getParameter("writer");
			String subject = request.getParameter("subject");
			String content = request.getParameter("content");
			String passwd = request.getParameter("passwd");
			bdto.setWriter(writer);
			bdto.setSubject(subject);
			bdto.setContent(content);
			bdto.setPasswd(passwd);
			bdto.setRef(ref);
			bdto.setRe_step(re_step);
			bdto.setRe_level(re_level);
			//첨부파일 관련정보
			bdto.setFilename("-");
			bdto.setFilesize(0);
			bdto.setDown(0);
			//dao실행호출
			// 답글순서조정
			bdao.getUpdateStep(ref, re_level);
			//답글쓰기
			bdao.getReply(bdto);
			//목록으로 이동
			String page = "/board_servlet/list.do";
			//forwarding
		/*	RequestDispatcher dis = request.getRequestDispatcher(page);
			dis.forward(request, response);
			System.out.println("insertReply.do => list.do  포워딩 o");//연결확인  */	
			response.sendRedirect(contextPath+page);
			System.out.println("insertReply.do =>list.do sendRedirect o");//연결확인  
			
			
		}else if (url.indexOf("pass_check.do") != -1) {
			System.out.println("pass_check.do 호출.....ok");
			//게시물 번호
			int num =Integer.parseInt(request.getParameter("num"));
			//입력한 비밀번호 
			String passwd = request.getParameter("passwd");
			//비밀번호가 맞는지 확인
			String result = bdao.getPassCheck(num, passwd);
			String page="";
			if(result != null) { //비밀번호가 맞으면
				page="/board/edit.jsp";
				request.setAttribute("bdto", bdao.getView(num) );
				RequestDispatcher dis = request.getRequestDispatcher(page);
				dis.forward(request, response);
				System.out.println("비밀번호 맞음=> edit.jsp 포워딩 o");
			}else { //비밀번호가 틀리면
				page="/board_servlet/view.do?num="+num+"&message=error";
				response.sendRedirect(contextPath+page);
				System.out.println("비밀번호 틀림 =>view.do sendRedirect o");//연결확인  
			}
			
		}else if (url.indexOf("update.do") != -1) {
			System.out.println("update.do 호출.....ok");
			//파일 업로드 처리
			//디렉토리가 없으면 생성
			File uploadDir = new File(Constants.UPLOAD_PATH);
			if(!uploadDir.exists()) {
				uploadDir.mkdir();
			}
			//request를 확장시킨 MultipartRequest 생성
			//new MultipartRequest(request , 파일업로드 디렉토리,업로드 용량지정
			//                      ,인코딩방식지정, 파일이름 중복 정책(DefaultFileRenamePolicy)
			MultipartRequest multi = new MultipartRequest(
					request, Constants.UPLOAD_PATH, Constants.MAX_UPLOAD
					, "utf-8", new DefaultFileRenamePolicy());
			
			int num = Integer.parseInt(multi.getParameter("num"));//추가*
			String writer = multi.getParameter("writer");//tag의 name 값에서 받아옴
			String subject = multi.getParameter("subject");//tag의 name 값에서 받아옴
			String content = multi.getParameter("content");//tag의 name 값에서 받아옴
			String passwd = multi.getParameter("passwd");//tag의 name 값에서 받아옴
			String ip = request.getRemoteAddr();//클라이언트의 ip주소

			String filename=" ";//공백1개
			int filesize= 0;
			
			try {
				//첨부파일의 집합
				Enumeration files = multi.getFileNames();
				//다음요소가 있으면
				while(files.hasMoreElements()) {
					//첨부파일의 이름
					String file1 = (String) files.nextElement();
					filename = multi.getFilesystemName(file1);
					File f1=multi.getFile(file1);
					if(f1 != null) {
						filesize=(int)f1.length();//파일 사이즈 저장
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}			
			
			BoardDTO bdto = new BoardDTO();
			bdto.setNum(num);//추가*
			bdto.setWriter(writer);
			bdto.setSubject(subject);
			bdto.setContent(content);
			bdto.setPasswd(passwd);
			bdto.setIp(ip);
			//파일첨부를 하지 않을 경우
			//trim() 문자열의 좌우 공백 제거
			if(filename == null || filename.trim().equals("")) {
				//새로운 첨부 파일이 없을때(기존테이블의 정보를 가져옴)
				BoardDTO bdto2 = bdao.getView(num);
				String fName = bdto2.getFilename();
				int fSize = bdto2.getFilesize();
				int fDown = bdto2.getDown();
				bdto2.setFilename(fName);
				bdto2.setFilesize(fSize);
				bdto2.setDown(fDown);
			} else {
				//새로운 첨부 파일이 있을때
				bdto.setFilename(filename);
				bdto.setFilesize(filesize);
			}
			//첨부파일 삭제 처리
			String fileDel = multi.getParameter("fileDel"); //edit.jsp name="fileDel">첨부파일 삭제<br>
			//체크박스에 체크 되었으면 =>"on"
			if (fileDel != null && fileDel.equals("on")) {
				String fileName = bdao.getFileName(num);
				File f = new File(Constants.UPLOAD_PATH+fileName);
				f.delete();//파일 삭제
				//첨부파일 정보를 지움 -삭제후 초기화
				bdto.setFilename("-");
				bdto.setFilesize(0);
				bdto.setDown(0);
			}
			//레코드 수정
			bdao.getUpdate(bdto);
			//페이지 이동
			String page = "/board_servlet/list.do";
			response.sendRedirect(contextPath+page);
			System.out.println("insert.do =>list.do sendRedirect o");//연결확인  
			
			
		}else if (url.indexOf("delete.do") != -1) {
			System.out.println("delete.do 호출.....ok");
			//삭제할 게시물 번호 
			//enctype="multipart/form-data" 이거 사용시 request.getParameter("");사용이 안됨
			//int num = Integer.parseInt(request.getParameter("num"));
			MultipartRequest multi = new MultipartRequest(
					request, Constants.UPLOAD_PATH, Constants.MAX_UPLOAD
					, "utf-8", new DefaultFileRenamePolicy());
			//삭제할 게시물 번호
			int num = Integer.parseInt(multi.getParameter("num"));//추가*
			//DAO xml호출
			 bdao.getDelete(num);
			//페이지 이동
			String page = "/board_servlet/list.do";
			response.sendRedirect(contextPath+page);
			System.out.println("delete.do =>list.do sendRedirect o");//연결확인  
			
			
		}else if (url.indexOf("search.do") != -1) {
			System.out.println("search.do 호출.....ok");
			//검색 옵션과 검색 키워드
			String search_option = request.getParameter("search_option");
			String keyword = request.getParameter("keyword");
			List <BoardDTO> list = bdao.getSearchList(search_option, keyword);
			request.setAttribute("list", list);//request 영역에 저장
			request.setAttribute("search_option", search_option);//request 영역에 저장
			request.setAttribute("keyword", keyword);//request 영역에 저장
			System.out.println("request 저장 list 확인 o:"+list);//연결확인
			System.out.println("request 저장 search_option 확인 o:"+search_option);//연결확인
			System.out.println("request 저장 keyword 확인 o:"+keyword);//연결확인
			//화면전환 -forwarding
			String page = "/board/search.jsp";
			RequestDispatcher dis = request.getRequestDispatcher(page);
			dis.forward(request, response);
			System.out.println("search.do => search.jsp 포워딩 o");//연결확인  
			
		}
		
		
		
		
		
	}

}
