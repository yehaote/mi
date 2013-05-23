package mi.practice.java.base.initialization;

import static mi.practice.java.base.util.Print.print;
import static mi.practice.java.base.util.Print.printnb;
/**
 * 当基本类型参数比函数中需求的要大的时候, 需要显示转换
 */
public class Demotion {
	void f1(char x) {
		printnb("f1(char) ");
	}

	void f1(byte x) {
		printnb("f1(byte) ");
	}

	void f1(short x) {
		printnb("f1(short) ");
	}

	void f1(int x) {
		printnb("f1(int) ");
	}

	void f1(long x) {
		printnb("f1(long) ");
	}

	void f1(float x) {
		printnb("f1(float) ");
	}

	void f1(double x) {
		printnb("f1(double) ");
	}

	void f2(char x) {
		printnb("f2(char) ");
	}

	void f2(byte x) {
		printnb("f2(byte) ");
	}

	void f2(short x) {
		printnb("f2(short) ");
	}

	void f2(int x) {
		printnb("f2(int) ");
	}

	void f2(long x) {
		printnb("f2(long) ");
	}

	void f2(float x) {
		printnb("f2(float) ");
	}

	void f3(char x) {
		printnb("f3(char) ");
	}

	void f3(byte x) {
		printnb("f3(byte) ");
	}

	void f3(short x) {
		printnb("f3(short) ");
	}

	void f3(int x) {
		printnb("f3(int) ");
	}

	void f3(long x) {
		printnb("f3(long) ");
	}

	void f4(char x) {
		printnb("f4(char) ");
	}

	void f4(byte x) {
		printnb("f4(byte) ");
	}

	void f4(short x) {
		printnb("f4(short) ");
	}

	void f4(int x) {
		printnb("f4(int) ");
	}

	void f5(char x) {
		printnb("f5(char) ");
	}

	void f5(byte x) {
		printnb("f5(byte) ");
	}

	void f5(short x) {
		printnb("f5(short) ");
	}

	void f6(char x) {
		printnb("f6(char) ");
	}

	void f6(byte x) {
		printnb("f6(byte) ");
	}

	void f7(char x) {
		printnb("f7(char) ");
	}

	void testDouble() {
		double x = 0.0;
		printnb("double argument: ");
		f1(x);
		f2((float) x);
		f3((long) x);
		f4((int) x);
		f5((short) x);
		f6((byte) x);
		f7((char) x);
		print();
	}

	public static void main(String[] args) {
		Demotion d = new Demotion();
		d.testDouble();
	}
}
