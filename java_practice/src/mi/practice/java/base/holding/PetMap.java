package mi.practice.java.base.holding;

import static mi.practice.java.base.util.Print.*;

import java.util.HashMap;
import java.util.Map;

import mi.practice.java.base.typeinfo.pets.Cat;
import mi.practice.java.base.typeinfo.pets.Dog;
import mi.practice.java.base.typeinfo.pets.Hamster;
import mi.practice.java.base.typeinfo.pets.Pet;
/**
 * 在map可以去查看值是否存在, 或者key是否存在
 * map.containsKey()
 * map.containsValue()
 */
public class PetMap {
	public static void main(String[] args){
		Map<String, Pet> petMap = new HashMap<String, Pet>();
		petMap.put("My Cat", new Cat("Molly"));
		petMap.put("My Dog", new Dog("Ginger"));
		petMap.put("My Hamster", new Hamster("Bosco"));
		print(petMap);
		Pet dog = petMap.get("My Dog");
		print(dog);
		print(petMap.containsKey("My Dog"));
		print(petMap.containsValue(dog));
	}
}
