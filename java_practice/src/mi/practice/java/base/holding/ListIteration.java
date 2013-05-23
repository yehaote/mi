package mi.practice.java.base.holding;

import java.util.List;
import java.util.ListIterator;

import mi.practice.java.base.typeinfo.pets.Pet;
import mi.practice.java.base.typeinfo.pets.Pets;
/**
 * ListIterator是List的一个扩展的Iterator
 * ListIterator是双向的
 * 而且它可以制定index进行初始化
 * 它还可以修改对象, set()
 */
public class ListIteration {
	public static void main(String[] args){
		List<Pet> pets = Pets.arrayList(8);
		ListIterator<Pet> it = pets.listIterator();
		while(it.hasNext()){
			System.out.print(it.next()+", "+it.nextIndex()+", "+it.previousIndex()+"; ");
		}
		System.out.println();
		while(it.hasPrevious()){
			System.out.print(it.previous().id()+" ");
		}
		System.out.println();
		System.out.println(pets);
		it=pets.listIterator(3);
		while(it.hasNext()){
			it.next();
			it.set(Pets.randomPet());
		}
		System.out.println(pets);
	}
}
