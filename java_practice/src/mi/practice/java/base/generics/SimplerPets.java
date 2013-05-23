package mi.practice.java.base.generics;

import java.util.List;
import java.util.Map;

import mi.practice.java.base.typeinfo.Person;
import mi.practice.java.base.typeinfo.pets.Pet;
import mi.practice.java.base.util.New;
/**
 * 使用泛型函数可以减少一些代码
 * @author waf
 */
public class SimplerPets {
	@SuppressWarnings("unused")
	public static void main(String[] args){
		Map<Person, List<? extends Pet>> petPeople= New.map();
	}
}
