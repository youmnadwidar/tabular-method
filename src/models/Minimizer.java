package models;


import java.util.LinkedList;

import controllers.QMM;

public class Minimizer implements IMinimizer {

	LinkedList<MintermReduced> answer;
	LinkedList<LinkedList<MintermReduced>[]> process;
	LinkedList<Minterm>[] firstTerms;
	int bits;
	QMM qm;

	@SuppressWarnings("unchecked")
	public Minimizer(int bit) {
		this.process = new LinkedList<>();
		this.answer = new LinkedList<>();
		this.bits = bit;
		this.firstTerms = new LinkedList[bits + 1];
	}

	@SuppressWarnings("unchecked")
	@Override
	public LinkedList<MintermReduced> minimize(
			int[] minterms) {

		PutMinterms(minterms);
		boolean finished = false;
		int k = 0;
		int i = 0;
		Reducefirst();

		while (!finished) {
			finished = true;
			i = 0;
			while (i + 1 < process.get(k).length) {
				if (process.get(k)[i] != null
						&& process.get(k)[i + 1] != null) {
					if (process.size() < k + 2) {
						process.add(
								new LinkedList[bits + 1]);
					}
					process.get(
							k + 1)[i] = new LinkedList<>();
					boolean result = reduceReduced(i, k);
					
					finished = finished && result;
					
					if (process.get(k)[i] != null && process
							.get(k)[i + 1] == null) {
						for (int j = 0; j < process
								.get(k)[i].size(); j++) {

							if (!process.get(k)[i].get(j)
									.isChecked()) {
								addAnswer(process.get(k)[i]
										.get(j));
								qm.addStep(new Action(
										process.get(k)[i]
												.get(j).toString(),
										"", "",
										" is not checked, added to answer."));
							}
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
			firstTerms[index]
					.add(new Minterm(bits, minterms[i]));
		}

	}

	/**
	 * the first reduction of the minterms.
	 */
	@SuppressWarnings("unchecked")
	public void Reducefirst() {
		qm.setCurrentProccess(
				"Step 2: Minimizing first Coloumn.");
		int i = 0;
		process.add(new LinkedList[bits + 1]);
		while (i + 1 < firstTerms.length) {
			if (firstTerms[i] != null
					&& firstTerms[i + 1] != null) {
				process.get(0)[i] = new LinkedList<>();
				for (int j = 0; j < firstTerms[i]
						.size(); j++) {
					for (int j2 = 0; j2 < firstTerms[i + 1]
							.size(); j2++) {
						MintermReduced newterm = firstTerms[i]
								.get(j)
								.reduce(firstTerms[i + 1]
										.get(j2));
						if (newterm != null) {
							process.get(0)[i].add(newterm);
							qm.addStep(new Action(
									firstTerms[i].get(j)
											.toString(),
									firstTerms[i + 1]
											.get(j2)
											.toString(),
									newterm.toString(),
									"is reduced with"));
						}
					}

					if (!firstTerms[i].get(j).isChecked())
						answer.add(
								new MintermReduced(bits,
										firstTerms[i].get(j)
												.getValue(),
										-1));

				}
			}
			if (firstTerms[i] != null
					&& firstTerms[i + 1] == null) {
				for (int j = 0; j < firstTerms[i]
						.size(); j++) {
					if (!firstTerms[i].get(j).isChecked())
						addAnswer(
								new MintermReduced(bits,
										firstTerms[i].get(j)
												.getValue(),
										-1));
					qm.addStep(new Action(
							firstTerms[i].get(j).toString(),
							"", "",
							"is not checked, added to answer."));
				}
			}
			i++;
		}
		if (i == firstTerms.length - 1
				&& firstTerms[i] != null
				&& !firstTerms[i].get(0).isChecked())
			addAnswer(new MintermReduced(bits,
					firstTerms[i].get(0).getValue(), -1));

	}

	/**
	 * put the last unchecked reduced minterms .
	 * @param k the last array of linkedlist.
	 */
	public void PutLast(int k) {

		for (int j = 0; j < process
				.get(k - 1).length; j++) {
			if (process.get(k - 1)[j] != null) {
				for (int j2 = 0; j2 < process.get(k - 1)[j]
						.size(); j2++) {
					if (!answer.contains(
							process.get(k - 1)[j].get(j2)))
						answer.add(process.get(k - 1)[j]
								.get(j2));
				}
			}
		}

	}

	/**
	 * @param term that wanted to be added.
	 */
	public void addAnswer(MintermReduced term) {
		if (!term.isChecked() && !answer.contains(term))
			answer.add(term);

	}

	/**
	 * @param i the minterms group index.
	 * @param k the number of process.
	 * @param finished reduced till the end.
	 * @return finished reducing or not yet
	 */
	public boolean reduceReduced(int i, int k) {
		int reducedTerms = 0;
		for (int j = 0; j < process.get(k)[i].size(); j++) {
			for (int j2 = 0; j2 < process.get(k)[i + 1]
					.size(); j2++) {
				MintermReduced newterm = process.get(k)[i]
						.get(j).reduce(process.get(k)[i + 1]
								.get(j2));
				if (newterm != null) {
					reducedTerms++;
					process.get(k + 1)[i].add(newterm);

					qm.addStep(new Action(
							process.get(k)[i].get(j)
									.toString(),
							process.get(k)[i + 1].get(j2)
									.toString(),
							newterm.toString(),
							" is reduced with "));
				}
			}
			addAnswer(process.get(k)[i].get(j));

		}
		return reducedTerms == 0;
	}

	public void setObserver(QMM qm) {
		this.qm = qm;
	}

}
