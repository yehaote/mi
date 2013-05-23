package mi.practice.java.base.initialization;

import static mi.practice.java.base.util.Print.*;

public class Flower {
	int petalsCount=0;
	String s="initial value";
	
	Flower(int petals){
		petalsCount=petals;
		print("Constructor w/ int arg only, petalsCount= "+petalsCount);
	}
	
	Flower(String ss){
		print("Constuctor w/ String arg only, s = "+ss);
		s=ss;
	}
	
	Flower(String s, int petals){
		this(petals);	//Must be the first command
//		this(s); //Can't call two!
		this.s=s;
		print("String & int args");
	}
	
	Flower(){
		this("hi",47);
		print("Defalut constructor (no args)");
	}
	
	void printPetalsCount(){
//		this(11); //Not inside non-constructor!
		print("petalCount = "+petalsCount + " s = "+s);
	}
	
	public static void main(String[] args){
		Flower x=  new Flower();
		x.printPetalsCount();
	}
	

}
