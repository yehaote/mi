package mi.practice.java.base.typeinfo;

import java.lang.reflect.Field;
import java.util.Random;
/**
 * 通过反射同样可以修改field,
 * 除非是编译期常量
 * @author waf
 */
class WithPrivateFinalField{
	private int i=1;//可以修改
	private final int j=2;//可以修改
	private final int k=new Random().nextInt(20);//可以修改
	private final String s ="I'm totally safe";//不可修改
	private String s2="Am I safe?";//可以修改
	@Override
	public String toString(){
		return "i = "+i+", "+"j = "+j+", "+"k = "+k+", "+s+", "+s2;
	}
}
public class ModifyingPrivateFields {
	public static void main(String[] args) throws Exception{
		WithPrivateFinalField pf = new WithPrivateFinalField();
		System.out.println(pf);
		
		Field f = pf.getClass().getDeclaredField("i");
		f.setAccessible(true);
		System.out.println("f.getInt(pf):" +f.getInt(pf));
		f.setInt(pf, 47);
		System.out.println(pf);
		
		f = pf.getClass().getDeclaredField("j");
		f.setAccessible(true);
		System.out.println("f.getInt(pf):" +f.getInt(pf));
		f.setInt(pf, 47);
		System.out.println(pf);
		
		f = pf.getClass().getDeclaredField("k");
		f.setAccessible(true);
		System.out.println("f.getInt(pf):" +f.getInt(pf));
		f.setInt(pf, 47);
		System.out.println(pf);
		
		f=pf.getClass().getDeclaredField("s");
		f.setAccessible(true);
		System.out.println("f.get(pf): "+f.get(pf));
		f.set(pf, "No, you're not!");
		System.out.println(pf);
		
		f=pf.getClass().getDeclaredField("s2");
		f.setAccessible(true);
		System.out.println("f.get(pf): "+f.get(pf));
		f.set(pf, "No, you're not!");
		System.out.println(pf);
	}
}
