package models;

import java.util.LinkedList;

public class Petrik {
LinkedList<LinkedList<Integer>> petrikTerm = new LinkedList<>();


public Petrik(LinkedList<LinkedList<Integer>> petrikterm) {
	this.petrikTerm = petrikterm;
}


public Petrik(Integer[] petrikterm) {
	LinkedList<LinkedList<Integer>> ans = new LinkedList<>();
	for (int i = 0; i < petrikterm.length; i++) {
		LinkedList<Integer> term = new LinkedList<>();
		term.add(petrikterm[i]);
		ans.add(term);
	}
	this.petrikTerm = ans;
}







public Petrik MinTerms(Petrik term) {
	Petrik answer =new Petrik(new LinkedList<>());
	LinkedList<LinkedList<Integer>> list1 = this.petrikTerm;
	LinkedList<LinkedList<Integer>> list2 = term.petrikTerm;

	for (int i = 0; i < list1.size(); i++) {
		for (int j = 0; j < list2.size(); j++) {
			LinkedList<Integer> temp =new LinkedList<>(list1.get(i));
			

			
				if(!list1.get(i).containsAll(list2.get(j)))
					temp.add(list2.get(j).getFirst());
				
				
			
			answer.petrikTerm.add(temp);
		}
		
		
	}
	answer=minimized(answer);
	return answer;
}
public Petrik minimized(Petrik term) {
	
	for (int i = 0; i < term.petrikTerm.size(); i++) {
		LinkedList<Integer> petrik1 = term.petrikTerm.get(i);

		int j = 0;
		while (j<term.petrikTerm.size()) {
			
	 
			LinkedList<Integer> petrik2 = term.petrikTerm.get(j);
			if(petrik2.containsAll(petrik1) && i!=j)
			{
				term.petrikTerm.remove(j);
				if(j<i)
				i--;
				j--;
			}

			j++;
			
		}
	}
	return term ;
}
}
