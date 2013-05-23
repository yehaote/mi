package mi.practice.java.base.control;

/**
 * 打印所有小写字母
 */
public class ListCharacter {
	public static void main(String[] args) {
		for (char c = 0; c < 128; c++) {
			if (Character.isLowerCase(c))
				System.out.println("value: " + (int) c + " character: " + c);
		}
	}
}
