package models;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.IntStream;

import controllers.QMM;

public class Simplifier implements ISimplifier {
	int bits;
	LinkedList<Integer> wanted = new LinkedList<>();
	LinkedList<LinkedList<Integer>> wantedTerms = new LinkedList<>();
	LinkedList<Integer>[] termschecked;
	LinkedList<MintermReduced> answer = new LinkedList<>();
	LinkedList<MintermReduced> Reducedterms = new LinkedList<>();
	LinkedList<LinkedList<MintermReduced>> answers = new LinkedList<LinkedList<MintermReduced>>();
	static LinkedList<int[]> diff = new LinkedList<>();
	QMM qmm;

	@Override
	public LinkedList<LinkedList<MintermReduced>> simplify(
			LinkedList<MintermReduced> firststep, int bits,
			int[] wantedTerms) {
		this.bits = bits;
		for (int i = 0; i < wantedTerms.length; i++) {
			this.wantedTerms.add(new LinkedList<>());
			this.wanted.add(wantedTerms[i]);
		}
		Reducedterms = firststep;
		fill2Darray(bits);
		getEssential(wantedTerms);
		if (this.wantedTerms.size() > 0) {
			getRowDominant();
			getColoumnDominant(wantedTerms);
			if (this.wantedTerms.size() > 1) {
				Petrik primes;
				primes = GetPetrik();

				for (int i = 0; i < primes.petrikTerm
						.size(); i++) {
					LinkedList<MintermReduced> temp = new LinkedList<>(
							answer);
					for (int k = 0; k < primes.petrikTerm
							.get(i).size(); k++) {

						temp.add(Reducedterms
								.get(primes.petrikTerm
										.get(i).get(k)));
					}
					answers.add(new LinkedList<>(temp));
				}
			} else if (this.wantedTerms.size() == 1) {
				for (int i = 0; i < this.wantedTerms
						.getFirst().size(); i++) {
					LinkedList<MintermReduced> temp = new LinkedList<>(
							answer);
					temp.add(Reducedterms
							.get(this.wantedTerms.getFirst()
									.get(i)));
					answers.add(new LinkedList<>(temp));
				}

			}

		} else {
			answers.add(answer);
		}

		return answers;
	}

	@SuppressWarnings("unchecked")
	public void fill2Darray(int bits) {

		termschecked = new LinkedList[(int) (Math.pow(2,bits))];

		for (int i = 0; i < Reducedterms.size(); i++) {

			int[] covered;
			covered = getcoverTerms(Reducedterms.get(i));

			for (int j = 0; j < covered.length; j++) {

				if (termschecked[covered[j]] == null)
					termschecked[covered[j]] = new LinkedList<>();
				termschecked[covered[j]].add(i);
			}
		}
	}

	public int[] getcoverTerms(MintermReduced term) {
		diff = new LinkedList<>();
		int[] coveredTerms = new int[(int) Math.pow(2,
				term.getReducedDifferences().length)];
		for (int j2 = 1; j2 <= term
				.getReducedDifferences().length; j2++) {

			powerset(term.getReducedDifferences(), 0, j2,
					new int[term
							.getReducedDifferences().length]);
		}
		int j = 1;
		coveredTerms[0] = term.getValue();
		for (int i = 0; i < diff.size(); i++) {
			if (!contain(coveredTerms, term.getValue()
					+ IntStream.of(diff.get(i)).sum())) {
				coveredTerms[j] = term.getValue()
						+ IntStream.of(diff.get(i)).sum();
				j++;
			}
		}
		return coveredTerms;

	}

	public static void powerset(int[] s, int i, int k,
			int[] buff) {
		if (i < k) {
			for (int j = i; j < s.length; j++) {
				if (!contain(buff, s[j]))

					buff[i] = s[j];
				powerset(s, i + 1, k, buff);
			}
		} else {
			int[] temp = Arrays.copyOf(buff, buff.length);
			diff.add(temp);

		}
	}

	public void getEssential(int[] wantedTerms) {

		fillWantedTerms(wantedTerms);
		int i = 0;
		while (i < this.wantedTerms.size()) {
			if (this.wantedTerms.get(i) != null && this.wantedTerms.get(i).size() == 1) {
				MintermReduced term = Reducedterms.get(
						this.wantedTerms.get(i).getFirst());
				answer.add(term);
				
				
					qmm.addStep(
							new Action(term.toString(),
							"", "",
							" is an essential prime implicant, added to answer."));
				remove(term);
				int j = 0;
				while (j < this.wantedTerms.size()) {
					if (this.wantedTerms.get(j) != null && this.wantedTerms.get(j)
							.contains(this.wantedTerms
									.get(i).getFirst())
							&& i != j) {
						wanted.remove(j);
						this.wantedTerms.remove(j);
						if (j < i)
							i--;
						j--;
					}
					j++;

				}
				this.wantedTerms.remove(i);

				i--;
			}
			i++;
		}

	}

	public static boolean contain(int[] a, int d) {

		{
			for (int i = 0; i < a.length; i++) {
				if (a[i] == d)
					return true;
			}
			return false;
		}

	}

	private void remove(MintermReduced term) {
		
		term.checked = true;
	}

	public void getRowDominant() {
		int i = Reducedterms.size() - 1;
		while (i > 0 && !Reducedterms.get(i).isChecked()) {
			int[] term1 = getcoverTerms(
					Reducedterms.get(i));
			for (int j = 0; j < i; j++) {
				int[] term2 = getcoverTerms(
						Reducedterms.get(j));
				int compare = containsAll(term1, term2);
				if (compare != 0) {
					if (compare == 1) {
						qmm.addStep(new Action(
								Reducedterms.get(j)
										.toString(),
								"", "",
								" is a row dominant implicant, added to answer."));
						remove(Reducedterms.get(j));
					} else {
						qmm.addStep(new Action(
								Reducedterms.get(i)
										.toString(),
								"", "",
								" is a row dominant implicant, added to answer."));
						remove(Reducedterms.get(i));
					}
				}
			}
			i--;
		}
	}

	public void getColoumnDominant(int[] wanted) {
		int i = 0;
		while (i < wantedTerms.size()) {
			for (int j = 0; j < wantedTerms.size()
					&& i != j; j++) {
				if (wantedTerms.get(i)!=null&&wantedTerms.set(j,wantedTerms.get(j))
						.containsAll(wantedTerms.set(i,wantedTerms.get(i)))) {
					qmm.addStep(new Action(this.wanted.get(j).toString(), this.wanted.get(i).toString(), "", "removed by coloumn Dominant with"));
					this.wanted.remove(j);
					this.wantedTerms.remove(j);
					j--;
				}

			}
			i++;
		}
	}

	public LinkedList<Integer> ArrayToList(int[] terms) {
		LinkedList<Integer> ans = new LinkedList<>();
		for (int i = 0; i < terms.length; i++) {
			ans.add(terms[i]);
		}
		return ans;
	}

	public Petrik GetPetrik() {
		while (wantedTerms.get(0)==null) {
			wantedTerms.removeFirst();
		}

		Petrik term1 = new Petrik(wantedTerms.get(0)
				.toArray(new Integer[wantedTerms.get(0).size()]));
		Petrik term2 = new Petrik(wantedTerms.get(1)
				.toArray(new Integer[wantedTerms.get(1)
						.size()]));
		Petrik ans = term1.MinTerms(term2);
		LinkedList<LinkedList<MintermReduced>> temp = new LinkedList<>();

		for (int i = 0; i < ans.petrikTerm.size(); i++) {
			temp.add(new LinkedList<>());
			for (int j = 0; j < ans.petrikTerm.get(i).size(); j++) {
				temp.get(i).add(Reducedterms.get(ans.petrikTerm.get(i).get(j)));
			}
		}
		qmm.addStep(new Action(wanted.removeFirst().toString(), wanted.removeFirst().toString(), temp.toString(), "used petrik to cover it with"));
		wantedTerms.removeFirst();
		wantedTerms.removeFirst();
		while (wantedTerms.size() != 0 &&wantedTerms.getFirst()!=null) {
			wantedTerms.set(0,
					wantedTerms.get(0));
			LinkedList<LinkedList<MintermReduced>> temp1 = new LinkedList<>();
			temp1=temp;
			ans = ans.MinTerms(new Petrik(wantedTerms.get(0)
					.toArray(new Integer[wantedTerms.get(0)
							.size()])));
			temp = new LinkedList<>();
			for (int i = 0; i < ans.petrikTerm.size(); i++) {
				temp.add(new LinkedList<>());
				for (int j = 0; j < ans.petrikTerm.get(i).size(); j++) {
					
					temp.get(i).add(Reducedterms.get(ans.petrikTerm.get(i).get(j)));
				}
			}
			qmm.addStep(new Action(temp1.toString(), wanted.removeFirst().toString(),temp.toString(), "used petrik to cover them both with the terms the covers"));

			wantedTerms.removeFirst();
		}
		ans = minAnswer(ans);
		return ans;
	}

	public void fillWantedTerms(int[] wanted) {
		for (int i = 0; i < wantedTerms.size(); i++) {
			wantedTerms.set(i, termschecked[wanted[i]]);
		}

	}

	public Petrik minAnswer(Petrik ans) {
		int min = 15555;
		for (int i = 0; i < ans.petrikTerm.size(); i++) {
			if (ans.petrikTerm.get(i).size() < min)
				min = ans.petrikTerm.get(i).size();
		}
		for (int i = 0; i < ans.petrikTerm.size(); i++) {
			if (ans.petrikTerm.get(i).size() > min) {
				qmm.addStep(new Action("answer"+i,"", "", "removed because it wasn't minimum"));
				ans.petrikTerm.remove(i);
				i--;
			}
		}
		return ans;
	}

	public LinkedList<Integer> removeChecked(
			LinkedList<Integer> primes) {
		int i = 0;
		while (i < primes.size()) {
			if (Reducedterms.get(primes.get(i))
					.isChecked()) {
				primes.remove(i);
				i--;
			}
			i++;
		}
		return primes;
	}

	/**
	 * @return 1 when the second array is a subset of the first
	 *         array.
	 * @return -1 when the first array is a subset of the second
	 *         array
	 * @return 0 when no array is subset of another
	 */
	public int containsAll(int[] a, int[] b) {
		boolean contain = true;
		for (int i = 0; i < b.length; i++) {
			if (!contain(a, b[i])) {
				contain = false;
			}

		}
		if (contain) {
			return 1;
		}
		contain = true;
		for (int i = 0; i < a.length; i++) {
			if (!contain(b, a[i])) {
				contain = false;
			}

		}
		if (contain) {
			return -1;
		}
		return 0;

	}

	public void setObserver(QMM qmm) {
		this.qmm = qmm;
	}
}
