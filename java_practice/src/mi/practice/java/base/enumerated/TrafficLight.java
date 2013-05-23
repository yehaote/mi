package mi.practice.java.base.enumerated;
/**
 * 枚举还有一个很好的用处是它可以在switch中使用
 */
enum Signal {
	GREEN, YELLOW, RED, // 多一个,也没有关系
}

public class TrafficLight {
	Signal color = Signal.RED;

	public void change() {
		switch (color) {
		case RED:
			color = Signal.GREEN;
			break;
		case GREEN:
			color = Signal.YELLOW;
			break;
		case YELLOW:
			color = Signal.RED;
			break;
		}
	}

	@Override
	public String toString() {
		return "The traffic light is " + color;
	}

	public static void main(String[] args) {
		TrafficLight t = new TrafficLight();
		for (int i = 0; i < 7; i++) {
			System.out.println(t);
			t.change();
		}
	}
}
