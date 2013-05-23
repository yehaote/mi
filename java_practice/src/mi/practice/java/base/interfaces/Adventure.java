package mi.practice.java.base.interfaces;

/**
 * 在java中不能同时继承好几个类,
 * 不过可以同时实现很多个接口
 * java继承必须在实现接口之前
 * java中使用接口的两个理由:
 * 1.可以向上转换成多个类型
 * 2.跟抽象类一样, 不能直接实例化
 */
interface CanFight {
	void fight();
}

interface CanSwim {
	void swim();
}

interface CanFly {
	void fly();
}

interface CanClimb {
	void climb();
}

class ActionCharacter {
	public void fight() {
	}
}

class Hero extends ActionCharacter implements CanFight, CanSwim, CanFly,
		CanClimb {
	@Override
	public void swim() {
	}

	@Override
	public void fly() {
	}

	@Override
	public void climb() {
	}
}

public class Adventure {
	public static void t(CanFight x) {
		x.fight();
	}

	public static void u(CanSwim x) {
		x.swim();
	}

	public static void v(CanFly x) {
		x.fly();
	}
	
	public static void y(CanClimb x){
		x.climb();
	}

	public static void w(ActionCharacter x) {
		x.fight();
	}

	public static void main(String[] args) {
		Hero h = new Hero();
		t(h);
		u(h);
		v(h);
		w(h);
		y(h);
	}
}
