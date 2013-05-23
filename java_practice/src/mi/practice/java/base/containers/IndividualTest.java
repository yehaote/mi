package mi.practice.java.base.containers;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import mi.practice.java.base.holding.MapOfList;
import mi.practice.java.base.typeinfo.pets.Individual;
import mi.practice.java.base.typeinfo.pets.Pet;

public class IndividualTest {
	public static void main(String[] args){
		Set<Individual> pets = new TreeSet<Individual>();
		for(List<? extends Pet> lp: MapOfList.petPeople.values()){
			for(Pet p : lp){
				pets.add(p);
			}
		}
		System.out.println(pets);
	}
}