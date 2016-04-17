package by.training.DBChecker.utils;

/**
 * Class data holder for info about column.
 */
public class DBColumn {
	
	private String name;
	private String type = "INT"; //Default value for type
	private String length = "";
	private boolean isNull = false;
	private boolean autoInc = false;
	private String DefaultType = "NONE"; //blank for Default Type
	private String Collation = "";
	private String Attributes = "";
	private String index = "---"; //blank for index
	private String comments = "";
	private String virtuality = "";
	
	public DBColumn(String name) {
		this.name = name;
	}

	public final String getName() {
		return name;
	}

	public final String getType() {
		return type;
	}

	public final void setType(String type) {
		this.type = type;
	}

	public final void setLength(String length) {
		this.length = length;
	}

	public final void setNull(boolean isNull) {
		this.isNull = isNull;
	}

	public final void setAutoInc(boolean autoInc) {
		this.autoInc = autoInc;
	}

	public final void setDefaultType(String defaultType) {
		DefaultType = defaultType;
	}

	public final void setCollation(String collation) {
		Collation = collation;
	}

	public final void setAttributes(String attributes) {
		Attributes = attributes;
	}

	public final void setIndex(String index) {
		this.index = index;
	}

	public final void setComments(String comments) {
		this.comments = comments;
	}

	public final void setVirtuality(String virtuality) {
		this.virtuality = virtuality;
	}

	public final String getLength() {
		return length;
	}

	public final boolean getIsNull() {
		return isNull;
	}

	public String getDefaultType() {
		return DefaultType;
	}

	public String getCollation() {
		return Collation;
	}

	public String getAttributes() {
		return Attributes;
	}

	public String getIndex() {
		return index;
	}

	public String getComments() {
		return comments;
	}

	public String getVirtuality() {
		return virtuality;
	}
	
	public boolean getAI() {
		return autoInc;
	}

}
