package mi.practice.java.base.initialization;

public class OptionalTrailingArguments {

	static void f(int required, String... trailing) {
		System.out.print("required: " + required + " ");
		for (String s : trailing) {
			System.out.print(s + " ");
		}
		System.out.println();
	}

	public static void main(String[] args) {
		f(1, "One");
		f(2, "Two", "Three");
		f(0);
	}
}
