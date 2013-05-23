package mi.practice.java.base.holding;

import java.util.LinkedList;

import mi.practice.java.base.typeinfo.pets.Hamster;
import mi.practice.java.base.typeinfo.pets.Pet;
import mi.practice.java.base.typeinfo.pets.Pets;
import mi.practice.java.base.typeinfo.pets.Rat;

import static mi.practice.java.base.util.Print.*;
/**
 * LinkedList实现了Queue接口
 */
public class LinkedListFeatures {
	public static void main(String[] args){
		LinkedList<Pet> pets = new LinkedList<Pet>(Pets.arrayList(5));
		print(pets);
		// 下面两个方法效果相同
		print("pets.getFirst(): "+pets.getFirst());
		print("pets.element(): "+pets.element());
		// 只有当是空集合的时候才会不同, 判断是否为空集合
		print("pets.peek(): "+pets.peek());
		// 相同: 删除并返回第一个元素
		print("pets.remove(): " +pets.remove());
		print("pets.removeFirst(): "+pets.removeFirst());
		// 带判断的removeFirst()
		print("pets.poll(): "+pets.poll());
		print(pets);
		//在最前面增加
		pets.addFirst(new Rat());
		print("After addFirst(): "+pets);
		// offer()其实也是调用add()
		pets.offer(Pets.randomPet());
		print("After offer(): "+pets);
		pets.add(Pets.randomPet());
		print("After add(): "+pets);
		// 无返回的add()
		pets.addLast(new Hamster());
		print("After addLast(): "+pets);
		print("pets.removeLast(): "+pets.removeLast());
	}
}
