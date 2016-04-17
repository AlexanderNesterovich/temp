package by.training.linkchecker.utils;

/**
 * Utility class for writing fancy HTML and using for tree data structure.
 */
public class HtmlUtils {
	
	private long count;

	public String getBigTop(String s) {
		count = count + 1;
		return  "		<li>\r\n" + 
				"			<label for=\""
				+ "folder" + count
				+ "\">"
				+ s
				+ "</label> <input type=\"checkbox\" id=\""
				+ "folder" + count
				+ "\" /> \r\n" + 
				"			<ol>";
	}
	
	public String getBigBottom() {
		return "			</ol>\r\n" + 
				"		</li>";
	}
	
	public String getSmallTop(String s) {
		return "				<li class=\"file\">" + s;
	}
	
	public String getSmallBottom() {
		return "</li>";
	}
	
	public String getHtmlTop() {
		return 
				"<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">\r\n" + 
				"\r\n" + 
				"<head>\r\n" + 
				"	<meta http-equiv=\"Content-Type\" content=\"text/html;charset=utf-8\">\r\n" + 
				"	\r\n" + 
				"	<style>\r\n" + 
				"	*, html { font-family: Verdana, Arial, Helvetica, sans-serif; }\r\n" + 
				"body, form, ul, li, p, h1, h2, h3, h4, h5\r\n" + 
				"{\r\n" + 
				"	margin: 0;\r\n" + 
				"	padding: 0;\r\n" + 
				"}\r\n" + 
				"body { background-color: ##ffffff; color: #000000; margin: 0; }\r\n" + 
				"img { border: none; }\r\n" + 
				"p\r\n" + 
				"{\r\n" + 
				"	font-size: 1em;\r\n" + 
				"	margin: 0 0 1em 0;\r\n" + 
				"}\r\n" + 
				"\r\n" + 
				"html { font-size: 100%;}\r\n" + 
				"body { font-size: 1em;}\r\n" + 
				"table { font-size: 100%;}\r\n" + 
				"input, select, textarea, th, td { font-size: 1em; }\r\n" + 
				"ol.tree\r\n" + 
				"{\r\n" + 
				"	padding: 0 0 0 30px;\r\n" + 
				"	width: 100%;\r\n" + 
				"}\r\n" + 
				"	li \r\n" + 
				"	{ \r\n" + 
				"		position: relative; \r\n" + 
				"		margin-left: -15px;\r\n" + 
				"		list-style: none;\r\n" + 
				"	}\r\n" + 
				"	li.file\r\n" + 
				"	{\r\n" + 
				"		margin-left: -1px !important;\r\n" + 
				"		padding-left: 21px;\r\n" + 
				"	}\r\n" + 
				"\r\n" + 
				"	li input\r\n" + 
				"	{\r\n" + 
				"		position: absolute;\r\n" + 
				"		left: 0;\r\n" + 
				"		margin-left: 0;\r\n" + 
				"		opacity: 0;\r\n" + 
				"		z-index: 2;\r\n" + 
				"		cursor: pointer;\r\n" + 
				"		height: 1em;\r\n" + 
				"		width: 1em;\r\n" + 
				"		top: 0;\r\n" + 
				"	}\r\n" + 
				"		li input + ol\r\n" + 
				"		{\r\n" + 
				"			margin: -0.938em 0 0 -44px; /* 15px */\r\n" + 
				"			height: 1em;\r\n" + 
				"		}\r\n" + 
				"		li input + ol > li { display: none; margin-left: -14px !important; padding-left: 1px; }\r\n" + 
				"	li label\r\n" + 
				"	{\r\n" + 
				"		cursor: pointer;\r\n" + 
				"		display: block;\r\n" + 
				"		padding-left: 37px;\r\n" + 
				"	}\r\n" + 
				"\r\n" + 
				"	li input:checked + ol\r\n" + 
				"	{\r\n" + 
				"		margin: -1.25em 0 0 -44px;\r\n" + 
				"		padding: 1.563em 0 0 80px;\r\n" + 
				"		height: auto;\r\n" + 
				"	}\r\n" + 
				"		li input:checked + ol > li { display: block; margin: 0 0 0.125em;}\r\n" + 
				"		li input:checked + ol > li:last-child { margin: 0 0 0.063em;}\r\n" + 
				"	</style>\r\n" + 
				"	\r\n" + 
				"	<title>LinkChecker Statistics</title>\r\n" + 
				"\r\n" + 
				"</head>\r\n" + 
				"<body>\r\n" + 
				"	\r\n" + 
				"	<ol class=\"tree\">";
	}
	
	public String getHtmlBottom() {
		return 
				"		\r\n" + 
				"	</ol>\r\n" + 
				"	\r\n" + 
				"</body>\r\n" + 
				"</html>";
	}
}
