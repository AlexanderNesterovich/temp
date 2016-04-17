package by.training.linkchecker.writers;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

import by.training.linkchecker.model.Report;
import by.training.linkchecker.utils.GeneralUtils;
import jxl.Workbook;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * Writer for excel files
 */
public class WriterToXls extends AbstractWriter {
	private WritableWorkbook workBookOut;
	private Set<Integer> sheets = new TreeSet<>();
	private int sheetNumber = 0;
	private int columnNumber = 0;
	private int rowNumber = 0;
	Label originalContent;
	Label time;
	Label message;
	
	@Override
	public void writeReports(String path) {
		File file = new File(path);
		String filename = file.getName().split("\\.")[0];
		File name = new File(file.getParentFile() + filename + "_log.xls");
		/* Creating file with {nameOforiginalFile}_log.txt name */
		try {
			workBookOut = Workbook.createWorkbook(name);
			workBookOut.createSheet("from file input", 0);
			workBookOut.createSheet("startup input", 1);
			for (Report r : reports) {
				writeXls(r);
			}
			workBookOut.write();
			workBookOut.close();
		} catch (IOException | WriteException e) {
			System.out.println("Cant write file: Please check rights and acces to that file");
			System.exit(0);
		}
	}
	
	/**
	 * @param r Report for writing
	 * @throws IOException exception from jxl handled higher
	 */
	private void writeXls(Report r) throws IOException {
		
		WritableSheet currentSheet;
		
		if (r.getCoords() != null) {
			//get coords of cell
			
			sheetNumber = r.getCoords()[0];
			columnNumber = r.getCoords()[1];
			rowNumber = r.getCoords()[2];
			if(sheetNumber > 1) {
				sheetNumber = 0;
			}
			currentSheet = workBookOut.getSheet(sheetNumber);

		}else{
			currentSheet = workBookOut.getSheet(sheetNumber);
		}
		
		try {
			//create new font
		    WritableFont times12ptBold = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
			WritableCellFormat newFormat = new WritableCellFormat(times12ptBold);
			//fill with green or red for positive and negative tests results.
			if(r.getState()) {
				newFormat.setBackground(Colour.LIGHT_GREEN);
			}else{
				newFormat.setBackground(Colour.CORAL);
			}
			//creating row
			originalContent = new Label(columnNumber, rowNumber, r.getOriginal(), newFormat);
			currentSheet.addCell(originalContent);
			time = new Label(columnNumber + 1, rowNumber, GeneralUtils.getThreeDigitsTimeInSeconds(r.getTime()), newFormat);
			currentSheet.addCell(time);
			message = new Label(columnNumber + 2, rowNumber, r.getMessage(), newFormat);
			currentSheet.addCell(message);
			rowNumber = rowNumber + 1;
		} catch (RowsExceededException e) {
			System.out.println("Rows Exceeded. Too much info!");
		} catch (WriteException e) {
			System.out.println("Cant write into that cell");
		}
	}
}
