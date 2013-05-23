package mi.practice.java.base.generics;

import java.util.List;
import java.util.Map;

import mi.practice.java.base.typeinfo.Person;
import mi.practice.java.base.typeinfo.pets.Pet;
//import mi.practice.java.base.util.New;
/**
 * 泛型函数是有限制的,
 * 比如不能作为另一个函数的参数,
 * 仅仅只能用来赋值
 * @author waf
 */
public class LimitsOfInference {
	static void f(Map<Person, List<? extends Pet>> petPeople){
//		f(New.map()); //不能编译
	}
}
