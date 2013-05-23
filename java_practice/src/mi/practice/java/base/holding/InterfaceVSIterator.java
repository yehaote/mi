package mi.practice.java.base.holding;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mi.practice.java.base.typeinfo.pets.Pet;
import mi.practice.java.base.typeinfo.pets.Pets;
/**
 * Collection接口是集合类的根
 * AbstractCollection提供了一个默认的Collection实现
 * 实现了Collection接口, 需呀实现iterator()方法
 * 当我们想在一个类里便利, 可是它又没有实现list接口的时候,Iterator就方便多了
 */
public class InterfaceVSIterator {
	public static void display(Iterator<Pet> it) {
		while (it.hasNext()) {
			Pet p = it.next();
			System.out.print(p.id() + ":" + p + " ");
		}
		System.out.println();
	}

	public static void display(Collection<Pet> pets) {
		for (Pet p : pets) {
			System.out.print(p.id() + ":" + p + " ");
		}
		System.out.println();
	}

	public static void main(String[] args) {
		List<Pet> petList = Pets.arrayList(8);
		Set<Pet> petSet = new HashSet<Pet>(petList);
		Map<String, Pet> petMap = new LinkedHashMap<String, Pet>();
		String[] names = ("Ralph, Eric, Robin, Lacey, Britney, Sam, Spot, Fluffy"
				.split(", "));
		for(int i=0;i<names.length;i++){
			petMap.put(names[i], petList.get(i));
		}
		display(petList);
		display(petSet);
		display(petList.iterator());
		display(petSet.iterator());
		System.out.println(petMap);
		System.out.println(petMap.keySet());
		display(petMap.values());
		display(petMap.values().iterator());
	}
}
