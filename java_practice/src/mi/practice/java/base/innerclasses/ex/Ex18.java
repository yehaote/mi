package mi.practice.java.base.innerclasses.ex;

public class Ex18 {
	Ex18(){
		System.out.println("Ex18()");
	}
	static class Ex18Nested{
		public Ex18Nested() {
			System.out.println("Ex18Nested()");
		}
		void say(){
			System.out.println("Nested.say()");
		}
	}
	public static void main(String[] args){
		Ex18Nested nested = new Ex18Nested();
		nested.say();
	}
}
