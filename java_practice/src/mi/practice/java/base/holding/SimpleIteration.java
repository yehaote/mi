package mi.practice.java.base.holding;

import java.util.Iterator;
import java.util.List;

import mi.practice.java.base.typeinfo.pets.Pet;
import mi.practice.java.base.typeinfo.pets.Pets;
/**
 * 迭代器可以很方便的来遍历集合
 * 主要方法: 调用iterator()获得迭代器, next()等到下一个元素
 * 			hasNext()判断是否还有其他元素, remove()删除元素
 * 如果可以获得集合对象, 并且只是想遍历而不需要修改的话
 * foreach循环显得更加简洁一点
 */
public class SimpleIteration {
	public static void main(String[] args){
		List<Pet> pets = Pets.arrayList(12);
		Iterator<Pet> it = pets.iterator();
		while(it.hasNext()){
			Pet p =it.next();
			System.out.print(p.id() +":"+p+" ");
		}
		System.out.println();
		for(Pet p :pets){
			System.out.print(p.id() +":"+p+" ");
		}
		System.out.println();
		//迭代器也可以删除元素
		it=pets.iterator();
		for(int i=0;i<6;i++){
			it.next();
			it.remove();
		}
		System.out.println(pets);
	}
}
