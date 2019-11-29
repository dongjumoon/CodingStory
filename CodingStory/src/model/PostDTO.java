package model;

public class PostDTO implements DTOInterface {
	private int boardId;
	private String userId;
	private String boardTitle;
	private String boardContent;
	private String boardDate;
	private int boardViews;
	
	public int getBoardId() {
		return boardId;
	}
	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getBoardTitle() {
		return boardTitle;
	}
	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}
	public String getBoardContent() {
		return boardContent;
	}
	public void setBoardContent(String boardContent) {
		this.boardContent = boardContent;
	}
	public String getBoardDate() {
		return boardDate;
	}
	public void setBoardDate(String boardDate) {
		this.boardDate = boardDate;
	}
	public int getBoardViews() {
		return boardViews;
	}
	public void setBoardViews(int boardHit) {
		this.boardViews = boardHit;
	}
	
}
