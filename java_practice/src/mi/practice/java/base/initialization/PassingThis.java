package mi.practice.java.base.initialization;

/**
 * 演示this的用法
 */

class Person {
	public void eatApple(Apple apple) {
		Apple peeled = apple.getPeeled();
		peeled.toString();
		System.out.println("Yummy");
	}
}

class Peeler {
	static Apple peel(Apple apple) {
		// remove peel
		return apple;// Peeled
	}
}

class Apple {
	Apple getPeeled() {
		return Peeler.peel(this);
	}
}

public class PassingThis {
	public static void main(String[] args) {
		new Person().eatApple(new Apple());
	}
}