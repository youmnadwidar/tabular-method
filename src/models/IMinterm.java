package models;

public interface IMinterm {
	/**
	 * Reduce two minterms together
	 * @param term to reduce
	 * @return new reduced term
	 */
	public Minterm reduce(Minterm term);
	
	
}
