package models;

import java.util.LinkedList;

public interface ISimplifier {
	/**
	 * simplify a reduced SOP form, to remove redundant terms
	 * @param terms list of reduced term to simplify
	 * @param literals number of maximum literals of a term
	 * @return a list of the terms in the most simplified form
	 */
	public LinkedList<MintermReduced> simplify(LinkedList<MintermReduced> firststep,int literals);
	
	
}
