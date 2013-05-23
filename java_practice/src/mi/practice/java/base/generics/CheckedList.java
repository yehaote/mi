package mi.practice.java.base.generics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mi.practice.java.base.typeinfo.pets.Cat;
import mi.practice.java.base.typeinfo.pets.Dog;
import mi.practice.java.base.typeinfo.pets.Pet;
/**
 * 在Java中提供新的CheckedList来防止unchecked操作, 
 * 如果是一个以前的类型而且还是rawtype的方式来使用的话, 
 * 可能会造成泛型失效的结果.
 * @author waf
 */
public class CheckedList {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	static void oldStyleMethod(List probablyDogs){
		probablyDogs.add(new Cat());
	}
	
	public static void main(String[] args){
		List<Dog> dogs1 = new ArrayList<Dog>();
		oldStyleMethod(dogs1); // quietly accepts a Cat
		List<Dog> dog2 = Collections.checkedList(new ArrayList<Dog>(), Dog.class);
		try{
			oldStyleMethod(dog2);
		}catch(Exception e){
			System.out.println(e);
		}
		//继承的类也可以很好地工作
		List<Pet> pets = Collections.checkedList(new ArrayList<Pet>(), Pet.class);
		pets.add(new Dog());
		pets.add(new Cat());
		
	}
}