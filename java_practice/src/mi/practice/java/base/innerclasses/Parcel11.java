package mi.practice.java.base.innerclasses;

/**
 * Nested classes(static inner class)
 * 内嵌类
 * 一般的内部类,不能包含static数据, static成员,
 * 或者内嵌类, 内嵌类都可以
 */
public class Parcel11 {
	private static class ParcelContents implements Contents {
		private int i = 11;

		@Override
		public int value() {
			return i;
		}
	}

	protected static class ParcelDestination implements Destination {
		private String label;

		private ParcelDestination(String whereTo) {
			label = whereTo;
		}

		@Override
		public String readLabel() {
			return label;
		}

		public static void f() {
		}

		static int x = 10;

		static class AnotherLevel {
			public static void f() {
			}

			static int x = 10;
		}
	}

	public static Destination destinaition(String s) {
		return new ParcelDestination(s);
	}

	public static Contents contents() {
		return new ParcelContents();
	}

	public static void main(String[] args) {
		Contents c = contents();
		c.value();
		Destination d = destinaition("Tasmania");
		d.readLabel();
	}
}
