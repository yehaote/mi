package mi.practice.java.base.typeinfo.ex;
/**
 * getInterfaces()是返回直接实现的接口,
 * 由父类实现的接口不返回
 * @author waf
 */
interface IEx2{}
class Ex2Demo extends FancyToy
	implements IEx2{
}
class FancyToyEx2 extends Toy
	implements HasBatteries, Waterproof, Shoots, IEx2{
	FancyToyEx2(int i) {
		super(i);
	}
}
@SuppressWarnings("rawtypes")
public class Ex2 {
	public static void main(String[] args){
		try {
			Class ex2C=Class.forName("mi.practice.java.base.typeinfo.ex.FancyToyEx2");
			Class[] interfaces=ex2C.getInterfaces();
			for(Class inter:interfaces){
				System.out.println(inter.getCanonicalName());
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println();
		try {
			Class ex2C=Class.forName("mi.practice.java.base.typeinfo.ex.Ex2Demo");
			Class[] interfaces=ex2C.getInterfaces();
			for(Class inter:interfaces){
				System.out.println(inter.getCanonicalName());
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
