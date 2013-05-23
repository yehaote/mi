package mi.practice.java.base.typeinfo.ex;
/**
 * char数组是object
 * int 是基本类型
 * @author waf
 */
public class Ex10 {
	@SuppressWarnings("rawtypes")
	public static void main(String[] args){
		char[] chars=new char[]{};
		Class c = chars.getClass();
		System.out.println(c);
		System.out.println(c.isPrimitive());
		System.out.println(c.getSuperclass());
		Class intc=int.class;
		System.out.println(intc);
		System.out.println(intc.isPrimitive());
		System.out.println(intc.getSuperclass());
	}
}
