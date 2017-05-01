package models;

import java.util.Arrays;

public class MintermReduced extends Minterm
		implements IMintermReduced {
	private int coloumn;
	private int[] reducedDifferences;

	public MintermReduced(int bits, int value,
			int reducedDiff) {
		
		super(bits, value);
		
		this.coloumn = 1;
		this.reducedDifferences = new int[this.coloumn];
		this.reducedDifferences[this.coloumn
				- 1] = reducedDiff;
	}

	public MintermReduced(Minterm minterm,
			int reducedDiff) {
		
		this(minterm.getBits(), minterm.getValue(),
				reducedDiff);
		
		this.coloumn = 1;
		this.reducedDifferences = new int[this.coloumn];
		this.reducedDifferences[this.coloumn
				- 1] = reducedDiff;

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
		return isAdjacent(value) && Arrays.equals(reducedDiff, this.reducedDifferences);
	}

	@Override
	public MintermReduced reduce(MintermReduced term) {
		if (!isAdjacent(term.getValue(),
				term.getReducedDifferences())) {
			return null;
		}
		
		MintermReduced reduced = this.getValue() < term.getValue() ? this : term;
		int hammingDistance = this.getValue() ^ term.getValue();
		this.checked=true;
		term.checked=true;
		
		return new MintermReduced(reduced,hammingDistance);
	}
	
}
