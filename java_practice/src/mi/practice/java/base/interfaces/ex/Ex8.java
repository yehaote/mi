package mi.practice.java.base.interfaces.ex;

import static mi.practice.java.base.util.Print.print;

interface FastFood{
	void makeFast();
	void eatFat();
}
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
class Sandwich extends PortableLunch implements FastFood{
	private Bread b = new Bread();
	private Cheese c = new Cheese();
	private Lettuce l = new Lettuce();
	private Pickle p = new Pickle();
	public Sandwich(){
		print("Sandwich()");
		makeFast();
		eatFat();
	}
	@Override
	public void makeFast() {
		print("FastFood Sanwich makeFast()");
	}
	@Override
	public void eatFat() {
		print("FastFood Sanwich eatFat()");
	}
}

public class Ex8{
	public static void main(String[] args){
		new Sandwich();
	}
}