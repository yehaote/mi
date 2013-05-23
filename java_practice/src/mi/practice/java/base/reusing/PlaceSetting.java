package mi.practice.java.base.reusing;

import static mi.practice.java.base.util.Print.*;
/**
 * 演示结合使用组合和继承
 */
class Plate{
	Plate(int i){
		print("Plate constructor");
	}
}

class DinnerPlate extends Plate{
	DinnerPlate(int i){
		super(i);
		print("DinnerPlate constructor");
	}
}

class Utensil{
	Utensil(int i){
		print("Utensil constructor");
	}
}

class Spoon extends Utensil{
	Spoon(int i){
		super(i);
		print("Spoon constructor");
	}
}

class Fork extends Utensil{
	Fork(int i){
		super(i);
		print("Fork constructor");
	}
}

class Knife extends Utensil{
	Knife(int i){
		super(i);
		print("Kinfe constructor");
	}
}

class Custom{
	Custom(int i){
		print("Custom constructor");
	}
}
@SuppressWarnings("unused")
public class PlaceSetting extends Custom{
	private Spoon sp;
	private Fork fork;
	private Knife knife;
	private DinnerPlate pl;
	
	public PlaceSetting(int i) {
		super(i+1);
		sp = new Spoon(i+2);
		fork = new Fork(i+3);
		knife = new Knife(i+4);
		pl = new DinnerPlate(i+5);
		print("PlaceSetting constructor");
	}
	
	public static void main(String[] args){
		PlaceSetting x = new PlaceSetting(7);
	}
}
