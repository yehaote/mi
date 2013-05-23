package mi.practice.java.base.initialization;

public class VarargType {
	static void f(Character... args) {
		System.out.print(args.getClass());
		System.out.println(" length " + args.length);
	}

	static void g(int... args) {
		System.out.print(args.getClass());
		System.out.println(" length " + args.length);
	}

	public static void main(String[] args) {
		f('a');
		f('a','b');
		f(new Character[]{'a','b','c'});
		f();
		g(1);
		g(1,2);
		g(new int[]{1,2,3});
		g();
		System.out.println("int[]: " + new int[0].getClass());
	}
}
