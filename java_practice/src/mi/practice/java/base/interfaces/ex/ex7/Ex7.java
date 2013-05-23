package mi.practice.java.base.interfaces.ex.ex7;

interface Rodent {
	void say();
}

class Mouse implements Rodent {
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

public class Ex7 {
	public static void main(String[] args) {
		Rodent[] rodents = { new Mouse(), new Gerbil(), new Hamster() };
		for (Rodent rodent : rodents) {
			rodent.say();
		}
	}
}
