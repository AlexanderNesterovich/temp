package by.training.mp3lister.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Class holder for mp3 files data. Automated sorting and unique names for data,
 * thanks java.util.Map.
 */
public class Tree {

	static private Map<String, Map<String, Map<String, List<String>>>> artists = new TreeMap<String, Map<String, Map<String, List<String>>>>();
	private Map<String, Map<String, List<String>>> albums;
	private Map<String, List<String>> songs;

	/**
	 * Automated map manager, if we already have {artistname} drop album into existing artist , if we already have {albumname} drop songs into existing album.
	 * 
	 * @param artist artist name.
	 * @param albumName album name.
	 * @param name name of song.
	 * @param duration duration of song.
	 * @param path file path.
	 */
	public void addElement(String artist, String albumName, String name, String duration, String path) {

		if (artists.containsKey(artist)) {

			albums = artists.get(artist);

			if (albums.containsKey(albumName)) {

				songs = albums.get(albumName);

				if (!songs.containsKey(name)) {
					songs.put(name, new ArrayList<String>(Arrays.asList(duration, path)));
				}

			} else {
				albums.put(albumName, new TreeMap<String, List<String>>() {
					private static final long serialVersionUID = 1618980974036590126L;

					{
						put(name, new ArrayList<String>(Arrays.asList(duration, path)));
					}
				});
			}
		} else {
			artists.put(artist, new TreeMap<String, Map<String, List<String>>>() {
				private static final long serialVersionUID = 8528495521279301216L;

				{
					put(albumName, new TreeMap<String, List<String>>() {
						private static final long serialVersionUID = 7930946819883022627L;

						{
							put(name, new ArrayList<String>(Arrays.asList(duration, path)));
						}
					});
				}
			});
		}

	}
	
	/**
	 * Simple return.
	 * @return return root Map with all artists, albums and songs.
	 */
	static public Map<String, Map<String, Map<String, List<String>>>> getTree() {
		return artists;
	}

}
