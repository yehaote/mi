package mi.practice.java.base.reusing;
/**
 * 练习13
 */
class Demo_Ex13{
	void say(){
		System.out.println("say()");
	}
	
	void say(int i){
		System.out.println("say(int) "+i);
	}
	
	void say(char c){
		System.out.println("say(char) "+c);
	}
}

class Demo2_Ex13 extends Demo_Ex13{
	void say(String s){
		System.out.println("say(String) "+s);
	}
}

public class Ex13 {
	public static void main(String[] args){
		Demo2_Ex13 ex = new Demo2_Ex13();
		ex.say();
		ex.say('x');
		ex.say(7);
		ex.say("hello");
	}
}
