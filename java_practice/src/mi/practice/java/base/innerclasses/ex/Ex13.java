package mi.practice.java.base.innerclasses.ex;

interface IEx13{
	void say();
}
public class Ex13 {
	IEx13 getI(){
		return new IEx13() {
			@Override
			public void say() {
				System.out.println("Ex13Anonymous.say()");
			}
		};
	}
	void say(){
		IEx13 inner = getI();
		inner.say();
	}
	public static void main(String[] args){
		Ex13 ex = new Ex13();
		ex.say();
	}
}