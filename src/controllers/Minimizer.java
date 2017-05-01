package controllers;

import java.util.LinkedList;

import models.Minterm;
import models.MintermReduced;

public class Minimizer implements IMinimizer {

	@Override
	public LinkedList<MintermReduced> minimize(int[] minterms, int bits) {
		LinkedList<MintermReduced> answer = new LinkedList<>();
		LinkedList<LinkedList<MintermReduced>[]> process = new LinkedList<>();
		LinkedList<Minterm>[] firstTerms = new LinkedList[bits];
		for (int i = 0; i < minterms.length; i++) {
			int index = Integer.bitCount(minterms[i]);
			if (firstTerms[index] == null) {
				firstTerms[index] = new LinkedList<>();
			}
			firstTerms[index].add(new Minterm(bits, minterms[i]));
		}
		boolean finished = false;
		int k = 0;
		int i = 0;
		process.add(new LinkedList[bits]);
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

		i = 0;
		while (!finished) {
			finished = true;
			while (i + 1 < process.get(k).length) {
				if (process.get(k)[i] != null && process.get(k)[i + 1] != null) {
					process.add(new LinkedList[bits]);
					process.get(k + 1)[i] = new LinkedList<>();
					for (int j = 0; j < process.get(k)[i].size(); j++) {
						for (int j2 = 0; j2 < process.get(k)[i + 1].size(); j2++) {

							MintermReduced newterm = process.get(k)[i].get(j).reduce(process.get(k)[i + 1].get(j2));
							if (newterm != null) {
								process.get(k + 1)[i].add(newterm);
								finished = false;
							}
						}
						if (!process.get(k)[i].get(j).isChecked() && !answer.contains(process.get(k)[i].get(j)))
							answer.add(process.get(k)[i].get(j));

					}
				}
				i++;

			}
			k++;

		}
		for (int j = 0; j < process.getLast().length; j++) {
			if (process.getLast()[j] != null) {
				for (int j2 = 0; j2 < process.getLast()[j].size(); j2++) {
if (!answer.contains(process.getLast()[j].get(j2)))
						answer.add(process.getLast()[j].get(j2));
				}
			}
		}
		return answer;
	}
}