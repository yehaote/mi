package mi.practice.java.base.generics;
/**
 * 因为擦写,所以java在进行操作的时候不能制定f(),
 * 不知道object有这个f()的方法
 * @author waf
 * @param <T>
 */
class Manipulator <T>{
	@SuppressWarnings("unused")
	private T obj;
	public Manipulator(T x){
		obj=x;
	}
	public void manipulate(){
//		obj.f();//不能编译, 找不到f()
	}
}

public class Manipulation{
	public static void main(String[] args){
		HasF hf = new HasF();
		Manipulator<HasF> manipulator=new Manipulator<HasF>(hf);
		manipulator.manipulate();
	}
}
