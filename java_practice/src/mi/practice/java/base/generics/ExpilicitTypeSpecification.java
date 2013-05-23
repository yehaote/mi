package mi.practice.java.base.generics;

import java.util.List;
import java.util.Map;

import mi.practice.java.base.typeinfo.Person;
import mi.practice.java.base.typeinfo.pets.Pet;
import mi.practice.java.base.util.New;
/**
 * 使用显示声明类型可以解决调用泛型方法的限制
 * @author waf
 */
public class ExpilicitTypeSpecification {
	static void f(Map<Person, List<? extends Pet>> petPeople){}
	public static void main(String[] args){
		f(New.<Person,List<? extends Pet>>map());
	}
}
