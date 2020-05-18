package model;

public class WealthPostDTO extends PostDTO{
	private String imgFileName;
	private String imgFileRealName;
	private String saveDirectory;
	
	public String getImgFileName() {
		return imgFileName;
	}
	public void setImgFileName(String imgFileName) {
		this.imgFileName = imgFileName;
	}
	public String getImgFileRealName() {
		return imgFileRealName;
	}
	public void setImgFileRealName(String imgFileRealName) {
		this.imgFileRealName = imgFileRealName;
	}
	public String getSaveDirectory() {
		return saveDirectory;
	}
	public void setSaveDirectory(String saveDirectory) {
		this.saveDirectory = saveDirectory;
	}
	
}
