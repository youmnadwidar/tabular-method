package models;

import java.util.Arrays;

public class MintermReduced extends Minterm
		implements IMintermReduced {
	private int coloumn;
	private int[] reducedDifferences;

	public MintermReduced(int bits, int value,
			int reducedDiff) {

		super(bits, value);
		if (reducedDiff == -1) {
			this.coloumn = 0;
			this.reducedDifferences = new int[0];

		} else {
			this.coloumn = 1;
			this.reducedDifferences = new int[this.coloumn];
			this.reducedDifferences[this.coloumn
					- 1] = reducedDiff;
		}
	}

	public MintermReduced(Minterm minterm,
			int reducedDiff) {

		this(minterm.getBits(), minterm.getValue(),
				reducedDiff);

	}

	public MintermReduced(MintermReduced minterm,
			int reducedDiff) {
		this(minterm.getBits(), minterm.getValue(),
				reducedDiff);

		this.coloumn = minterm.getColoumn() + 1;
		this.reducedDifferences = new int[this.coloumn];

		System.arraycopy(minterm.getReducedDifferences(), 0,
				this.reducedDifferences, 0,
				this.coloumn - 1);

		this.reducedDifferences[this.coloumn
				- 1] = reducedDiff;

		Arrays.sort(this.reducedDifferences);
	}

	public int[] getReducedDifferences() {
		return this.reducedDifferences;
	}

	public int getColoumn() {
		return this.coloumn;
	}

	private boolean isAdjacent(int value,
			int[] reducedDiff) {
		return isAdjacent(value) && Arrays.equals(
				reducedDiff, this.reducedDifferences);
	}

	@Override
	public MintermReduced reduce(MintermReduced term) {
		if (!isAdjacent(term.getValue(),
				term.getReducedDifferences())) {
			return null;
		}

		MintermReduced reduced = this.getValue() < term
				.getValue() ? this : term;
		int hammingDistance = this.getValue()
				^ term.getValue();
		this.checked = true;
		term.checked = true;

		return new MintermReduced(reduced, hammingDistance);
	}

	@Override
	public boolean equals(Object term) {

		return (this.getValue() == ((MintermReduced) term)
				.getValue()
				&& Arrays.equals(reducedDifferences,
						((MintermReduced) term).reducedDifferences));
	}

	public String toString() {
		return alphabetString();
	}
	
	public String toString(boolean isAlphabet){
		if(isAlphabet){
			return alphabetString();
		}
		return super.toString(false)+"("+printDiff()+")";
	}

	private String printDiff() {
		StringBuilder ommitted = new StringBuilder();
		for (int i = 0; i < this.reducedDifferences.length; i++) {
			if (i == getReducedDifferences().length - 1) {
				ommitted.append(reducedDifferences[i]);
				continue;
			}
			ommitted.append(reducedDifferences[i]+",");
		}
		return ommitted.toString();
	}
	
	protected String alphabetString() {
		StringBuilder sb = new StringBuilder();
		for(int i =0 ; i<this.bits;i++){
			if(isOmitted(i)){
				continue;
			}
			int literal = 'A'+i;
			sb.append(Character.toChars(literal));
			if(isNegated(i)){
				sb.append("'");
			}
		}
		return sb.length() == 0 ? "1" : sb.toString();
	}
	
	private boolean isOmitted(int index){
		for(int i = 0 ; i<this.reducedDifferences.length;i++){
			
			if( (int) Math.pow(2, this.bits-index-1)  ==  reducedDifferences[i])
			{
				return true;
			}
		}
		
		return false;
	}

}
