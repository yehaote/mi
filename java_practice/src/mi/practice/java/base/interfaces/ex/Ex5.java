package mi.practice.java.base.interfaces.ex;

import mi.practice.java.base.interfaces.ex.ex5.People;

class Student implements People{

	@Override
	public void say() {
		System.out.println(this+".say()");
	}

	@Override
	public void eat() {
		System.out.println(this+".eat()");
	}

	@Override
	public void think() {
		System.out.println(this+".think()");
	}
	
	@Override
	public String toString(){
		return "Student";
	}
	
}
public class Ex5 {
	public static void main(String[] args){
		People p = new Student();
		p.say();
		p.eat();
		p.think();
	}
}
