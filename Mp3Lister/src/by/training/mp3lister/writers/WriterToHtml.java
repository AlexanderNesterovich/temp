package by.training.mp3lister.writers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;

import by.training.mp3lister.utils.HtmlUtils;
import by.training.mp3lister.utils.Tree;

/**
 * Writer for proper writing to HTML files.
 */
public class WriterToHtml {
	
    /**
     * Write HTML into this folder.
     * @param path for writing.
     */
	public void writeReport(String path) {
		//create new file
		String fname= path+File.separator+"Mp3Lister_log.html";
		    File f = new File(path);
		    File f1 = new File(fname);
		    f.mkdirs();
		try {
			//check if exists
			if (!f1.createNewFile()) {
				System.out.println("Log already exists!");
				System.exit(0);
			}
			//check for other problems
		} catch (IOException e) {
			System.out.println(
					"Cannot create file! Check free disk space and possibility of creating files by this path");
			System.exit(0);
		}
		//write to file getting tree with all data.
		try {
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f1), "UTF8"));
			out.write(writeTree(Tree.getTree()));
			out.close();
			System.out.println("Log: " + f1);
		} catch (IOException e) {
			System.out.println(
					"Cannot write file! Check free disk space and possibility of reading/writing in this folder");
			System.exit(0);
		}
		
		
	}
	
    /**
     * Generate big String with final HTML.
     * @param tree tree with data.
     * @return big string with html.
     */
	private String writeTree(Map<String, Map<String, Map<String, List<String>>>> tree) {
		
		HtmlUtils utils = new HtmlUtils();
		StringBuffer htmlFile = new StringBuffer();
		
		htmlFile.append(utils.printTopHtml());
		
		//big for for for cycle for getting into deepest maps.
		for (Map.Entry<String, Map<String, Map<String, List<String>>>> entryArtist : tree.entrySet()) {
			htmlFile.append(utils.printTopFolder(entryArtist.getKey()));
			for (Map.Entry<String, Map<String, List<String>>> entryAlbum : entryArtist.getValue().entrySet()) {
				htmlFile.append(utils.printTopSubFolder(entryAlbum.getKey()));
			    for(Map.Entry<String, List<String>> entrySong : entryAlbum.getValue().entrySet()) {
			    	htmlFile.append(utils.printRow(entrySong.getKey(), entrySong.getValue().get(0), entrySong.getValue().get(1)));
			    }
			    htmlFile.append(utils.printBottomSubFolder());
			}
			htmlFile.append(utils.printBottomFolder());
		}
		htmlFile.append(utils.printBottomHtml());
		return htmlFile.toString();
	}

}

