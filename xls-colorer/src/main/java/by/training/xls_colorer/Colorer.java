package by.training.xls_colorer;

import java.io.File;
import java.io.IOException;

import jxl.CellType;
import jxl.Workbook;
import jxl.format.CellFormat;
import jxl.format.Colour;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class Colorer {
	
	Workbook workBookIn;
	WritableWorkbook workBookOut;
	Colour colorNumbers;
	Colour colorStrings;
	Colour colorMix;
	
	public Colorer(String inputFile, String outputFile) throws BiffException, IOException {
			workBookIn = Workbook.getWorkbook(new File(inputFile));
			workBookOut = Workbook.createWorkbook(new File(outputFile), workBookIn);
	}
	
	public void setColor(Colour colorNumbers, Colour colorStrings, Colour colorMix) throws WriteException, IOException{
		
		this.colorNumbers = colorNumbers;
		this.colorStrings = colorStrings;
		this.colorMix = colorMix;
		
		for(int l = 0; l < workBookOut.getNumberOfSheets(); l++) {
			
			WritableSheet tmp = workBookOut.getSheet(l);
			System.out.println("Number of sheet: " + l);
			
			for(int i = 0; i < tmp.getRows(); i++) {
				
				for(int k = 0; k < tmp.getColumns(); k++) {
					
					System.out.println("Number of column/row: " + k + "/" + i);
					WritableCell cell = tmp.getWritableCell(k, i);
					CellFormat format = cell.getCellFormat();

					if (cell.getType() == CellType.NUMBER) {
						WritableFont formatFont = new WritableFont(format.getFont());
						formatFont.setColour(colorNumbers);
						WritableCellFormat newFormat = new WritableCellFormat(format);
						newFormat.setFont(formatFont);
						Label number = new Label(k, i, cell.getContents(), newFormat);
						tmp.addCell(number);
					}
					
					if(cell.getType() == CellType.LABEL) {
						WritableFont formatFont = new WritableFont(format.getFont());
						formatFont.setColour(colorStrings);
						WritableCellFormat newFormat = new WritableCellFormat(format);
						newFormat.setFont(formatFont);
						Label label = new Label(k, i, cell.getContents(), newFormat);
						tmp.addCell(label);
					}
					
					String content = cell.getContents();
					
					if (content.matches("^(\\d+\\p{L}+|\\p{L}+\\d+)+$")) {
						WritableFont formatFont = new WritableFont(format.getFont());
						WritableCellFormat newFormat = new WritableCellFormat(format);
						newFormat.setBackground(colorMix);
						Label label = new Label(k, i, cell.getContents(), newFormat);
						tmp.addCell(label);
					}
					
					System.out.println("content: " + content);
					
				}
			}
		}
		
		workBookOut.write(); 
		workBookOut.close();
		workBookIn.close();		
	}
	
}
