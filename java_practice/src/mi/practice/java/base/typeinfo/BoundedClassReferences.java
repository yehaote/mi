package mi.practice.java.base.typeinfo;
/**
 * ? extend 代表所有从它继承的, 
 * 因为Number.class 不是Double.class的父类,
 * 虽说Double继承自Number.
 * 所以要表示的话需要使用? extends 这样的形式
 * @author waf
 */
public class BoundedClassReferences {
	@SuppressWarnings("unused")
	public static void main(String[ ] args){
		Class<? extends Number> bounded = int.class;
		bounded = double.class;
		bounded = int.class;
	}
}
