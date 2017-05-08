package tests;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.LinkedList;

import models.MintermReduced;
import models.Simplifier;

import org.junit.Before;
import org.junit.Test;

public class SimplifierTest {
	Simplifier test;

	@Before
	public void setUp() {
		test = new Simplifier();
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

	

}
