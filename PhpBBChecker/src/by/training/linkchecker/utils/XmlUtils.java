package by.training.linkchecker.utils;

import by.training.linkchecker.model.Report;

/**
 * Simple utility class for comfortable xml printing.
 */
public class XmlUtils {

	public String getCommandElement(Report r) {
		return  "\n"
				+ "    <command name = \""
				+ r.getKeyword()
				+ "\">\r\n" + 
				"      <state>"
				+ r.getState()
				+ "</state>\r\n" + 
				"      <original>"
				+ "<![CDATA[" + r.getOriginal() + "]]>"
				+ "</original>\r\n" + 
				"      <message>"
				+ r.getMessage()
				+ "</message>\r\n" + 
				"      <time>"
				+ GeneralUtils.getThreeDigitsTimeInSeconds(r.getTime())
				+ "</time>\r\n" + 
				"    </command>";
	}
}
	