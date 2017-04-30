package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import models.Minterm;
import models.MintermReduced;

public class MintermTest {
	private Minterm minterm;

	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void testHammingDistance() {
		minterm = new Minterm(4, 0);
		assertTrue(minterm
				.isAdjacent(new Minterm(4, 1).getValue()));
		assertFalse(minterm
				.isAdjacent(new Minterm(4, 3).getValue()));

	}

	@Test
	public void testReduce() {
		minterm = new Minterm(4, 0);
		MintermReduced reduced = minterm
				.reduce( new Minterm(4, 2));
		assertEquals(0, reduced.getValue());
		final int[] reducedDiff = {2};
		assertArrayEquals(reducedDiff,
				reduced.getReducedDifferences());

	}

	@Test
	public void testReduceNotPossible() {
		minterm = new Minterm(4, 1);
		MintermReduced reduced = minterm
				.reduce( new Minterm(4, 10));
		assertNull(reduced);
	}
	@Test 
	public void testAlreadyReduced(){
		MintermReduced term1 = new MintermReduced(4, 0, 2);
		MintermReduced term2 = new MintermReduced(4, 8, 2);
		MintermReduced reduced = term1.reduce(term2);
		assertEquals(0, reduced.getValue());
		final int[] reducedDiff = {2,8};
		assertArrayEquals(reducedDiff,
				reduced.getReducedDifferences());

	}
	
	@Test 
	public void testAlreadyReducedMultipleTimes(){
		MintermReduced term1 = new MintermReduced(4, 0, 2);
		MintermReduced term2 = new MintermReduced(4, 8, 2);
		MintermReduced reduced = term1.reduce(term2);
		assertEquals(0, reduced.getValue());
		final int[] reducedDiff = {2,8};
		assertArrayEquals(reducedDiff,
				reduced.getReducedDifferences());
		
		MintermReduced term3 = new MintermReduced(4, 1, 2);
		MintermReduced term4 = new MintermReduced(4, 9, 2);
		MintermReduced reduced2 = term3.reduce(term4);
		assertEquals(1, reduced2.getValue());
		final int[] reducedDiff2 = {2,8};
		assertArrayEquals(reducedDiff2,
				reduced.getReducedDifferences());
		
		MintermReduced term5 = reduced2.reduce(reduced);
		assertEquals(0, term5.getValue());
		final int[] reducedDiff3 = {1,2,8};
		assertArrayEquals(reducedDiff3,
				term5.getReducedDifferences());
	}
}
