package controllers;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import models.Minterm;
import models.MintermReduced;

public class Minimizer implements IMinimizer {

	LinkedList<MintermReduced> answer;
	LinkedList<LinkedList<MintermReduced>[]> process;
	LinkedList<Minterm>[] firstTerms;

	int bits;

	public Minimizer(int bit) {
		this.process = new LinkedList<>();
		this.answer = new LinkedList<>();
		this.bits = bit;
		this.firstTerms = new LinkedList[bits + 1];
	}

	@Override
	public LinkedList<MintermReduced> minimize(int[] minterms) {

		PutMinterms(minterms);
		boolean finished = false;
		int k = 0;
		int i = 0;
		Reducefirst();

		while (!finished) {
			finished = true;
			while (i + 1 < process.get(k).length) {
				if (process.get(k)[i] != null && process.get(k)[i + 1] != null) {
					process.add(new LinkedList[bits + 1]);
					process.get(k + 1)[i] = new LinkedList<>();
					finished=reduceReduced(i, k, finished);
				
				if (process.get(k)[i] != null && process.get(k)[i + 1] == null) {
					for (int j = 0; j < process.get(k)[i].size(); j++) {
						addAnswer(process.get(k)[i].get(j));
					}
				}
				}
				i++;

			}
			k++;

		}

		PutLast(k);

		return answer;
	}
	
	
	
/**
 * set the minterms in the process.
 * @param minterms array of minterms.
 */
	public void PutMinterms(int[] minterms) {
		for (int i = 0; i < minterms.length; i++) {
			int index = Integer.bitCount(minterms[i]);
			if (firstTerms[index] == null) {
				firstTerms[index] = new LinkedList<>();
			}
			firstTerms[index].add(new Minterm(bits, minterms[i]));
		}

	}
/**
 * the first reduction of the minterms.
 */
	public void Reducefirst() {
		int i = 0;
		process.add(new LinkedList[bits + 1]);
		while (i + 1 < firstTerms.length) {
			if (firstTerms[i] != null && firstTerms[i + 1] != null) {
				process.get(0)[i] = new LinkedList<>();
				for (int j = 0; j < firstTerms[i].size(); j++) {
					for (int j2 = 0; j2 < firstTerms[i + 1].size(); j2++) {
						MintermReduced newterm = firstTerms[i].get(j).reduce(firstTerms[i + 1].get(j2));
						if (newterm != null) {
							process.get(0)[i].add(newterm);
						}
					}

					// if (!firstTerms[i].get(j).isChecked())
					// answer.add((MintermReduced)firstTerms[i].get(j));
				}
			}
			i++;
		}

	}
/**
 * put the last unchecked reduced minterms .
 * @param k the last array of linkedlist.
 */
	public void PutLast(int k) {

		for (int j = 0; j < process.get(k - 1).length; j++) {
			if (process.get(k - 1)[j] != null) {
				for (int j2 = 0; j2 < process.get(k - 1)[j].size(); j2++) {
					if (!answer.contains(process.get(k - 1)[j].get(j2)))
						answer.add(process.get(k - 1)[j].get(j2));
				}
			}
		}

	}
	/**
	 * 
	 * @param term that wanted to be added.
	 */
	public void addAnswer(MintermReduced term) {
		if (!term.isChecked() && !answer.contains(term))
			answer.add(term);

	}
	/**
	 * 
	 * @param i the minterms group index.
	 * @param k the number of process.
	 * @param finished reduced till the end.
	 * @return finished reducing or not yet 
	 */
	public boolean reduceReduced(int i,int k,boolean finished) {
		for (int j = 0; j < process.get(k)[i].size(); j++) {
			for (int j2 = 0; j2 < process.get(k)[i + 1].size(); j2++) {

				MintermReduced newterm = process.get(k)[i].get(j).reduce(process.get(k)[i + 1].get(j2));
				if (newterm != null) {
					process.get(k + 1)[i].add(newterm);
					finished = false;
				}
			}
			addAnswer(process.get(k)[i].get(j));

		}
		return finished;
	}
}
