package mi.practice.java.base.polymorphism;
import static mi.practice.java.base.util.Print.*;
/**
 * 关于构造函数的多态
 * 构造函数是不能多态的, 因为它是static方法, 只是它是隐式的
 * 
 */
class Meal{
	Meal(){
		print("Meal()");
	}
}

class Bread{
	Bread(){
		print("Bread()");
	}
}

class Cheese{
	Cheese(){
		print("Cheese()");
	}
}

class Lettuce{
	Lettuce(){
		print("Lettuce()");
	}
}

class Pickle{
	Pickle(){
		print("Pickle()");
	}
}

class Lunch extends Meal{
	Lunch(){
		print("Lunch()");
	}
}

class PortableLunch extends Lunch{
	PortableLunch(){
		print("PortableLunch()");
	}
}
@SuppressWarnings("unused")
public class Sandwich extends PortableLunch{
	private Bread b = new Bread();
	private Cheese c = new Cheese();
	private Lettuce l = new Lettuce();
	private Pickle p = new Pickle();
	public Sandwich(){
		print("Sandwich()");
	}
	public static void main(String[] args){
		new Sandwich();
	}
}
