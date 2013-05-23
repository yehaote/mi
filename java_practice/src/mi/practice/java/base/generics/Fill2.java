package mi.practice.java.base.generics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import mi.practice.java.base.generics.coffee.Coffee;
import mi.practice.java.base.generics.coffee.Latte;
import mi.practice.java.base.generics.coffee.Mocha;
import mi.practice.java.base.util.Generator;

interface Addable<T> {
	void add(T t);
}

public class Fill2 {
	//class token version
	public static <T> void fill(Addable<T> addable,
			Class<? extends T> classToken, int size) {
		for(int i=0;i<size;i++){
			try {
				addable.add(classToken.newInstance());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	//generic version
	public static <T> void fill(Addable<T> addable,
			Generator<T> gen, int size){
		for(int i=0;i<size;i++){
			addable.add(gen.next());
		}
	}
	
	public static void main(String[] args){
		
	}
}

// To adapt a base type, you must use composition.
// Make any Collection Addable using composition.
class AddableCollectionAdapter<T> implements Addable<T>{
	private Collection<T> c;
	public AddableCollectionAdapter(Collection<T> c){
		this.c=c;
	}
	@Override
	public void add(T t) {
		c.add(t);
	}
}

// A Helper to capture the type automatically
class Adapter {
	public static <T>
		Addable<T> collectionAdapt(Collection<T> c){
		return new AddableCollectionAdapter<T>(c);
	}
}

// To adapt to a specific type, you can use inheritance.
// Make a SimpleQueue Addable using inheritance.
class AddableSimpleQueue<T> extends SimpleQueue<T>
	implements Addable<T>{
	@Override // This override is not useful, the add method is just the same be there
	public void add(T item){
		super.add(item);
	}
}

class Fill2Test{
	public static void main(String[] args){
		//Adapt a Collection:
		List<Coffee> carrier = new ArrayList<Coffee>();
		Fill2.fill(new AddableCollectionAdapter<Coffee>(carrier), Coffee.class, 3);
		// Helper method captures the type
		Fill2.fill(Adapter.collectionAdapt(carrier), Coffee.class, 2);
		for(Coffee c: carrier){
			System.out.println(c);
		}
		System.out.println("-----------------------------");
		// Use a adapted class:
		AddableSimpleQueue<Coffee> coffeeQueue= new AddableSimpleQueue<Coffee>();
		Fill2.fill(coffeeQueue, Mocha.class, 4);
		Fill2.fill(coffeeQueue, Latte.class, 1);
		for(Coffee c:coffeeQueue){
			System.out.println(c);
		}
	}
}