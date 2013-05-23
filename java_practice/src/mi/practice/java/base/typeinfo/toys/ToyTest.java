package mi.practice.java.base.typeinfo.toys;
import static mi.practice.java.base.util.Print.*;
/**
 * 获取Class的方法, 如果没有实体类可以使用Class.forName()来获取,
 * 如果有实体类, 可以使用.getClass()方法来获取
 * @author waf
 */
interface HasBatteries{}
interface Waterproof{}
interface Shoots{}
class Toy{
//	private Toy(){}
	Toy(){}
	Toy(int i){}
}

class FancyToy extends Toy
	implements HasBatteries, Waterproof, Shoots{
	FancyToy(){
		super(1);
	}
}
@SuppressWarnings("rawtypes")
public class ToyTest {
	static void printInfo(Class cc){
		//显示全限定名, 并判断是否接口
		print("Class name:"+cc.getName()+
				"is interface? ["+cc.isInterface()+"]");
		//获取简称
		print("Simple name: "+cc.getSimpleName());
		//获取全限定名
		print("Canonical name: "+cc.getCanonicalName());
	}
	public static void main(String[] args){
		Class c =null;
		try{
//			c=Class.forName("FancyToy");
			//必须使用全限定名称
			c=Class.forName("mi.practice.java.base.typeinfo.toys.FancyToy");
		}catch(ClassNotFoundException e){
			print("Can't find FancyToy");
			System.exit(1);
		}
		printInfo(c);
		//获取接口
		for(Class face: c.getInterfaces()){
			printInfo(face);
		}
		//通过Class获取父类
		Class up=c.getSuperclass();
		Object obj =null;
		try{
			//需要默认的构造函数, 构造一个新的实体
			obj = up.newInstance();
		}catch(InstantiationException e){
			print("Can't instantiate");
			System.exit(1);
		}catch(IllegalAccessException e){
			print("Can't access");
			System.exit(1);
		}
		printInfo(obj.getClass());
	}
}
