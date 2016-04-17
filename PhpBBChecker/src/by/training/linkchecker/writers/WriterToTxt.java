package by.training.linkchecker.writers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import by.training.linkchecker.commands.PingCommand;
import by.training.linkchecker.model.Report;
import by.training.linkchecker.utils.GeneralUtils;

/**
 * Writer for proper writing to txt files.
 */
public class WriterToTxt extends AbstractWriter {

	/**
	 * @param path String as path of object.
	 */
	@Override
	public void writeReports(String path) {

		/*Creating file with {nameOforiginalFile}_log.txt name*/
		File file = new File(path);
		String filename = file.getName().split("\\.")[0];
		File name = new File(file.getParentFile() + filename + "_log.txt");

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
			
			totalReports = reports.size();
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

				if (r.getKeyword().equals("open")) {
					out.write(r.toString() + System.lineSeparator());
				} else {
					out.write(" " + r.toString() + System.lineSeparator());
				}
			}
			out.write(System.lineSeparator());
			out.write("Total tests: " + totalReports + System.lineSeparator());
			out.write("Passed/Failed/Skipped: " + positive + "/" + negative + "/" + skipped + System.lineSeparator());
			out.write("Total time: " + GeneralUtils.getThreeDigitsTimeInSeconds(totalTime) + "s" + System.lineSeparator());
			out.write("Average time: " + GeneralUtils.getThreeDigitsTimeInSeconds(totalTime/totalReports)+ "s" + System.lineSeparator());
			out.write("Average ping: " + GeneralUtils.getThreeDigitsTimeInSeconds(PingCommand.getAveragePing())+ "s" + System.lineSeparator());
			out.close();
		} catch (IOException e) {
			System.out.println(
					"Cannot write to file! Check free disk space and possibility of reading/writing to that folder");
			System.exit(0);
		}

	}

}
