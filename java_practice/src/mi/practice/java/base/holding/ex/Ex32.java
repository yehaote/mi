package mi.practice.java.base.holding.ex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import mi.practice.java.base.typeinfo.pets.Pet;
import mi.practice.java.base.typeinfo.pets.Pets;

class PetSequence {
	protected Pet[] pets = Pets.createArray(8);
}

class NonCollectionSequence extends PetSequence implements Iterable<Pet> {
	public Iterator<Pet> iterator() {
		return new Iterator<Pet>() {
			private int index = 0;

			@Override
			public boolean hasNext() {
				return index < pets.length;
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

	public Iterable<Pet> reverse() {
		return new Iterable<Pet>() {
			@Override
			public Iterator<Pet> iterator() {
				return new Iterator<Pet>() {
					private int index = pets.length - 1;

					@Override
					public boolean hasNext() {
						return index > -1;
					}

					@Override
					public Pet next() {
						return pets[index--];
					}

					@Override
					public void remove() {
						throw new UnsupportedOperationException();
					}

				};
			}
		};
	}

	public Iterable<Pet> randomized() {
		return new Iterable<Pet>() {
			@Override
			public Iterator<Pet> iterator() {
				List<Pet> list = new ArrayList<Pet>(Arrays.asList(pets));
				Collections.shuffle(list);
				return list.iterator();
			}
		};
	}
}

public class Ex32 {
	public static void main(String[] args) {
		NonCollectionSequence ncs = new NonCollectionSequence();
		System.out.print("reverse(): ");
		for (Pet pet : ncs.reverse()) {
			System.out.print(pet + " ");
		}
		System.out.println();
		System.out.print("randomized(): ");
		for (Pet pet : ncs.randomized()) {
			System.out.print(pet + " ");
		}
		System.out.println();
		for (Pet pet : ncs) {
			System.out.print(pet + " ");
		}
	}
}
