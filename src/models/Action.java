package models;

public class Action {
	String term1;
	String term2;
	String result;
	String action;

	public Action(String term1, String term2, String result,
			String action) {
		this.term1 = term1;
		this.term2 = term2;
		this.result = result;
		this.action = action;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (term1 != "") {
			sb.append(term1 + " ");
		}
		if (action != "") {
			sb.append(action + " ");
		}
		if (term2 != "") {
			sb.append(term2 + " ");
		}

		if (result != "") {
			sb.append(" Result is " + result);
		}
		return sb.toString();
	}
}
