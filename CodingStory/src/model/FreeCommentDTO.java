package model;

public class FreeCommentDTO {
	private int boardId;
	private int cmtId;
	private String cmtContent;
	private String cmtUser;
	private String cmtDate;
	
	
	public int getBoardId() {
		return boardId;
	}
	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}
	public int getCmtId() {
		return cmtId;
	}
	public void setCmtId(int cmtId) {
		this.cmtId = cmtId;
	}
	public String getCmtContent() {
		return cmtContent;
	}
	public void setCmtContent(String cmtContent) {
		this.cmtContent = cmtContent;
	}
	public String getCmtUser() {
		return cmtUser;
	}
	public void setCmtUser(String cmtUser) {
		this.cmtUser = cmtUser;
	}
	public String getCmtDate() {
		return cmtDate;
	}
	public void setCmtDate(String cmtDate) {
		this.cmtDate = cmtDate;
	}
	
	
}
