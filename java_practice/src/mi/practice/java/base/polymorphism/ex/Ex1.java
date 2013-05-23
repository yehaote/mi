package mi.practice.java.base.polymorphism.ex;

/**
 * ex1, ex5, ex17
 */
class Cycle {
	private String name = "cycle";

	@Override
	public String toString() {
		return name;
	}

	public int wheels() {
		return 0;
	}

}

class Unicycle extends Cycle {
	private String name = "unicycle";

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int wheels() {
		return 1;
	}

	public void balance() {
	}
}

class Bicycle extends Cycle {
	private String name = "bicycle";

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int wheels() {
		return 2;
	}

	public void balance() {
	}
}

class Tricycle extends Cycle {
	private String name = "tricycle";

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int wheels() {
		return 3;
	}
}

public class Ex1 {
	public static void ride(Cycle c) {
		System.out.println("ride() " + c.wheels());
	}

	public static void main(String[] args) {
		Unicycle unicycle = new Unicycle();
		Bicycle bicycle = new Bicycle();
		Tricycle tricycle = new Tricycle();
		ride(unicycle);
		ride(bicycle);
		ride(tricycle);
	}
}

class Ex17 {
	public static void main(String[] args){
		Cycle[] cycles= {new Unicycle(), new Bicycle(), new Tricycle()};
//		cycles[0].balance();
//		cycles[1].balance();
//		cycles[2].balance();
		((Unicycle)cycles[0]).balance();
		((Bicycle)cycles[1]).balance();
//		((Tricycle)cycles[2]).balance();
	}
}
