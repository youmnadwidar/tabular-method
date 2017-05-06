package models;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.stream.IntStream;
import models.MintermReduced;

 class Simplifier implements ISimplifier {
	boolean [][] checked;
	int [] minterms;
	LinkedList<MintermReduced> answer = new LinkedList<>();
	LinkedList<MintermReduced> Reducedterms = new LinkedList<>();
	static LinkedList<int[]> diff = new LinkedList<>();

	@Override
	public LinkedList<MintermReduced> simplify(LinkedList<MintermReduced> firststep, int bits , int[] wantedTerms) {
		Reducedterms = firststep;
		fill2Darray(bits);
		getEssential(wantedTerms);
		getRowDominant();
		
		
		
		return null;
	}
	public void getRowDominant(){
		for (int i = 0; i < Reducedterms.size(); i++) {
			for (int j = i+1; j < Reducedterms.size(); j++) {
				int compare=containsAll(Reducedterms.get(i).getReducedDifferences(), Reducedterms.get(j).getReducedDifferences()) ;
				if(compare!=0)
				{
					if(compare==1){
						remove(Reducedterms.get(j));
					}
					else {
						remove(Reducedterms.get(i));
					}
				}
			
			}
			
		}
		
	}
	public void getColoumnDominant(){
		
	}
	public void fill2Darray(int bits){
		Arrays.fill(minterms, 0);
		checked=new boolean [Reducedterms.size()][(int) Math.pow(2, bits)];
		for (int i = 0; i < Reducedterms.size(); i++) {
             int [] coverd = new int [(int) Math.pow(2, Reducedterms.get(i).getReducedDifferences().length)];
             coverd=getcoverTerms(Reducedterms.get(i));
			for (int j = 0; j <coverd.length ; j++) {
			
				checked[i][coverd[j]]=true;
                minterms[coverd[j]]++;
				}
			}
		}
		
		
		
	
	public void getEssential(int[] wantedTerms){
		int [] essentials = new int [Reducedterms.size()];
		int j =0;
		for (int i = 0; i < minterms.length; i++) {
			if(minterms[i]==1)
			{
				if(contain(wantedTerms, i)){
				essentials[j]=i;
				j++;
				}
			}
		}
		for (int i = 0; i < Reducedterms.size(); i++) {
			for (int k = 0; k < essentials.length; k++) {
				
			if(checked[i][k]==true)
			{
				answer.add(Reducedterms.get(i));
				minterms[essentials[k]]--;
				 continue;
			}
				
			}
		}
		
	}
	public void getRemoved(){
		
	}
	private void remove(MintermReduced term){
		
	}

	public static void powerset(int[] s,int i,int k,int[] buff) {
        if(i<k) {
            for(int j=i;j<s.length;j++) {
            	int o =s[j];
              if(!contain(buff,s[j]))

                buff[i] = s[j];
                powerset(s,i+1,k,buff);
            }
        }       
        else {

          diff.add(buff);

        }
	}
    	public static boolean contain(int [] a,int d) {
			
		 {
    		for (int i = 0; i < a.length; i++) {
    			if(a[i]==d)
    				return true;
    		}
    		return false;
    	}
    	


    	}
    	public int[] getcoverTerms(MintermReduced term) {
    		diff=new LinkedList<>();
    		int[] coveredTerms=new int [(int) Math.pow(2, term.getReducedDifferences().length)];
    		for (int j2 = 1; j2 <= term.getReducedDifferences().length; j2++) {
				
    			powerset(term.getReducedDifferences(), 0, j2, new int [term.getReducedDifferences().length]);
    			}
    		int j = 0;
    		for (int i = 0; i < diff.size(); i++) {
    			if(!contain(coveredTerms, term.getValue()+IntStream.of(diff.get(i)).sum()))
    			coveredTerms[j]=term.getValue()+IntStream.of(diff.get(i)).sum();
			}
    		return coveredTerms;
    		
		}
    	/**
    	 * 
    	 * @return 1 when the second array is a subset of the first array.
    	 * @return -1 when the first array is a subset of the second array
    	 *  @return 0 when no array is subset of another
    	 */
    	public int containsAll(int [] a ,int [] b) {
    		boolean contain =true;
    		for (int i = 0; i < b.length; i++) {
				if(!contain(a, b[i])){
					contain=false;
				}
					
			}
    		if(contain)
    		{
    			return 1;
    		}
    		contain =true;
    		for (int i = 0; i < a.length; i++) {
				if(!contain(b, a[i])){
					contain=false;
				}
					
			}
    		if(contain)
    		{
    			return -1;
    		}
    		return 0;
			
		}
}
	


