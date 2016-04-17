package by.training.mp3lister.utils;

/**
 * Utility class for writing fancy HTML and using for tree data structure.
 */
public class HtmlUtils {
		
	private int folderCount = 0;
	private int subFolderCount = 0;
    /**
     * @param artist  artist name = folder name.
     * @return part of html for printing part of html for folder
     */
	public String printTopFolder(String artist) {
		folderCount = folderCount + 1;
		return "		<li>\r\n" + 
				"			<label for=\""
				+ "folder" + folderCount
				+ "\">"
				+ artist
				+ "</label> <input type=\"checkbox\" id=\""
				+ "folder" +  + folderCount
				+ "\" /> \r\n" + 
				"			<ol>\r\n";
	}
    /**
     * @return part of html for printing part of html for folder
     */
	public String printBottomFolder() {
		return "			</ol>\r\n" + 
				"		</li>\r\n";
	}
    /**
     * @param album  album name = subfolder name.
     * @return part of html for printing part of html for subfolder
     */
	public String printTopSubFolder(String album) {
		subFolderCount = subFolderCount + 1;
		return "				<li>\r\n" + 
				"					<label for=\""
				+ "subfolder" + subFolderCount
				+ "\">"
				+ album
				+ "</label> <input type=\"checkbox\" id=\""
				+ "subfolder" +  + subFolderCount
				+ "\" /> \r\n" + 
				"					<ol>\r\n";
	}
    /**
     * @return part of html for printing part of html for subfolder
     */
	public String printBottomSubFolder() {
		return "					</ol>\r\n" + 
				"				</li>\r\n";
	}
    /**
     * @param name  song name.
     * @param duration duration of song.
     * @param path path of file.
     * @return part of html for printing part of html for song
     */
	public String printRow(String name, String duration, String path) {
		return "						<li class=\"file\"><a href=\""
				+ "file:///" + path
				+ "\">"
				+ name + " <span  style = \"color:#f0a90f\">" + duration + "</span> " + path
				+ "</a></li>\r\n";
	}
    /**
     * @return part of html for printing part of html for final html.
     */
	public String printTopHtml() {
		return "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">\r\n" + 
				"\r\n" + 
				"<html>\r\n" + 
				"\r\n" + 
				"<head>\r\n" + 
				"	<meta http-equiv=\"Content-Type\" content=\"text/html;charset=utf-8\">\r\n" + 

				"	<style>\r\n" + 
				"	/* Just some base styles not needed for example to function */\r\n" + 
				"*, html { font-family: Verdana, Arial, Helvetica, sans-serif; }\r\n" + 
				"\r\n" + 
				"body, form, ul, li, p, h1, h2, h3, h4, h5\r\n" + 
				"{\r\n" + 
				"	margin: 0;\r\n" + 
				"	padding: 0;\r\n" + 
				"}\r\n" + 
				"body { background-color: #293d3d; color: #f3f3f3; margin: 0; }\r\n" + 
				"img { border: none; }\r\n" + 
				"p\r\n" + 
				"{\r\n" + 
				"	font-size: 1em;\r\n" + 
				"	margin: 0 0 1em 0;\r\n" + 
				"}\r\n" + 
				"\r\n" + 
				"html { font-size: 100%; /* IE hack */ }\r\n" + 
				"body { font-size: 1em; /* Sets base font size to 16px */ }\r\n" + 
				"table { font-size: 100%; /* IE hack */ }\r\n" + 
				"input, select, textarea, th, td { font-size: 1em; }\r\n" + 
				"	\r\n" + 
				"/* CSS Tree menu styles */\r\n" + 
				"ol.tree\r\n" + 
				"{\r\n" + 
				"	padding: 0 0 0 30px;\r\n" + 
				"	width: 90%;\r\n" + 
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
				"	}\r\n" + 
				"		li.file a\r\n" + 
				"		{\r\n" + 
				"			background:  url('data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAACXklEQVR42o2T60tTcRjHv7+zucuZW8rMy6pZCS3FejMzQoKug171MnzpIoKodaFXK3qRlUFEWZSsUpMu/0EIQhZChbXeeEEkDfSFrOmkbWcXt53z6znTzZNK9MDDeX6/8/B5br+HYY2Y3j5ws/JNZ8k8Qlq7cj1DOshjUiB9yvdd688KhuHWFVHnbnxMZpv2fo1w0l55/MeFzNWOZBFgaL8s6pr2DJDZgv+TTwTxqJA8wNzf000fr2rvNFvRubsZpbqSovd8KoG+2Um8i4e1kJ7UCe9pZuy75xYq7d8K2fic9XDJOkTSKcQyaTitZXBvdmAquogRnsb9mfFiOdSTfYyiP6PDmcLtJWcDWu3bMLYYhqvMjrScwxaLLQ8IJSX4J4YRNpeACYLq/lwFTJFRpwXIC4tQOF9XuKu8AoGxIEajEbAqO9VumlYBWfqn1wI6B/shE4CZ9BBIQdGYwHBUrMFcIo7RyK9lZ6sl90+AQKkqSzlQOmBGPTzV2zEnaQBAbsMStBlQEyAwBsUgbASY3rCJj4YGwEU9OEX21x/A9f2HYXnVgWO2rX+XALxgxu67bsFRWRzjRQI8/fIesoUAySz8jS241nwIpYF2eGp2YDYezU8oP0Yp0bzuIe0SbWi1VkNKJBDPZtDW2IS9FVW48/UjNYrhdnAIGUVWXXuXfDe9y0/Zf07UHWwqPmWuKOCheSCRgsNixcm6BnSNDGsn+lmZ/Hk8++R1cnWZliHFZeLURB5aAKWydpleKhPT57Ndb1aXSSsrPcmvM0FqeTgCxCR1nT/w37FA5sbDoNb/D0A7DdlPqbFLAAAAAElFTkSuQmCC') 0 0 no-repeat;\r\n" + 
				"			color: #f3f3f3;\r\n" + 
				"			padding-left: 21px;\r\n" + 
				"			text-decoration: none;\r\n" + 
				"			display: block;\r\n" + 
				"		}\r\n" + 
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
				"			background:  url('data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAURJREFUeNpi/P//PwMlgImBQkCxASwwRlLLKwYmJqZgRkbGbiBXEYva+0Dvlv7792/tvBoxTAO+fv0MororE6UU9VU5MHRfvP1DsX3+M5DhaxkYxDC98ObNGxBW1FVmY/j16xcYu6SdYvjw4QPDixcvGGSEvoLlQeqweuHdu7dg+vfv32D85ctXsNijR4/B4hwcnHA1WA348uUbmP779y+DUchOuIKQsltgetsUE7garAb8/w9h/vz5h+H0Sk8w2yRsN8OZVa5g9ocPn+BqsBrAzs4PdQEzw48ff+Fi375B2Gxs3HA1WNPB45NlDNzcIvfPXv8LVMwJxmdWOcDZF2//A8uD1GF1wefXZ8Q+Pt42oWN+VBED41d5DKv+/30IlJ8IVCcF5D2DCTPC8gIwAXEDKT4Qk0Di+wzU8xnDgKGbmQACDAAtTZadqmiADQAAAABJRU5ErkJggg==') 40px 0 no-repeat;\r\n" + 
				"			margin: -0.938em 0 0 -44px; /* 15px */\r\n" + 
				"			height: 1em;\r\n" + 
				"		}\r\n" + 
				"		li input + ol > li { display: none; margin-left: -14px !important; padding-left: 1px; }\r\n" + 
				"	li label\r\n" + 
				"	{\r\n" + 
				"		background:  url('data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAATNJREFUeNqkk71KA1EQhc/dOxsbEYukVYm9jQg+hz6CYGkrBNsEKwtrX0EfwU4UVFDLFWSDlYGAGszP3p91ZlNpdoVrBg572bnf2ZlhR+V5jnmCbo9VZTJS2ODHbkX63Od4Ij7ssdbKbvAFbB76o2GWYTAe42sywX7aQL8/xNnjYsRXttR1G+3tg4tW7twPWGmNXvJcnBvrzRlzyd+c7nTIeWjPXxD9jjqDb0mC9O6+tAdhpwbWQDTTwsCgvrpSCksFr1dsYAsDC1diUETFe11bgC0qcCBnMoiCQikIS9ZxBcawgQnkIwgrBiT9h1YQRYUBkfFTA2/DKvA8RGGJ5xf/OcSK0JogLH2MsCS/VKzjsBZYwtJDiu7nSevyP4v00kNXNqnGWpbFCuQt613Nu87fAgwAb3KTD1NdyNYAAAAASUVORK5CYII=') 15px 1px no-repeat;\r\n" + 
				"		cursor: pointer;\r\n" + 
				"		display: block;\r\n" + 
				"		padding-left: 37px;\r\n" + 
				"	}\r\n" + 
				"\r\n" + 
				"	li input:checked + ol\r\n" + 
				"	{\r\n" + 
				"		background:  url('data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAASxJREFUeNpi/P//PwMlgImBQkCxASwwRlLLKwYmJqZgRkbGbiBXEYva+0Dvlv7792/tvBoxTAO+fv0MororE6UU9VU5MHRfvP1DsX3+M5DhaxkYsBjw5s0bEKWoq6zA8OvXL7AYKIC/f//O8OPHDwYZIVaQGqjLlDENePfuLZj+/fs3GH/58pXh/fv3YDYIcHBwwtVgDYMvX76B6b9//zIYhezEULhtiglcDVYD/v+HMH/+/MNweqUnhsIPHz7B1WA1gJ2dH+oCZqCf/2IoZGPjhqvBmg4enyxj4OYWuX/2+l+gYk4MfPH2P7A8SB1WF3x+fUbs4+NtEzrmRxUxMH6Vx7Dq/9+HQPmJQHVSQN4zmDAjLC8AExA3kOIDMQkkvs9APZ8xDBi6mQkgwADDMYZH9Ls66AAAAABJRU5ErkJggg==') 40px 5px no-repeat;\r\n" + 
				"		margin: -1.25em 0 0 -44px; /* 20px */\r\n" + 
				"		padding: 1.563em 0 0 80px;\r\n" + 
				"		height: auto;\r\n" + 
				"	}\r\n" + 
				"		li input:checked + ol > li { display: block; margin: 0 0 0.125em;  /* 2px */}\r\n" + 
				"		li input:checked + ol > li:last-child { margin: 0 0 0.063em; /* 1px */ }\r\n" + 
				"	</style>\r\n" + 
				"	<title>Mp3 Checker</title>\r\n" + 
				"\r\n" + 
				"</head>\r\n" + 
				"<body>\r\n" + 
				"	\r\n" + 
				"	<ol class=\"tree\">";
	}
    /**
     * @return part of html for printing part of html for final html.
     */
	public String printBottomHtml() {
		return "	</ol>\r\n" + 
				"	\r\n" + 
				"</body>\r\n" + 
				"</html>";
	}
	
	
	
}
