package mi.practice.java.base.enumerated.cartoons;

import java.util.Random;

import mi.practice.java.base.util.Generator;

/**
 * enum定义的类型不能继承其他的类,
 * 因为编译器会帮它继承Enum.(Java不支持多重继承)
 * 但是可以实现接口.
 */
enum CartoonCharacter implements Generator<CartoonCharacter> {
	SLAPPY, SPANKY, PUNCHY, SILLY, BOUNCY, NUTTY, BOB;
	private Random rand = new Random(47);

	@Override
	public CartoonCharacter next() {
		return values()[rand.nextInt(values().length)];
	}
}

public class EnumImplementation {
	public static <T> void printNext(Generator<T> rg) {
		System.out.print(rg.next() + ", ");
	}

	public static void main(String[] args) {
		CartoonCharacter cc = CartoonCharacter.BOB;
		for (int i = 0; i < 10; i++) {
			printNext(cc);
		}
	}
}
