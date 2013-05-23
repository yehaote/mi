package mi.practice.java.base.interfaces.ex.ex17;

public interface Ex17Interface {
	int A = PrintInitialized.printInitial("A");
	int B = PrintInitialized.printInitial("B");
}

class PrintInitialized {
	public static int printInitial(String str) {
		System.out.println(str + " initialized");
		return 1;
	}
}
