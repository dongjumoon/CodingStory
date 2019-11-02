package util;

public class StringUtil {
	public static String parseHtml(String htmlString) {
		return htmlString.replace("&", "&#38;").replace("<", "&lt;").replace(" ", "&nbsp;").replace("\n", "<br>");
	}
}
