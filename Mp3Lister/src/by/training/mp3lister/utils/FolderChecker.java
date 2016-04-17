package by.training.mp3lister.utils;

import java.io.File;

import java.io.IOException;
import java.time.LocalTime;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
/**
 * Class for checking folder for mp3 files.
 */
public class FolderChecker {

	private Tree tree = new Tree();
	
	
	/**
	 * @param path path of folder
	 */
	public void checkFolder(String path) {
		
		File rootFile = new File(path);

		//Checking for bad folder
		if (!rootFile.exists() || !rootFile.isDirectory()) {
			System.out.println("Folder doesnt exist!");
			return;
		}
		//Simple recursion for checking every folder
		File listOfFiles[] = rootFile.listFiles();
		for (File file : listOfFiles) {
			if (file == null) {
				return;
			}
			
			if (file.isHidden() || !file.canRead())
				continue;
			if (file.isDirectory())
				checkFolder(file.getPath());
			else if (file.getName().endsWith(".mp3")) {
				try {
					System.out.println("Checking: " + file.getAbsolutePath());
					Mp3File mp3file = new Mp3File(file);
					//parse tags and adds into our tree.
					if (mp3file.hasId3v2Tag()) {
						ID3v2 id3v2Tag = mp3file.getId3v2Tag();
						tree.addElement(
								emptyChecker(id3v2Tag.getArtist()), 
								emptyChecker(id3v2Tag.getAlbum()),
								emptyChecker(id3v2Tag.getTitle()),
								LocalTime.MIN.plusSeconds(mp3file.getLengthInSeconds()).toString(),
								file.getAbsolutePath());
					} else if (mp3file.hasId3v1Tag()) {
						ID3v1 id3v1Tag = mp3file.getId3v1Tag();
						
						tree.addElement(
								emptyChecker(id3v1Tag.getArtist()), 
								emptyChecker(id3v1Tag.getAlbum()),
								emptyChecker(id3v1Tag.getTitle()),
								LocalTime.MIN.plusSeconds(mp3file.getLengthInSeconds()).toString(),
								file.getAbsolutePath());
					} else {
						tree.addElement(
								"(WITHOUT TAG)", 
								file.getParent(), 
								mp3file.getFilename(),
								LocalTime.MIN.plusSeconds(mp3file.getLengthInSeconds()).toString(),
								file.getAbsolutePath());
					}
					//handling exceptions without interruption
				} catch (UnsupportedTagException e) {
					System.out.println("Unsupported TAG: " + file.getAbsolutePath());
				} catch (InvalidDataException e) {
					System.out.println("Invalid Data: " + file.getAbsolutePath());
				} catch (IOException e) {
					System.out.println("IO Exception: " + file.getAbsolutePath());
				}
			}

		}
	}

	/**
	 * @param attribute string with original tag.
	 * @return if blank return placeholder tag.
	 */
	private String emptyChecker(String attribute) {
		if (attribute == null || attribute.isEmpty()) {
			return "(BLANK TAG)";
		} else {
			return attribute;
		}
	}
}
