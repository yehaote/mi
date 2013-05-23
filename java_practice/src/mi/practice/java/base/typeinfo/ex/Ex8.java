package mi.practice.java.base.typeinfo.ex;

public class Ex8 {
	@SuppressWarnings("rawtypes")
	public static void printInfo(Object o){
		Class c =o.getClass();
		System.out.println(c);
		Class superClass = c.getSuperclass();
		while(superClass!=null){
			System.out.println(superClass);
			superClass=superClass.getSuperclass();
		}
	}
	
	public static void main(String[] args){
		Ex8 ex= new Ex8();
		printInfo(ex);
	}
}
