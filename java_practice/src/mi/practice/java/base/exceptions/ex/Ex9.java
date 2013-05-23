package mi.practice.java.base.exceptions.ex;

import java.util.Random;

@SuppressWarnings("serial")
class Ex9E1 extends Exception{
}
@SuppressWarnings("serial")
class Ex9E2 extends Exception{
}
@SuppressWarnings("serial")
class Ex9E3 extends Exception{
}
public class Ex9 {
	static Random rand = new Random();
	static void f() throws Ex9E1,Ex9E2,Ex9E3{
		int i = rand.nextInt(3);
		switch(i){
		case 0: throw new Ex9E1();
		case 1: throw new Ex9E2();
		case 2: throw new Ex9E3();
		}
	}
	public static void main(String[] args){
		try{
			f();
		}catch(Exception e){
			e.printStackTrace(System.out);
		}
	}
}
