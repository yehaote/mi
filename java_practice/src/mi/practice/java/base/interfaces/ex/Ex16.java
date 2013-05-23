package mi.practice.java.base.interfaces.ex;

import java.io.IOException;
import java.nio.CharBuffer;
import java.util.Random;
import java.util.Scanner;

class RandomChars {
	private Random random = new Random(47);
	private static final char[] chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
			.toCharArray();

	public char[] next() {
		int length = random.nextInt(10) + 1;
		char[] result = new char[length];
		for (int i = 0; i < result.length; i++) {
			result[i] = chars[random.nextInt(chars.length)];
		}
		return result;
	}
}

class AdaptedRandomChars implements Readable {
	private RandomChars randomChars = new RandomChars();
	private int count;

	public AdaptedRandomChars(int count) {
		this.count = count;
	}

	@Override
	public int read(CharBuffer cb) throws IOException {
		if (count-- == 0) {
			return -1;
		}
		char[] result = randomChars.next();
		for (char c : result) {
			cb.append(c);
		}
		cb.append(" ");
		return result.length+1;
	}
}

public class Ex16 {
	public static void main(String[] args){
		AdaptedRandomChars random = new AdaptedRandomChars(10);
		Scanner scanner = new Scanner(random);
		while(scanner.hasNext()){
			System.out.println(scanner.next());
		}
	}
}
