package mi.practice.java.base.innerclasses.ex;
interface IEx20{
	void say();
	class IEx20Nest implements IEx20{
		@Override
		public void say() {
			System.out.println("IEx20Nest.say()");
		}
		
	}
}
public class Ex20 {
	public static void main(String[] args){
		IEx20 iex = new IEx20.IEx20Nest();
		iex.say();
	}
}
