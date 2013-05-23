package mi.practice.java.base.exceptions.ex;

import mi.practice.java.base.exceptions.OnOffException1;
import mi.practice.java.base.exceptions.OnOffException2;
import mi.practice.java.base.exceptions.Switch;

public class Ex15 {
	static Switch sw = new Switch();
	public static void f() throws OnOffException1, OnOffException2{
		throw new RuntimeException();
	}
	public static void main(String[] args){
		try{
			sw.on();
			f();
		}catch(OnOffException1 e){
			System.out.println("OnOffException1");
		}catch(OnOffException2 e){
			System.out.println("OnOffException2");
		}finally{
			sw.off();
		}
	}
}
