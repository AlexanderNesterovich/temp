package by.training.linkchecker.writers;

import java.util.ArrayList;
import java.util.List;

import by.training.linkchecker.commands.PingCommand;
import by.training.linkchecker.model.Report;
import by.training.linkchecker.utils.GeneralUtils;

/**
 * Abstract writer class that can work with Reports objects with one abstract method which must be implemented for different type of files.
 */
abstract public class AbstractWriter implements Writer {

	protected List<Report> reports = new ArrayList<Report>();
	protected long skipped = 0L;
	protected long positive = 0L;
	protected long negative = 0L;
	protected long totalTime = 0L;
	protected long totalReports;

	/**
	 * Adding report to reports list.
	 * @param r Report.
	 */
	public void addReport(Report r) {
		reports.add(r);
	}

	/**
	 * Getting list of reports.
	 * @return list of reports.
	 */
	public List<Report> getReports() {
		return reports;
	}

	/**
	 * Prints reports to console.
	 */
	public void printReports() {
		totalReports = reports.size();
		for (Report r : reports) {
			
			totalTime = totalTime + r.getTime();
			
			if (r.getMessage().equals("(skipped)")) {
				skipped = skipped + 1;
				continue;
			}
			
			if (r.getState() == true) {
				positive = positive + 1;
			}else{
				negative = negative + 1;
			}
			
			if (r.getKeyword().equals("open")) {
				System.out.println(r.toString());
			} else {
				System.out.println(" " + r.toString());
			}
		}
		if(totalReports == 0) {
			System.err.println("Program have not found any commands!");
			System.exit(0);
		}
		System.out.println();
		System.out.println("Total tests: " + totalReports);
		System.out.println("Passed/Failed/Skipped: " + positive + "/" + negative + "/" + skipped);
		System.out.println("Total time: " + GeneralUtils.getThreeDigitsTimeInSeconds(totalTime) + "s");
		System.out.println("Average time: " + GeneralUtils.getThreeDigitsTimeInSeconds(totalTime/totalReports) + "s");
		System.out.println("Average ping: " + GeneralUtils.getThreeDigitsTimeInSeconds(PingCommand.getAveragePing()) + "s");


	}

	abstract public void writeReports(String path);

}
