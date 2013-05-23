package mi.practice.java.base.enumerated;
/**
 * 因为values()方法是编译器加到enum中的,
 * 当upcast到Enum的时候没有这个方法,
 * 这个时候可以通过Class的getEnumConstants()来获取信息.
 * 这个方法获取类里定义的enum成员.
 */
enum Search {
	HITHER, YON
}

public class UpcastEnum {
	@SuppressWarnings({ "rawtypes", "unused" })
	public static void main(String[] args) {
		Search[] vals = Search.values();
		Enum e = Search.HITHER;
		// e.values(); // 没有values()
		// 可以这么获取
		for (Enum en : e.getClass().getEnumConstants()) {
			System.out.println(en);
		}
	}
}
