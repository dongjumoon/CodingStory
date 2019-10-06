package model;

public class FreeCommentDTO {
	private int parentId;
	private int cmtId;
	private String cmtContent;
	private String cmtUser;
	private String cmtDate;
	
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
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
