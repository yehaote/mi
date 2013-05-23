package mi.practice.java.base.holding.ex;

import java.util.Collection;
import java.util.Iterator;

import mi.practice.java.base.holding.InterfaceVSIterator;
import mi.practice.java.base.typeinfo.pets.Pet;
import mi.practice.java.base.typeinfo.pets.Pets;

class CollectionSequence implements Collection<Pet>{
	private Pet[] pets = Pets.createArray(8);
	@Override
	public int size() {
		return pets.length;
	}

	@Override
	public boolean isEmpty() {
		return pets.length>0;
	}

	@Override
	public boolean contains(Object o) {
		for(Pet p : pets){
			if((p==o)||(p.equals(o))){
				return true;
			}
		}
		return false;
	}

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
	public Object[] toArray() {
		return pets;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean add(Pet e) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean remove(Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(Collection<? extends Pet> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		for(int i=0;i<pets.length;i++){
			pets[i]=null;
		}
	}
}
public class Ex30 {
	public static void main(String[] args){
		CollectionSequence cs = new CollectionSequence();
		InterfaceVSIterator.display(cs);
		InterfaceVSIterator.display(cs.iterator());
	}
}
