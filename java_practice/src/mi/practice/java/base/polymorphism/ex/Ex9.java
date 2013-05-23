package mi.practice.java.base.polymorphism.ex;

/**
 * Ex9, Ex12, Ex14
 */
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

class Gerbil extends Mouse {
	public Gerbil() {
	}

	public Gerbil(Description des) {
		super(des);
	}

	private Word word = new Word("Gerbil");

	@Override
	public void say() {
		System.out.println("Gerbil zzzzz");
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

public class Ex9 {
	public static void main(String[] args) {
		Rodent[] rodents = { new Rodent(), new Mouse(), new Gerbil(),
				new Hamster() };
		for (Rodent rodent : rodents) {
			rodent.say();
		}
	}
}

class Ex11 {
	public static void main(String[] args) {
		Rodent[] rodents = { new Rodent(), new Mouse(), new Gerbil(),
				new Hamster() };
		for (Rodent rodent : rodents) {
			rodent.say();
		}
	}
}

class Ex12{
	public static void main(String[] args){
		Description des = new Description();
		Rodent[] rodents = { new Rodent(des), new Mouse(des), new Gerbil(des),
				new Hamster(des) };
		for (Rodent rodent : rodents) {
			rodent.dispose();
		}
	}
}