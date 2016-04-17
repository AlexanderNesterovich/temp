package by.training.linkchecker.writers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import by.training.linkchecker.commands.PingCommand;
import by.training.linkchecker.model.Report;
import by.training.linkchecker.utils.GeneralUtils;
import by.training.linkchecker.utils.HtmlUtils;
import by.training.linkchecker.utils.TreeElement;

/**
 * Writer for proper writing to html files.
 */
public class WriterToHtml extends AbstractWriter{
	
	/**
	 * @param path String as path of object.
	 */
	public void writeReports(String path) {
		
		/*Creating file with {nameOforiginalFile}_log.html name*/
		HtmlUtils utilsHtml = new HtmlUtils();
		File file = new File(path);
		String filename = file.getName().split("\\.")[0];
		File name = new File(file.getParentFile() + filename + "_log.html");

		try {
			if (!name.createNewFile()) {
				System.out.println("Log already exists!");
				System.exit(0);
			}
		} catch (IOException e) {
			System.out.println(
					"Cannot create file! Check free disk space and possibility of creating files in that folder");
			System.exit(0);
		}
		try {
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(name), "UTF-8"));
			/*resetting*/
			totalReports = reports.size();
			positive = 0;
			negative = 0;
			totalTime = 0;
			skipped = 0;
			
			TreeElement root = new TreeElement(utilsHtml.getHtmlTop(), utilsHtml.getHtmlBottom());
			TreeElement topElement = null;
			/*checking list of reports and based on keyword throwing them into different elements of tree and count statistics*/
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
				
			
				if (r.getKeyword().equals("open")) {
					topElement = new TreeElement(utilsHtml.getBigTop(r.toString()), utilsHtml.getBigBottom());
					root.addLeave(topElement);
				} else {
					/*Checking for calls like checkTitle before any open and throw them inside special element "Calls without page"*/
					if (topElement == null) {
						topElement = new TreeElement(utilsHtml.getBigTop("Calls without Page"), utilsHtml.getBigBottom());
						topElement.addLeave(new TreeElement(utilsHtml.getSmallTop(r.toString()), utilsHtml.getSmallBottom()));
						root.addLeave(topElement);
					}else{
						topElement.addLeave(new TreeElement(utilsHtml.getSmallTop(r.toString()), utilsHtml.getSmallBottom()));
					}
				}
			}
			
			topElement = new TreeElement(utilsHtml.getBigTop("Statistics"), utilsHtml.getBigBottom());
			root.addLeave(topElement);
			topElement.addLeave(new TreeElement(utilsHtml.getBigTop("Total tests: " + totalReports), utilsHtml.getBigBottom()));
			topElement.addLeave(new TreeElement(utilsHtml.getBigTop("Passed/Failed/Skipped: " + positive + "/" + negative + "/" + skipped), utilsHtml.getBigBottom()));
			topElement.addLeave(new TreeElement(utilsHtml.getBigTop("Total time: " + GeneralUtils.getThreeDigitsTimeInSeconds(totalTime) + "s"), utilsHtml.getBigBottom()));
			topElement.addLeave(new TreeElement(utilsHtml.getBigTop("Average time: " + GeneralUtils.getThreeDigitsTimeInSeconds(totalTime/totalReports) + "s"), utilsHtml.getBigBottom()));
			topElement.addLeave(new TreeElement(utilsHtml.getBigTop("Average ping: " + GeneralUtils.getThreeDigitsTimeInSeconds(PingCommand.getAveragePing())+ "s"), utilsHtml.getBigBottom()));
			out.write(root.getString());
			out.close();
			
		} catch (IOException e) {
			System.out.println(
					"Cannot write to file! Check free disk space and possibility of reading/writing to that folder");
			System.exit(0);
		}
		
		
	}

}

