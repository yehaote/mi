package mi.practice.java.base.typeinfo.ex;
import static mi.practice.java.base.util.Print.print;
/**
 * 因为没有默认构造函数, 
 * 所以调用Class.newInstance()失败 
 * Can't instantiate
 * @author waf
 */
interface HasBatteries{}
interface Waterproof{}
interface Shoots{}
class Toy{
//	Toy(){}
	Toy(int i){}
}

class FancyToy extends Toy
	implements HasBatteries, Waterproof, Shoots{
	FancyToy(){
		super(1);
	}
}
@SuppressWarnings("rawtypes")
class ToyTest {
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
			c=Class.forName("mi.practice.java.base.typeinfo.ex.FancyToy");
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
public class Ex1 {
	public static void main(String[] args){
		ToyTest.main(args);
	}
}
