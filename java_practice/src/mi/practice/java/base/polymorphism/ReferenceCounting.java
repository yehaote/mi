package mi.practice.java.base.polymorphism;

import static mi.practice.java.base.util.Print.*;

/**
 * 当多个对象引用一个对象的时候,
 * 如果这个被引用的对象需要销毁,
 * 可以在这个被引用的类中加入引用计数,
 * 当引用等于0时再销毁
 * static long 定义了创建实体的id
 * id定义成final意味着不可改变,
 * 一个实体一个id
 * Ex13
 */
class Shared {
	private int refcount = 0;
	private static long counter = 0;
	private final long id = counter++;

	public Shared() {
		print("Creating " + this);
	}

	public void addRef() {
		refcount++;
	}

	protected void dispose() {
		if ((--refcount) == 0) {
			print("Disposing " + this);
		}
	}

	@Override
	public void finalize() {
		if (refcount > 0) {
			print("error refcount didn't equals zero!!!");
		}
	}

	@Override
	public String toString() {
		return "Shared " + id;
	}
}

class Composing {
	private Shared shared;
	private static long counter = 0;
	private final long id = counter++;

	public Composing(Shared shared) {
		print("Creating " + this);
		this.shared = shared;
		this.shared.addRef();
	}

	protected void dispose() {
		print("disposing " + this);
		shared.dispose();
	}

	@Override
	public void finalize() {
		print("composing finalize, composing id = " + id);
	}

	@Override
	public String toString() {
		return "Composing " + id;
	}

}

public class ReferenceCounting {
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Shared shared = new Shared();
		Composing[] composings = { new Composing(shared),
				new Composing(shared), new Composing(shared),
				new Composing(shared), new Composing(shared) };
		for (Composing composing : composings) {
			composing.dispose();
		}
		shared.finalize();

		Shared sharedTest = new Shared();
		Composing composingTest = new Composing(sharedTest);
		sharedTest.finalize();

	}
}
