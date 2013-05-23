package mi.practice.java.base.typeinfo;

public class GenericToyTest {
	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception{
		Class<Integer> intClass =Integer.class;
		Integer intNum=intClass.newInstance();
		Class<? super Integer> up = intClass.getSuperclass();
//		Class<Number> up2 = intClass.getSuperclass(); // 这句编译通不过
		Object o = up.newInstance(); //只生成Object
	}
}
