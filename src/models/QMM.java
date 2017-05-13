package models;

import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;

import controllers.Minimizer;
import models.Parser;

public class QMM {
	int[] minterms;
	int[] dc;
	Parser parser;
	Minimizer minimizer;
	Simplifier simplifier;
	LinkedList<LinkedList<MintermReduced>> minimizedTerms;
	String reduced;
	String currentProcess;
	ArrayBlockingQueue<Action> steps = new ArrayBlockingQueue<Action>(1000000);

	public QMM(String minterms, String dc, String bits) {
		parser = new Parser();
		
		this.minterms = parser.parse(minterms);
		this.dc = parser.parse(dc);
		int[] allTerms;
		if (this.minterms.length == 0) {
			this.reduced = "minterms can't be empty";
			return;
		}
		if (this.dc.length==0) {
			allTerms = new int[this.minterms.length];
		} else {
			allTerms = new int[this.minterms.length
					+ this.dc.length];
		}
		try {
			System.arraycopy(this.minterms, 0, allTerms, 0,
					this.minterms.length);
		} catch (NullPointerException e) {
			this.reduced = "minterms can't be empty";
		}
		try {
			System.arraycopy(this.dc, 0, allTerms,
					this.minterms.length, this.dc.length);
		} catch (NullPointerException e) {

		}
		try {
			this.minimizer = new Minimizer(
					Integer.valueOf(bits));
			minimizer.setObserver(this);
		} catch (NumberFormatException e) {
			this.reduced = "number of bits is required";
			return;
		}
		simplifier = new Simplifier();
		/*
		 * minimizedTerms = simplifier.simplify(
		 * minimizer.minimize(allTerms), bits, this.minterms);
		 */
		simplifier.setObserver(this);
		this.reduced = parser
				.parse(simplifier.simplify(minimizer.minimize(allTerms), Integer.valueOf(bits), this.minterms),true);
		//this.reduced = parser
		//		.parse(minimizer.minimize(allTerms));
	
		this.currentProcess="Complete!";
		for(Action action: steps){
			System.out.println(action.toString());
		}
		
	}

	public void printArray(int[] array) {
		for (int i : array) {
			System.out.println(i);
		}
	}

	public String getReduced() {
		return this.reduced;
	}
	
	public void setCurrentProccess(String process){
		this.currentProcess = process;
	}
	
	public void addStep(Action step){
		steps.add(step);
	}

	public String getCurrentProccess() {
		return this.currentProcess ;
	}
	
	public String getSteps(){
		StringBuilder sb = new StringBuilder();
		for(Action a : steps){
			sb.append(a.toString());
			sb.append('\n');
		}
		return sb.toString();
	}

}
