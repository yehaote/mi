package mi.practice.java.base.polymorphism.ex;

class AlertStatus {
	public void warn() {
		System.out.println("AlertStatus.warn()");
	}
}

class RedStatus extends AlertStatus {
	@Override
	public void warn() {
		System.out.println("RedStatus.warn()");
	}
}

class YellowStatus extends AlertStatus {
	@Override
	public void warn() {
		System.out.println("YellowStatus.warn()");
	}
}

class BlackStatus extends AlertStatus {
	@Override
	public void warn() {
		System.out.println("BlackStatus.warn()");
	}
}

class Starship {
	private AlertStatus as = new RedStatus();
	private int i = 0;

	public void change() {
		i++;
		switch (i % 3) {
		default:
		case 0:
			as = new RedStatus();
			break;
		case 1:
			as = new YellowStatus();
			break;
		case 2:
			as = new BlackStatus();
			break;
		}
	}

	public void warn() {
		as.warn();
	}
}

public class Ex16 {
	public static void main(String[] args) {
		Starship ss = new Starship();
		for (int i = 0; i < 10; i++) {
			ss.warn();
			ss.change();
		}
	}
}
