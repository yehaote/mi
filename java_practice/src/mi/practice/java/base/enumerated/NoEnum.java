package mi.practice.java.base.enumerated;
/**
 * getEnumConstants()所有的Class都能调用
 */
public class NoEnum {
	public static void main(String[] args) {
		Class<Integer> intClass = Integer.class;
		try {
			for (Object en : intClass.getEnumConstants()) {
				System.out.println(en);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
