package mi.practice.java.base.innerclasses.ex;

import mi.practice.java.base.innerclasses.ex.ex6.Ex6Demo;
import mi.practice.java.base.innerclasses.ex.ex6.i.Ex6Interface;

public class Ex6 extends Ex6Demo{
	public Ex6Interface getInner(){
		return new Ex6Inner(); //注意this.new 
	}
	
	public static void main(String[] args){
		Ex6 ex = new Ex6();
		Ex6Interface face = ex.getInner();
		face.say();
	}
}
