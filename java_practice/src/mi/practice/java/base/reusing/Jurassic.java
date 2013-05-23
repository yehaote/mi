package mi.practice.java.base.reusing;

/***
 * 可以把类定义成final, 其他的类就不能从这个类继承
 * 但是这个类本身还是可以改变的
 */

class SmallBrain{}

final class Dinosaur{
	int i=7;
	int j=1;
	SmallBrain x = new SmallBrain();
	void f(){}
}

//class Further extends Dinosaur{
//}

public class Jurassic {
	public static void main(String[] args){
		Dinosaur n = new Dinosaur();
		n.f();
		n.j=40;
		n.i++;
	}
}