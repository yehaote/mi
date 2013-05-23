package mi.practice.java.base.interfaces.ex;

abstract class Rodent {
	abstract void say();
}

class Mouse extends Rodent {
	@Override
	public void say() {
		System.out.println("Mouse zzzzz");
	}
}

class Gerbil extends Mouse {
	@Override
	public void say() {
		System.out.println("Gerbil zzzzz");
	}
}

class Hamster extends Mouse {
	@Override
	public void say() {
		System.out.println("Hamster zzzzz");
	}
}

public class Ex1 {
	public static void main(String[] args) {
		Rodent[] rodents = { new Mouse(), new Gerbil(), new Hamster() };
		for (Rodent rodent : rodents) {
			rodent.say();
		}
	}
}
