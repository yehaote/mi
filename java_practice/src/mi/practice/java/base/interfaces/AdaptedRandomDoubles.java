package mi.practice.java.base.interfaces;

import java.io.IOException;
import java.nio.CharBuffer;
import java.util.Scanner;

public class AdaptedRandomDoubles extends RandomDoubles implements Readable {
	private int count;

	public AdaptedRandomDoubles(int count) {
		this.count = count;
	}

	@Override
	public int read(CharBuffer cb) throws IOException {
		if (count-- == 0) {
			return -1;
		}
		String add = Double.toString(next()) + " ";
		cb.append(add);
		return add.length();
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(new AdaptedRandomDoubles(7));
		while (scanner.hasNext()) {
			System.out.println(scanner.next());
		}
	}

}
