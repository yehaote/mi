package mi.practice.java.base.interfaces.ex;

interface Cycle {
	int wheel();
}

interface CycleFactory {
	Cycle getCycle();
}

class Unicycle implements Cycle {
	public int wheel() {
		return 1;
	}
}

class UnicycleFactory implements CycleFactory {
	public Cycle getCycle() {
		return new Unicycle();
	}
}

class Bicycle implements Cycle {
	public int wheel() {
		return 2;
	}
}

class BicycleFactory implements CycleFactory {
	public Cycle getCycle() {
		return new Bicycle();
	}
}

class Tricycle implements Cycle {
	public int wheel() {
		return 3;
	}
}

class TricycleFactory implements CycleFactory {
	public Cycle getCycle() {
		return new Tricycle();
	}
}

public class Ex18 {
	public static void printWheel(CycleFactory cf){
		Cycle cycle = cf.getCycle();
		System.out.println(cycle.wheel()+" wheels");
	}
	public static void main(String[] args){
		printWheel(new UnicycleFactory());
		printWheel(new BicycleFactory());
		printWheel(new TricycleFactory());
	}
}
