package board.dto;

import java.util.Date;

public class BoardCommentDTO {
	
	private int comment_num; //댓글번호
	private int board2_num;	//게시글 번호FK
	private String writer;
	private String content;
	private Date reg_date; //java.util.Date;
	
	
	public BoardCommentDTO() {
		// TODO Auto-generated constructor stub
	}


	public BoardCommentDTO(int comment_num, int board2_num, String writer, String content, Date reg_date) {
		super();
		this.comment_num = comment_num;
		this.board2_num = board2_num;
		this.writer = writer;
		this.content = content;
		this.reg_date = reg_date;
	}


	@Override
	public String toString() {
		return "BoardCommentDTO [comment_num=" + comment_num + ", board2_num=" + board2_num + ", writer=" + writer
				+ ", content=" + content + ", reg_date=" + reg_date + "]";
	}


	public int getComment_num() {
		return comment_num;
	}


	public void setComment_num(int comment_num) {
		this.comment_num = comment_num;
	}


	public int getBoard2_num() {
		return board2_num;
	}


	public void setBoard2_num(int board2_num) {
		this.board2_num = board2_num;
	}


	public String getWriter() {
		return writer;
	}


	public void setWriter(String writer) {
		this.writer = writer;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public Date getReg_date() {
		return reg_date;
	}


	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}
	
	

}
