package mi.practice.java.base.control;

import java.util.Random;
import static mi.practice.java.base.util.Print.*;

/**
 * 原音和辅音
 */
public class VowelsAndConsonants {

	public static void main(String[] args) {
		Random rand = new Random(47);
		for (int i = 0; i < 100; i++) {
			int c = rand.nextInt(26) + 'a';//产生0-25之间的整形数字
			printnb((char) c + ", " + c + ": ");
			switch (c) {
			case 'a':
			case 'e':
			case 'i':
			case 'o':
			case 'u':
				print("vowel");
				break;
			case 'y':
			case 'w':
				print("simetimes a vowel");
				break;
			default:
				print("consonant");
			}
		}
	}
}
