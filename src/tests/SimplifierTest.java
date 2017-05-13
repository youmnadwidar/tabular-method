package tests;

import static org.junit.Assert.*;

import java.util.LinkedList;

import models.MintermReduced;
import models.Parser;
import models.Simplifier;

import org.junit.Before;
import org.junit.Test;

import controllers.QMM;

public class SimplifierTest {
	Simplifier test;
	Parser parser;
	QMM qmm;

	@Before
	public void setUp() {
		test = new Simplifier();
		parser =new Parser();
		qmm = new QMM("4","4","4");
		test.setObserver(qmm);
	}

	@Test
	public void testgetTerms() {
		int[] ans = {5, 7, 13, 15};
		assertArrayEquals(ans,
				test.getcoverTerms(new MintermReduced(
						new MintermReduced(4, 5, 2), 8)));
	}

	@Test
	public void testgetterms2() {

		int[] ans = {4, 5, 6, 12, 7, 13, 14, 15};
		assertArrayEquals(ans, test.getcoverTerms(
				new MintermReduced(new MintermReduced(
						new MintermReduced(4, 4, 1), 2),
						8)));
	}
	
	@Test
	public void test(){
		LinkedList<MintermReduced> terms= new LinkedList<MintermReduced>();
		terms.add(new MintermReduced(4, 0, -1));
		int[] wantedTerms= {0};
		System.out.println(parser.parse(test.simplify(terms, 4, wantedTerms),true));
	}

	

}
