package mi.practice.java.base.reusing;

/**
 * 练习12
 */
class C1_Ex12 {
	C1_Ex12() {
		System.out.println("C1_Ex12()");
	}

	void dispose() {
		System.out.println("C1_Ex12.dispose()");
	}
}

class C2_Ex12 {
	C2_Ex12() {
		System.out.println("C2_Ex12()");
	}

	void dispose() {
		System.out.println("C2_Ex12.dispose()");
	}
}

class C3_Ex12 {
	C3_Ex12() {
		System.out.println("C3_Ex12()");
	}

	void dispose() {
		System.out.println("C3_Ex12.dispose()");
	}
}

class Root_EX12 {
	Root_EX12() {
		System.out.println("Root_EX12()");
	}

	C1_Ex12 c1 = new C1_Ex12();
	C2_Ex12 c2 = new C2_Ex12();
	C3_Ex12 c3 = new C3_Ex12();

	void dispose() {
		System.out.println("Root_EX12.dispose()");
		c3.dispose();
		c2.dispose();
		c1.dispose();
	}
}

class Stem_EX12 extends Root_EX12 {
	Stem_EX12() {
		System.out.println("Stem_EX12()");
	}

	C1_Ex12 c1 = new C1_Ex12();
	C2_Ex12 c2 = new C2_Ex12();
	C3_Ex12 c3 = new C3_Ex12();

	@Override
	void dispose() {
		System.out.println("Stem_EX12.dispose()");
		c3.dispose();
		c2.dispose();
		c1.dispose();
		super.dispose();
	}
}

public class Ex12 {
	public static void main(String[] args) {
		System.out.println("new root");
		Root_EX12 root = new Root_EX12();
		try {

		} finally {
			root.dispose();
		}
		System.out.println("new stem");
		Stem_EX12 stem = new Stem_EX12();
		try {

		} finally {
			stem.dispose();
		}
	}
}
