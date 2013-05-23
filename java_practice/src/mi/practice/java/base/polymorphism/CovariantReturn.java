package mi.practice.java.base.polymorphism;

/**
 * 默认在子类中覆盖父类的函数的时候,
 * 返回的Type必须相同
 * 在Java SE5以后可以返回其子类
 * 这里Mill.Process()返回Grain
 * 它的继承类WheatMill.process()覆盖父类方法, 
 * 并返回Grain的子类Wheat
 */
class Grain {
	public String toString() {
		return "Grain";
	}
}

class Wheat extends Grain {
	public String toString() {
		return "Wheat";
	}
}

class Mill {
	Grain process() {
		return new Grain();
	}
}

class WheatMill extends Mill {
	@Override
	Wheat process() {
		return new Wheat();
	}
}

public class CovariantReturn {
	public static void main(String[] args) {
		Mill m = new Mill();
		Grain g = m.process();
		System.out.println(g);
		m = new WheatMill();
		g = m.process();
		System.out.println(g);
	}
}
