package mi.practice.java.base.exceptions.ex;

import java.util.Random;

@SuppressWarnings("serial")
class Ex13E1 extends Exception{
}
@SuppressWarnings("serial")
class Ex13E2 extends Exception{
}
@SuppressWarnings("serial")
class Ex13E3 extends Exception{
}
public class Ex13 {
	static Random rand;
	static void f() throws Ex13E1,Ex13E2,Ex13E3{
		int i = rand.nextInt(3);
		switch(i){
		case 0: throw new Ex13E1();
		case 1: throw new Ex13E2();
		case 2: throw new Ex13E3();
		}
	}
	public static void main(String[] args){
		try{
			f();
		}catch(Exception e){
			e.printStackTrace(System.out);
		}finally{
			System.out.println("Even if unllpoint exception, this is always run");
		}
	}
}
