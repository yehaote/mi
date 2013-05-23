package mi.practice.java.base.initialization;

enum Week {
	Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday
}

public class Ex2122 {
	public static void main(String[] args) {
		for (Week week : Week.values()) {
			System.out.println(week.name() + " " + week.ordinal());
		}

		for (Week week : Week.values()) {
			switch (week) {
			case Monday:
				System.out.println("星期一");
				break;
			case Tuesday:
				System.out.println("星期二");
				break;
			case Wednesday:
				System.out.println("星期三");
				break;
			case Thursday:
				System.out.println("星期四");
				break;
			case Friday:
				System.out.println("星期五");
				break;
			case Saturday:
				System.out.println("星期六");
				break;
			case Sunday:
				System.out.println("星期日");
				break;
			}
		}
	}
}