package test;

import static org.junit.Assert.*;

import org.junit.Test;

import util.StringUtil;

public class StringUtilTest {

	@Test
	public void stringUtilMethodTest() {
		String htmlString = "히히  나는   \n <br> 쓰고 <div class=\"moon\">";
		String result = "히히&nbsp;&nbsp;나는&nbsp;&nbsp;&nbsp;<br>&nbsp;&lt;br&gt;&nbsp;쓰고&nbsp;&lt;div&nbsp;class=\"moon\"&gt;";
		assertEquals(result, StringUtil.parseHtml(htmlString));
	}

}
