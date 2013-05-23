package mi.practice.java.base.holding;

import java.util.AbstractCollection;
import java.util.Iterator;

import mi.practice.java.base.typeinfo.pets.Pet;
import mi.practice.java.base.typeinfo.pets.Pets;
/**
 * remove()是一个可选的方法, 不需要一定要有具体实现, 屏蔽它可以采用抛出异常的方式
 * 如果你继承了AbstractCollection或者实现了Collection, 你必须实现Iterator
 * Iterator可以单独实现
 */
public class CollectionSequence extends AbstractCollection<Pet>{
	private Pet[] pets = Pets.createArray(8);
	@Override
	public Iterator<Pet> iterator() {
		return new Iterator<Pet>() {
			private int index=0;
			@Override
			public boolean hasNext() {
				return index<pets.length;
			}
			@Override
			public Pet next() {
				return pets[index++];
			}
			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
			
		};
	}
	@Override
	public int size() {
		return pets.length;
	}
	
	public static void main(String[] args){
		CollectionSequence cs = new CollectionSequence();
		InterfaceVSIterator.display(cs);
		InterfaceVSIterator.display(cs.iterator());
	}
}