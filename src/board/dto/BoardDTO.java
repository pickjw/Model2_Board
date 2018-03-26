package board.dto;

import java.sql.Date;

public class BoardDTO {
	private int num;
	private String writer;
	private String subject;
	private String passwd;
	private Date reg_date; //java.sql.Date;  //계산이 필요한 경우=>Date  ex)90일경과 해지     계산이 필요하지 않은 경우 =>String to_char 함수
	private int readcount;//조회수
	private int ref;
	private int re_step;
	private int re_level;
	private String content;
	private String ip;
	private int comment_count; //댓글 갯수
	private String filename; //확장자 검사 악성 파일 검사
	private int filesize;
	private int down;
	private String ext;//첨부파일의 확장자-확장자 검사 악성 파일 검사
	private String show;
	
	
	
	
	public void setShow(String show) {
		this.show = show;
	}
	
	public String getShow() {
		return show;
	}
		
	
	public BoardDTO() {
		// TODO Auto-generated constructor stub
	}


	public BoardDTO(int num, String writer, String subject, String passwd, Date reg_date, int readcount, int ref,
			int re_step, int re_level, String content, String ip, int comment_count, String filename, int filesize,
			int down, String ext) {
		super();
		this.num = num;
		this.writer = writer;
		this.subject = subject;
		this.passwd = passwd;
		this.reg_date = reg_date;
		this.readcount = readcount;
		this.ref = ref;
		this.re_step = re_step;
		this.re_level = re_level;
		this.content = content;
		this.ip = ip;
		this.comment_count = comment_count;
		this.filename = filename;
		this.filesize = filesize;
		this.down = down;
		this.ext = ext;
	}
	
	@Override
	public String toString() {
		return "BoardDTO [num=" + num + ", writer=" + writer + ", subject=" + subject + ", passwd=" + passwd
				+ ", reg_date=" + reg_date + ", readcount=" + readcount + ", ref=" + ref + ", re_step=" + re_step
				+ ", re_level=" + re_level + ", content=" + content + ", ip=" + ip + ", comment_count=" + comment_count
				+ ", filename=" + filename + ", filesize=" + filesize + ", down=" + down + ", ext=" + ext + "]";
	}


	public int getNum() {
		return num;
	}


	public void setNum(int num) {
		this.num = num;
	}


	public String getWriter() {
		return writer;
	}


	public void setWriter(String writer) {
		this.writer = writer;
	}


	public String getSubject() {
		return subject;
	}


	public void setSubject(String subject) {
		this.subject = subject;
	}


	public String getPasswd() {
		return passwd;
	}


	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}


	public Date getReg_date() {
		return reg_date;
	}


	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}


	public int getReadcount() {
		return readcount;
	}


	public void setReadcount(int readcount) {
		this.readcount = readcount;
	}


	public int getRef() {
		return ref;
	}


	public void setRef(int ref) {
		this.ref = ref;
	}


	public int getRe_step() {
		return re_step;
	}


	public void setRe_step(int re_step) {
		this.re_step = re_step;
	}


	public int getRe_level() {
		return re_level;
	}


	public void setRe_level(int re_level) {
		this.re_level = re_level;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getIp() {
		return ip;
	}


	public void setIp(String ip) {
		this.ip = ip;
	}


	public int getComment_count() {
		return comment_count;
	}


	public void setComment_count(int comment_count) {
		this.comment_count = comment_count;
	}


	public String getFilename() {
		return filename;
	}


	public void setFilename(String filename) {
		this.filename = filename;
	}


	public int getFilesize() {
		return filesize;
	}


	public void setFilesize(int filesize) {
		this.filesize = filesize;
	}


	public int getDown() {
		return down;
	}


	public void setDown(int down) {
		this.down = down;
	}


	public String getExt() {
		return ext;
	}


	public void setExt(String ext) {
		this.ext = ext;
	}
	
	
	
}
