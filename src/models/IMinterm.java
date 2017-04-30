package models;

public interface IMinterm {
	/**
	 * Reduce two minterms together
	 * @param term to reduce
	 * @return new reduced term
	 */
	public MintermReduced reduce(Minterm term);

	/**
	 * check if a minterm is checked
	 * @return boolean value checked
	 */
	public boolean isChecked();

}
