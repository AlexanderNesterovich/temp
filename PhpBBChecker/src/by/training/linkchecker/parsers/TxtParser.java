package by.training.linkchecker.parsers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import by.training.linkchecker.model.Action;

/**
 * Txt parser object which parse txt files line by line and forms list of Action objects.
 */
public class TxtParser implements Parser{

	List<Action> commandList = new ArrayList<Action>();

	/**
	 * Parse txt file line by line.
	 * @param path String with path to txt.
	 */
	public void parseByPath(String path) {
		
		FileInputStream fis;
		BufferedReader br;
		String line = null;
		
		try {
			File file = new File(path);
			fis = new FileInputStream(file);
			br = new BufferedReader(new InputStreamReader(fis, "UTF8"));
			while ((line = br.readLine()) != null) {
				if(!line.isEmpty()) {
					parseLine(line);
				}
			}
			br.close();
		} catch (UnsupportedEncodingException e) {
			System.err.println("Unsupported coding. UTF-8 required");
		}
		catch (FileNotFoundException e) {
			System.err.println("File Not Found!");
		} catch (IOException e) {
			System.err.println("Can't read from the source file");
		}

	}

	/**
	 * Parse line and form Action object.
	 * @param line String representing one line of file.
	 */
	private void parseLine(String line) {
		
		List<String> list = new ArrayList<String>();
		/*Pattern for dividing string for words with spaces or quoted sentences*/
		Matcher m = Pattern.compile("(['\"])((?:\\\\\\1|.)+?)\\1|([^\\s\"']+)").matcher(line);
		while (m.find()) {
			/*Sanitize string for blank chars like \uFEFF*/
			String temp = m.group(0).replaceAll("[\uFEFF-\uFFFF]", ""); 
			
			/*escape quotes by -QUOT- for difficult queries*/
			temp = temp.replaceAll("-QUOT-", "\"");
			/*Delete quotes by sides*/
			if (temp.length() >= 2 
					&& temp.charAt(0) == '"' 
					&& temp.charAt(temp.length()-1) == '"') {
				list.add(temp.substring(1, temp.length() - 1));
				continue;
			}
			
			
				
			list.add(temp);
		}
		    
		/*If arguments > 1 create with line */
		if (list.size() > 1)
		{
			Action tmp = new Action(list.toArray(new String[0]), line);
			commandList.add(tmp);
			
		}else{
			Action tmp = new Action(line);
			commandList.add(tmp);
		}
		
	}

	public List<Action> getCommandsList() {

		return commandList;

	}

}


