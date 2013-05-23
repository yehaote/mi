package mi.practice.java.base.typeinfo.pets;

import mi.practice.java.base.util.TypeCounter;
import static mi.practice.java.base.util.Print.*;

public class PetCount4 {
	public static void main(String[] args){
		TypeCounter counter = new TypeCounter(Pet.class);
		for(Pet pet : Pets.createArray(20)){
			printnb(pet.getClass().getSimpleName()+" ");
			counter.count(pet);
		}
		print();
		print(counter);
	}
}
