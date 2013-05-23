package mi.practice.java.base.innerclasses.ex;

interface U {
	void um1();

	void um2();

	void um3();
}

class A {
	U getU() {
		return new U() {
			@Override
			public void um3() {
				System.out.println("A.um3()");
			}

			@Override
			public void um2() {
				System.out.println("A.um2()");
			}

			@Override
			public void um1() {
				System.out.println("A.um1()");
			}
		};
	}
}

class B {
	int i = 0;
	U[] us;

	B(int length) {
		us = new U[length];
	}

	void addU(U u) {
		if (i < us.length && 0 <= i) {
			us[i] = u;
			i++;
		}
	}

	void callU() {
		for(int j=0;j<us.length;j++){
			if(us[j] !=null){
				us[j].um1();
				us[j].um2();
				us[j].um3();
			}
		}
	}

	void delete(int num) {
		if (0 <= num && num < us.length) {
			us[num] = null;
		}
	}
}

public class Ex23 {
	public static void main(String[] args){
		int length = 5;
		B b = new B(length);
		for(int i=0;i<length;i++){
			b.addU(new A().getU());
		}
		b.callU();
		b.delete(1);
		b.delete(3);
		b.callU();
	}
}
