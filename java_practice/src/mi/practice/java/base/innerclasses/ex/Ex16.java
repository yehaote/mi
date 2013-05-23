package mi.practice.java.base.innerclasses.ex;

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
	public static CycleFactory factory = new CycleFactory() {
		@Override
		public Cycle getCycle() {
			return new Unicycle();
		}
	};
}

class Bicycle implements Cycle {
	public int wheel() {
		return 2;
	}
	public static CycleFactory factory = new CycleFactory() {
		@Override
		public Cycle getCycle() {
			return new Bicycle();
		}
	};
}

class Tricycle implements Cycle {
	public int wheel() {
		return 3;
	}
	public static CycleFactory factory = new CycleFactory() {
		@Override
		public Cycle getCycle() {
			return new Tricycle();
		}
	};
}

public class Ex16 {
	public static void printWheel(CycleFactory cf){
		Cycle cycle = cf.getCycle();
		System.out.println(cycle.wheel()+" wheels");
	}
	public static void main(String[] args){
		printWheel(Unicycle.factory);
		printWheel(Bicycle.factory);
		printWheel(Tricycle.factory);
	}
}