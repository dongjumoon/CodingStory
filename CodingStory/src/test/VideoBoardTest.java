package test;

import static org.junit.Assert.*;

import org.junit.Test;

import dao.VideoBoardDAO;

public class VideoBoardTest {

	@Test
	public void getVideoId() {
		String pattern1 = "https://www.youtube.com/embed/VIDEO_ID";
		String pattern2 = "https://www.youtube.com/watch?v=VIDEO_ID";
		String pattern3 = "https://www.youtube.com/watch?v=VIDEO_ID&feature=youtu.be";
		// 위 패턴에서 VIDEO_ID 만 추출 되는지
		VideoBoardDAO vBoard = new VideoBoardDAO();
		assertEquals(vBoard.getVideoId(pattern1), "VIDEO_ID");
		assertEquals(vBoard.getVideoId("VIDEO_ID"), "VIDEO_ID");
		assertEquals(vBoard.getVideoId(pattern2), "VIDEO_ID");
		assertEquals(vBoard.getVideoId(pattern3), "VIDEO_ID");
	}

}
