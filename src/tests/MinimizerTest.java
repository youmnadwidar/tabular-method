package tests;


import java.util.LinkedList;

import org.junit.Assert;
import org.junit.Test;

import models.Minimizer;
import models.MintermReduced;

public class MinimizerTest {

	@Test
	public void testtwoterms() {
		Minimizer test=new Minimizer(4);
	    LinkedList<MintermReduced> answer =new LinkedList<>();
	    answer.add(new MintermReduced(2, 0, 1));
	    int [] term={0,1};
	    Assert.assertEquals("message", answer, test.minimize(term));
	    
	}
	@Test
	public void testReducedfirst(){
		Minimizer test=new Minimizer(4);
	    int [] term={0,1,5,7,8,10,14,15};

	    LinkedList<MintermReduced> answer =new LinkedList<>();
	    answer.add(new MintermReduced(4, 0, 1));
	    answer.add(new MintermReduced(4, 0, 8));
	    answer.add(new MintermReduced(4, 1, 4));
	   answer.add(new MintermReduced(4, 8, 2));
	    answer.add(new MintermReduced(4, 5, 2));
	    answer.add(new MintermReduced(4, 10, 4));
	    answer.add(new MintermReduced(4, 7, 8));
	   answer.add(new MintermReduced(4, 14, 1));
	    Assert.assertEquals("message", answer, test.minimize(term));

	}
	@Test
	public void testReducedsecond(){
		Minimizer test=new Minimizer(4);
	    int [] term={0,1,2,3,5,7,8,10,12,13,15};

	    LinkedList<MintermReduced> answer =new LinkedList<>();
	    answer.add(new MintermReduced(4, 8, 4));
	    answer.add(new MintermReduced(4, 12, 1));
	    answer.add(new MintermReduced(new MintermReduced(4, 0, 1), 2));
	    
	   answer.add(new MintermReduced(new MintermReduced(4, 0, 2), 8));
	    answer.add(new MintermReduced(new MintermReduced(4, 1, 2), 4));
	    answer.add(new MintermReduced(new MintermReduced(4, 5, 2), 8));
	    Assert.assertEquals("message", answer, test.minimize(term));
	}

	@Test
	public void testReduced2(){
		Minimizer test=new Minimizer(4);
	    int [] term={1,2,4,5,6,12,13,14};

	    LinkedList<MintermReduced> answer =new LinkedList<>();
	    answer.add(new MintermReduced(4, 1, 4));
	    answer.add(new MintermReduced(4, 2, 4));
	    answer.add(new MintermReduced(new MintermReduced(4, 4, 1), 8));
	    answer.add(new MintermReduced(new MintermReduced(4, 4, 2), 8));
	  
	    Assert.assertEquals("message", answer, test.minimize(term));
	}

	@Test
	public void testReduced(){
		Minimizer test=new Minimizer(4);
	    int [] term={1,4,5,9,10,12,13,15,14,11};

	    LinkedList<MintermReduced> answer =new LinkedList<>();
	    answer.add(new MintermReduced(new MintermReduced(4, 1, 4), 8));
	    answer.add(new MintermReduced(new MintermReduced(4, 4, 1), 8));
	    answer.add(new MintermReduced(new MintermReduced(4, 9, 2), 4));
	    answer.add(new MintermReduced(new MintermReduced(4, 10, 1), 4));
	    answer.add(new MintermReduced(new MintermReduced(4, 12, 1), 2));

	  
	    Assert.assertEquals("message", answer, test.minimize(term));
	}

}
