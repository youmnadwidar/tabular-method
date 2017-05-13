package models;

public interface IParser {
	/**
	 * transform minterms form decimal form to written literals
	 * @param minterms array of integers of minterms to transform
	 * @return array of strings of equation
	 */
	public String[] mintermToSop(int[] minterms);

	/**
	 * transform minterms form written literal to decimal form 
	 * @param sop array of strings of written literal form
	 * @return array of integers of minterms transformed
	 */
	public int[] sopToMinterm(String[] sop);
}
