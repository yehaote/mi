package mi.practice.java.base.holding.ex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

class Rodent {
	private Word word = new Word("Rodent");
	private Description des;

	public Rodent() {
	}

	public Rodent(Description des) {
		this.des = des;
		this.des.addRef();
	}

	public void say() {
		System.out.println("Rodent zzzzz");
	}

	public void dispose() {
		System.out.println("Rodent.dispose()");
		des.dispose();
	}

	@Override
	public String toString() {
		return word.toString();
	}
}

class Mouse extends Rodent {
	public Mouse() {
	}

	public Mouse(Description des) {
		super(des);
	}

	private Word word = new Word("Mouse");

	@Override
	public void say() {
		System.out.println("Mouse zzzzz");
	}

	@Override
	public String toString() {
		return word.toString();
	}
}

class Ex10Gerbil extends Mouse {
	public Ex10Gerbil() {
	}

	public Ex10Gerbil(Description des) {
		super(des);
	}

	private Word word = new Word("Ex10Gerbil");

	@Override
	public void say() {
		System.out.println("Ex10Gerbil zzzzz");
	}

	@Override
	public String toString() {
		return word.toString();
	}
}

class Hamster extends Mouse {
	public Hamster() {
	}

	public Hamster(Description des) {
		super(des);
	}

	private Word word = new Word("Hamster");

	@Override
	public void say() {
		System.out.println("Hamster zzzzz");
	}

	@Override
	public String toString() {
		return word.toString();
	}
}

class Word {
	private String s;

	Word(String s) {
		System.out.println("Word " + s);
	}

	public String toString() {
		return s;
	}
}

class Description {
	private static long count = 0;
	private final long id = count++;
	private int refcount = 0;

	public Description() {
		System.out.println("Description() id = " + id);
	}

	public void addRef() {
		refcount++;
	}

	public void dispose() {
		if (--refcount == 0) {
			System.out.println("Description dispose()");
		}
	}

	@Override
	public void finalize() {
		if (refcount > 0) {
			System.out.println("error: refcount>0");
		}
	}

}

public class Ex10 {
	public static void main(String[] args) {
		List<Rodent> list = new ArrayList<Rodent>();
		Collections.addAll(list, new Rodent(), new Mouse(), new Ex10Gerbil(),
				new Hamster());
		Iterator<Rodent> it = list.iterator();
		while(it.hasNext()){
			it.next().say();
		}
	}
}