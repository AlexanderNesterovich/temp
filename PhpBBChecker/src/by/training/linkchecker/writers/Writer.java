package by.training.linkchecker.writers;

import by.training.linkchecker.model.Report;

/**
 * Interface for general methods for writers and polymorphic code.
 */
public interface Writer {
	
	public void addReport(Report r);
	public void writeReports(String path);
	public void printReports();

}
