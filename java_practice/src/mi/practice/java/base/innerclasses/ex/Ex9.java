package mi.practice.java.base.innerclasses.ex;
interface Ex9Inter{
	void say();
}
public class Ex9 {
	void say(){
		class Ex9Inner implements Ex9Inter{
			public void say(){
				System.out.println("Ex9Inner.say()");
			}
		}
		Ex9Inter inner = new Ex9Inner();
		inner.say();
	}
	public static void main(String[] args){
		Ex9 ex = new Ex9();
		ex.say();
	}
}
