package by.training.DBChecker.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Class contains options for each row.
 */
public class RowsInitializer {

	/**
	 * @return list of DBRows.
	 */
	public static List<DBRow> getList() {
		List<DBRow> list = new ArrayList<DBRow>();
		list.add(new DBRow() {{
			setId("1");
			setLogin("user1");
			setPassword("e38ad214943daad1d64c102faec29de4afe9da3d");
			setEmail("user1@mail.com");
			setName("Pupkin");
		}});
		
		list.add(new DBRow() {{
			setId("2");
			setLogin("user2");
			setPassword("2aa60a8ff7fcd473d321e0146afd9e26df395147");
			setEmail("user2@mail.com");
			setName("Smith");
		}});
		
		return list;
	}
	
}
