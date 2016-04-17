package by.training.linkchecker.parsers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import by.training.linkchecker.model.Action;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class XlsParser implements Parser {

	List<Action> commandList = new ArrayList<Action>();
	Workbook workBookIn;
	FileInputStream fis;

	/* (non-Javadoc)
	 * @see by.training.linkchecker.parsers.Parser#parseByPath(java.lang.String)
	 */
	@Override
	public void parseByPath(String path) {
		try {
			workBookIn = Workbook.getWorkbook(new File(path));
			for (int l = 0; l < workBookIn.getNumberOfSheets(); l++) {

				Sheet tmp = workBookIn.getSheet(l);
				//System.out.println("Number of sheet: " + l);

				for (int i = 0; i < tmp.getRows(); i++) {

					for (int k = 0; k < tmp.getColumns(); k++) {

						//System.out.println("Number of column/row: " + k + "/" + i);
						Cell cell = tmp.getCell(k, i);
						String content = cell.getContents();
						if (!content.isEmpty()) {
							parseLine(content, new int[] {l,k,i});
						}
					}
				}
			}

			workBookIn.close();

		} catch (BiffException e) {
			System.err.println("Cant read xls file!");
		} catch (IOException e) {
			System.err.println("Input/Output Exception. Check path and rights in your folder");
		}

	}

	/**
	 * @param line parse each cell
	 * @param coordinates coordinates of cell
	 */
	private void parseLine(String line, int[] coordinates) {

		List<String> list = new ArrayList<String>();
		/*
		 * Pattern for dividing string for words with spaces or quoted sentences
		 */
		Matcher m = Pattern.compile("(['\"])((?:\\\\\\1|.)+?)\\1|([^\\s\"']+)").matcher(line);
		while (m.find()) {
			/* Sanitize string for blank chars like \uFEFF */
			String temp = m.group(0).replaceAll("[\uFEFF-\uFFFF]", "");

			/* escape quotes by -QUOT- for difficult queries */
			temp = temp.replaceAll("-QUOT-", "\"");
			/* Delete quotes by sides */
			if (temp.length() >= 2 && temp.charAt(0) == '"' && temp.charAt(temp.length() - 1) == '"') {
				list.add(temp.substring(1, temp.length() - 1));
				continue;
			}

			list.add(temp);
		}

		/* If arguments > 1 create with line */
		if (list.size() > 1) {
			Action tmp = new Action(list.toArray(new String[0]), line, coordinates);
			commandList.add(tmp);

		} else {
			Action tmp = new Action(line);
			commandList.add(tmp);
		}

	}

	@Override
	public List<Action> getCommandsList() {
		return commandList;
	}

}
