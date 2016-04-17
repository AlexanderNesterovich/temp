package by.training.linkchecker.utils;

import java.util.LinkedList;
import java.util.List;

/**
 * Simple tree class for comfortable html printing or other tree structures.
 */
public class TreeElement {
	private List<TreeElement> leaves = new LinkedList<TreeElement>();
	private String firstOut;
	private String secondOut;

	public TreeElement(String first, String second) {
		this.firstOut = first;
		this.secondOut = second;
	}
	
	public void addLeave(TreeElement element) {
		leaves.add(element);
	}

	public String getString() {
		StringBuilder result = new StringBuilder();
		result.append(firstOut);
		for(TreeElement leave: leaves) {
			result.append(leave.getString());
		}
		result.append(secondOut);
		return result.toString();
	}
}
