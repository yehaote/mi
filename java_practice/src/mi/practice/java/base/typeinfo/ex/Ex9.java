package mi.practice.java.base.typeinfo.ex;

import java.lang.reflect.Field;

@SuppressWarnings("unused")
class Ex9Demo{
	private String str;
	protected int i;
	double d;
	public float f;
}
@SuppressWarnings("rawtypes")
public class Ex9 {
	public static void getFields(Object o){
		Class c = o.getClass();
		//无论是什么访问限定都是可以取到的
		Field[] fields =c.getDeclaredFields();
		for(Field f: fields){
			System.out.println(f);
		}
	}
	
	public static void main(String[] args){
		Ex9Demo demo = new Ex9Demo();
		getFields(demo);
	}
}
