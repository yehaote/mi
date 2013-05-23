package mi.practice.java.base.exceptions.ex;

import mi.practice.java.base.exceptions.OnOffException1;
import mi.practice.java.base.exceptions.OnOffException2;
import mi.practice.java.base.exceptions.Switch;

public class Ex14 {
	private static Switch sw = new Switch();
	public static void f() throws OnOffException1, OnOffException2{
		throw new RuntimeException();
	}
	public static void main(String[] args){
		try{
			sw.on();
			f();
			sw.off();
		}catch(OnOffException1 e){
			System.out.println("OnOffException1");
			sw.off();
		}catch(OnOffException2 e){
			System.out.println("OnOffException2");
			sw.off();
		}finally{
			System.out.println(sw.toString());
		}
	}
}
