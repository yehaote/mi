package mi.practice.java.base.reusing;
import static mi.practice.java.base.util.Print.*;
/**
 * 继承, 子类继承父类所有的方法和属性
 */
class Clearnser {
	private String s= "Clearnser";
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
		Clearnser x = new Clearnser();
		x.dilute();
		x.apply();
		x.scrub();
		print(x);
	}
}

public class Detergent extends Clearnser{
	public void scrub(){
		append(" Detergent.scrub()");
		super.scrub();//调用父类的版本
	}
	
	public void foam(){
		append(" foam()");
	}
	
	public static void main(String[] args){
		Detergent x = new Detergent();
		x.dilute();
		x.apply();
		x.scrub();
		x.foam();
		print(x);
		print("Testing base class:");
		Clearnser.main(args);
	}
}
