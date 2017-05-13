package tests;

import java.util.LinkedList;

import models.MintermReduced;
import models.Simplifier;

public class Test2 {

	public static void main(String[] args) {
		LinkedList<MintermReduced> answer = new LinkedList<>();
		answer.add(new MintermReduced(4, 0, 1));
		answer.add(new MintermReduced(4, 0, 8));
		answer.add(new MintermReduced(4, 1, 4));
		answer.add(new MintermReduced(4, 8, 2));
		answer.add(new MintermReduced(4, 5, 2));
		answer.add(new MintermReduced(4, 10, 4));
		answer.add(new MintermReduced(4, 7, 8));
		answer.add(new MintermReduced(4, 14, 1));
		Simplifier test = new Simplifier();
		int[] term = {0, 1, 5, 7, 8, 10, 14, 15};

		test.simplify(answer, 4, term);
	}

}
