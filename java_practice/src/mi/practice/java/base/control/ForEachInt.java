package mi.practice.java.base.control;

import static mi.practice.java.base.util.Range.*;
import static mi.practice.java.base.util.Print.*;

public class ForEachInt {

	public static void main(String[] args) {
		for (int i : range(10)) {
			printnb(i + " ");
		}
		print();
		for (int i : range(5, 10)) {
			printnb(i + " ");
		}
		print();
		for (int i : range(5, 20, 3)) {
			printnb(i + " ");
		}
		print();
	}
}
