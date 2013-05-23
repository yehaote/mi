package mi.practice.java.base.reusing;
/**
 * 当把一个子类转换成父类的时候, 
 * 如果子类覆盖了父类的方法
 * 那么在转换后的父类调用的时候会直接调用这个覆盖的方法
 */
class Ex17_Amphibian{
	public void sayHello(){
		System.out.println("Ex17_Amphibian sayHello()");
	}
}

class Ex17_Forg extends Ex17_Amphibian{
	@Override
	public void sayHello(){
		System.out.println("Ex17_Forg sayHello()");
	}
}
public class Ex17 {
	public static void main(String[] args){
		Ex17_Forg forg = new Ex17_Forg();
		Ex17_Amphibian amphibian = forg;
		amphibian.sayHello();
	}
}
