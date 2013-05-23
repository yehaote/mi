package mi.practice.java.base.holding;

import java.util.Iterator;

import mi.practice.java.base.typeinfo.pets.Pet;
import mi.practice.java.base.typeinfo.pets.Pets;
/**
 * Iterator一般使用匿名类实现
 * 不需要在类声明的时候集成或者实现接口,
 * 直接写一个方法返回就好了
 */
class PetSequence{
	protected Pet[] pets= Pets.createArray(8);
}
public class NonCollectionSequence extends PetSequence{
	public Iterator<Pet> iterator(){
		return new Iterator<Pet>() {
			private int index=0;
			@Override
			public boolean hasNext() {
				return index< pets.length;
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
	public static void main(String[] args){
		NonCollectionSequence ncs = new NonCollectionSequence();
		InterfaceVSIterator.display(ncs.iterator());
	}
}
