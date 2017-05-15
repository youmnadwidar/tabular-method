package controllers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;

import models.Action;
import models.Minimizer;
import models.MintermReduced;
import models.Parser;
import models.Simplifier;

public class QMM {
	int[] minterms;
	int[] dc;
	Parser parser;
	Minimizer minimizer;
	Simplifier simplifier;
	LinkedList<LinkedList<MintermReduced>> minimizedTerms;
	String reduced;
	String currentProcess;
	ArrayBlockingQueue<Action> steps = new ArrayBlockingQueue<Action>(
			1000000);
	private Integer bits;

	public QMM(String path) {
		this(new File(path));

	}

	public QMM(String minterms, String dc, String bits) {
		initiate(minterms, dc, bits);
	}

	public QMM(File file) {
		Scanner in;
		String bits, minterms, dc;
		try {
			in = new Scanner(file);
		} catch (FileNotFoundException e) {
			this.currentProcess = "Could Not Load File";
			return;
		}
		try {
			bits = in.nextLine();
			minterms = in.nextLine();
		} catch (NoSuchElementException e) {
			this.currentProcess = "Invalid file format";
			return;
		}
		try {
			dc = in.nextLine();
		} catch (NoSuchElementException e) {
			dc = "";
		}
		initiate(minterms, dc, bits);
	}

	public void initiate(String minterms, String dc,
			String bits) {
		parser = new Parser();
		try{
			this.bits = Integer.valueOf(bits);
		}catch(NumberFormatException e){
			this.currentProcess = "Invalid Number Format";
			return;
		}
		this.minterms = parser.parse(minterms);
		this.dc = parser.parse(dc);
		if (this.minterms == null || this.dc == null){
			this.currentProcess = "Invalid Input format, only positive numbers and commas.";
			return;
		}
		int[] allTerms;
		if (this.minterms.length == 0) {
			this.currentProcess = "minterms can't be empty";
			return;
		}
		if (this.dc.length == 0) {
			allTerms = new int[this.minterms.length];
		} else {
			allTerms = new int[this.minterms.length
					+ this.dc.length];
		}
		try {
			System.arraycopy(this.minterms, 0, allTerms, 0,
					this.minterms.length);
		} catch (NullPointerException e) {
			this.currentProcess = "minterms can't be empty";
		}
		try {
			System.arraycopy(this.dc, 0, allTerms,
					this.minterms.length, this.dc.length);
			Arrays.sort(allTerms);
			Arrays.sort(this.minterms);
		} catch (NullPointerException e) {

		}
		if(allTerms[allTerms.length-1]>= (int) Math.pow(2, this.bits)){
			this.currentProcess = "One or term is not smaller than 2^bits.";
			return;

		}
		
		try {
			this.minimizer = new Minimizer(
					this.bits);
			minimizer.setObserver(this);
		} catch (NumberFormatException e) {
			this.currentProcess = "number of bits is required";
			return;
		}
		
		simplifier = new Simplifier();

		simplifier.setObserver(this);
		this.reduced = parser.parse(simplifier.simplify(
				minimizer.minimize(allTerms),
				this.bits, this.minterms),
				true);

		this.currentProcess = "Complete!";
	}

	public String printArray(int[] array) {
		StringBuilder sb = new StringBuilder();
		for (int i : array) {
			sb.append(i+", ");
		}
		sb.delete(sb.length()-2, sb.length());
		return sb.toString();
	}

	public String getReduced() {
		return this.reduced;
	}

	public void setCurrentProccess(String process) {
		this.currentProcess = process;
	}

	public void addStep(Action step) {
		steps.add(step);
	}

	public String getCurrentProccess() {
		return this.currentProcess;
	}

	public String  getSteps() {
		StringBuilder sb = new StringBuilder();
		for (Action a : steps) {
			sb.append("* "+a.toString());
			sb.append('\n');
		}
		return sb.toString();
	}

	public void save(File file) throws IOException {
		BufferedWriter out = new BufferedWriter(
				new FileWriter(file));
		out.write("The function:"+'\n');
		out.write(printArray(minterms)+'\n');
		out.write("The Don't cares"+'\n');
		out.write(printArray(dc)+'\n');
		out.write("Number of Bits:"+'\n');
		out.write(this.bits+'\n');
		out.write("Reduced Form of the function:"+'\n');
		out.write(this.reduced);
		out.write('\n');
		out.write('\n');
		out.write("Steps:");
		out.write('\n');
		out.write(getSteps());
		out.close();

	}

}
