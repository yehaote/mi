package mi.practice.java.base.enumerated;
/**
 * 当使用enum关键字来声明枚举的时候,
 * java解释成继承java.lang.Enum的类.
 * Enum还实现了Comparable和Serializable接口.
 */
enum Shrubbery {
	GROUND, CRAWLING, HANGING
}

public class EnumClass {
	public static void main(String[] args) {
		for (Shrubbery s : Shrubbery.values()) {
			// 返回位置信息
			System.out.println(s + " ordinal: " + s.ordinal());
			// 位置信息比对?
			System.out.print(s.compareTo(Shrubbery.CRAWLING) + " ");
			System.out.print(s.equals(Shrubbery.CRAWLING) + " ");
			System.out.println(s == Shrubbery.CRAWLING);
			// 枚举的类
			System.out.println(s.getDeclaringClass());
			// 名称
			System.out.println(s.name());
			System.out.println("----------------------");
		}
		// 根据名称(String)和枚举类的class返回枚举对象
		for (String s : "HANGING CRAWLING GROUND".split(" ")) {
			Shrubbery shrub = Enum.valueOf(Shrubbery.class, s);
			System.out.println(shrub);
		}
	}
}
