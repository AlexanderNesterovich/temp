package by.training.DBChecker.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Class contains options for each column.
 */
public class ColumnInitializer {
	
	/**
	 * @return list of DBColumn objects.
	 */
	public static List<DBColumn> getList() {
		List<DBColumn> list = new ArrayList<DBColumn>();
		list.add(new DBColumn("u_id") {{
			setType("INT");
			setLength("11");
			setIndex("PRIMARY");
			setAutoInc(true);
		}});
		
		list.add(new DBColumn("u_login") {{
			setType("VARCHAR");
			setLength("255");
		}});
		
		list.add(new DBColumn("u_password") {{
			setType("CHAR");
			setLength("40");
		}});
		
		list.add(new DBColumn("u_email") {{
			setType("VARCHAR");
			setLength("255");
		}});
		
		list.add(new DBColumn("u_name") {{
			setType("VARCHAR");
			setLength("255");
		}});
		
		list.add(new DBColumn("u_remember") {{
			setType("CHAR");
			setLength("40");
		}});
		return list;
	}
	
}
