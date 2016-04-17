package by.training.linkchecker.writers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import by.training.linkchecker.commands.PingCommand;
import by.training.linkchecker.model.Report;
import by.training.linkchecker.utils.GeneralUtils;
import by.training.linkchecker.utils.XmlUtils;

/**
 * Writer for proper writing to xml files.
 */
public class WriterToXml extends AbstractWriter{

	/**
	 * @param path String as path of object.
	 */
	@Override
	public void writeReports(String path) {
		/*Creating file with {nameOforiginalFile}_log.xml name*/
		XmlUtils utilsXml = new XmlUtils();
		File file = new File(path);
		String filename = file.getName().split("\\.")[0];
		File name = new File(file.getParentFile() + filename + "_log.xml");

		try {
			if (!name.createNewFile()) {
				System.out.println("log already exists!");
				System.exit(0);
			}
		} catch (IOException e) {
			System.out.println(
					"Cannot create file! Check free disk space and possibility of creating files in that folder");
			System.exit(0);
		}
		try {
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(name), "UTF-8"));
			totalReports = reports.size();
			
			out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
					"<TESTINFO>\r\n" + 
					"  <commands>");
			
			positive = 0;
			negative = 0;
			totalTime = 0;
			skipped = 0;
			/*Simple cycle for writing every report into a file and count statistics*/
			for (Report r : reports) {
				
				totalTime = totalTime + r.getTime();
				if (r.getMessage().equals("(skipped)")) {
					skipped = skipped + 1;
					continue;
				}

				if (r.getState() == true) {
					positive = positive + 1;
				} else {
					negative = negative + 1;
				}
				
				out.write(utilsXml.getCommandElement(r));
			}
			
			out.write("  </commands>" + System.lineSeparator());
			out.write("  <statistics>"  + System.lineSeparator());
			out.write("    <totalTests>" + totalReports + "</totalTests>" + System.lineSeparator());
			out.write("    <positive>" + positive + "</positive>"  + System.lineSeparator());
			out.write("    <negative>" + negative + "</negative>"  + System.lineSeparator());
			out.write("    <skipped>" + skipped + "</skipped>"  + System.lineSeparator());
			out.write("    <totalTime>" + GeneralUtils.getThreeDigitsTimeInSeconds(totalTime) + "</totalTime>"  + System.lineSeparator());
			out.write("    <averageTime>" + GeneralUtils.getThreeDigitsTimeInSeconds(totalTime/totalReports) + "</averageTime>"  + System.lineSeparator());
			out.write("    <averagePing>" + GeneralUtils.getThreeDigitsTimeInSeconds(PingCommand.getAveragePing()) + "</averagePing>"  + System.lineSeparator());
			out.write("  </statistics>"  + System.lineSeparator());
			out.write("</TESTINFO>"  + System.lineSeparator());
			out.close();
			
		} catch (IOException e) {
			System.out.println(
					"Cannot write to file! Check free disk space and possibility of reading/writing to that folder");
			System.exit(0);
		}
		
		
	}

}
