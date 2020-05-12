package voca;

public enum ResultMessage {
	SUCCESS("정상적으로 처리되었습니다."), // 정상 처리시 무조건 이 값 사용중
	
	OVERLAP("중복된 단어입니다"),
	MISSING_WORD("오답입니다"),
	FAIL("단어 수정에 실패하였습니다.");
	
	private String message;
	
	ResultMessage(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	
}
