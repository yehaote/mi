package mi.practice.java.base.reusing;

import static mi.practice.java.base.util.Print.*;
/***
 * 当有继承的时候进行初始化, 
 * 会先初始化父类的静态成员, 然后再是子类的静态成员, 
 * 接下来才是父类的构造, 子类的构造
 * 包含Ex24
 */
@SuppressWarnings("unused")
class Insect{
	private int i=9;
	protected int j;
	Insect(){
		print("i = "+i+", j = "+j);
	}
	private static int x1=printInit("static Insect.x1 initialized");
			
	static int printInit(String s){
		print(s);
		return 47;
	}
}
@SuppressWarnings("unused")
public class Beetle extends Insect{
	private int k = printInit("Beetle.k initialized");
	public Beetle(){
		print("k = "+k);
		print("j = "+j);
	}
	
	private static int x2=printInit("static Beetle.x2 initialized");
	public static void main(String[] args){
		print("Beetle constructor");
		Beetle b = new Beetle();
	}
}
@SuppressWarnings("unused")
class Scarab extends Beetle{
	private int l = printInit("Scarab.l initialized");
	public Scarab(){
		print("l = "+l);
		print("j = "+j);
	}
	private static int x3=printInit("static Scarab.x3 initialized");
	public static void main(String[] args){
		print("First static initialized");
		print("new Scarab object");
		Scarab s = new Scarab();
	}
}