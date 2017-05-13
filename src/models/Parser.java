package models;

import java.util.LinkedList;

public class Parser {
	public int[] parse(String text) {
		text = text.trim();
		if (text.length() == 0) {
			return new int[0];
		}
		String[] elements = text.split(",");
		int[] terms = new int[elements.length];
		for (int i = 0; i < terms.length; i++) {
			terms[i] = Integer.parseInt(elements[i]);
		}
		return terms;
	}
	public void printArray(Object[] array) {
		for (Object i : array) {
			System.out.println(i);
		}
	}
	public String parse(LinkedList<MintermReduced> terms) {
		StringBuilder sb = new StringBuilder();
		for (MintermReduced term : terms) {
			sb.append(term.getValue() + "(");
			for (int i = 0; i < term
					.getReducedDifferences().length; i++) {
				int element = term
						.getReducedDifferences()[i];
				if (i == term.getReducedDifferences().length
						- 1) {
					sb.append(element);
				} else {
					sb.append(element + ",");
				}
			}
			sb.append(") + ");
		}
		return sb.toString();
	}

	public String parse(LinkedList<LinkedList<MintermReduced> > terms,boolean isFinal) {
		StringBuilder sb = new StringBuilder();
		for ( LinkedList<MintermReduced> term: terms ) {
			sb.append(parse(term) + '\n');
		}
		return sb.toString();
	}
}
