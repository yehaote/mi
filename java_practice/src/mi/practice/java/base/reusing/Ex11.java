package mi.practice.java.base.reusing;

import static mi.practice.java.base.util.Print.print;
/**
 * 练习11
 */
class Clearnser2 {
	private String s= "Clearnser2";
	public void append(String a){
		s += a;
	}
	public void dilute(){
		append(" dilute()");
	}
	public void apply(){
		append(" apply()");
	}
	public void scrub(){
		append(" scrub()");
	}
	public String toString(){
		return s;
	}
	public static void main(String[] args){
		Clearnser2 x = new Clearnser2();
		x.dilute();
		x.apply();
		x.scrub();
		print(x);
	}
}

class Detergent2{
	private Clearnser clearnser = new Clearnser();
	
	public void append(String a){
		clearnser.append(a);
	}
	public void dilute(){
		clearnser.dilute();
	}
	public void apply(){
		clearnser.apply();
	}
	public void scrub(){
		append(" Detergent2.scrub()");
		clearnser.scrub();
	}
	
	public void foam(){
		append(" foam()");
	}
	
	public String toString(){
		return clearnser.toString();
	}
	
	public static void main(String[] args){
		Detergent2 x = new Detergent2();
		x.dilute();
		x.apply();
		x.scrub();
		x.foam();
		print(x);
		print("Testing base class:");
		Clearnser.main(args);
	}
}
public class Ex11 {
	public static void main(String[] args){
		Detergent2.main(args);
	}
}
