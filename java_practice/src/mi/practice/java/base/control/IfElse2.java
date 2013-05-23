package mi.practice.java.base.control;

import static mi.practice.java.base.util.Print.*;

public class IfElse2 {

	static int test(int testval, int target) {
		if (testval > target)
			return +1;
		else if (testval < target)
			return -1;
		else
			return 0;
	}

	public static void main(String[] args) {
		print(test(10,5));
		print(test(5,10));
		print(test(5,5));
	}

}
