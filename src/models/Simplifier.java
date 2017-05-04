package models;

import java.util.LinkedList;

public class Simplifier implements ISimplifier {
	boolean [][] checked;
	int [] minterms;
	LinkedList<MintermReduced> answer = new LinkedList<>();
	LinkedList<MintermReduced> terms = new LinkedList<>();

	@Override
	public LinkedList<MintermReduced> simplify(LinkedList<MintermReduced> firststep, int literals) {
		terms = firststep;
		return null;
	}
	public void getRowDominant(){
		
	}
	public void getColoumnDominant(){
		
	}
	public void fill2Darray(){
		
	}
	public void getEssential(){
		
	}
	public void getRemoved(){
		
	}
	private void remove(){
		
	}
	private void addToResult(){
		
	}
	

}
