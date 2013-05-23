package mi.practice.java.base.innerclasses.ex;
interface IEx11{
	void say();
}

public class Ex11 {
	private class Ex11Inner implements IEx11{
		public void say(){
			System.out.println(getClass().getSimpleName()+".say()");
		}
	}
	IEx11 getI(){
		return new Ex11Inner();
	}
	@SuppressWarnings("unused")
	public static void main(String[] args){
		Ex11 ex = new Ex11();
		IEx11 iex = ex.getI();
		iex.say();
		Ex11.Ex11Inner exInner = ex.new Ex11Inner();
		((Ex11.Ex11Inner)iex).say();
	}
}

class Ex11Demo{
	public static void main(String[] args){
		Ex11 ex = new Ex11();
		IEx11 iex = ex.getI();
		iex.say();
//		Ex11.Ex11Inner exInner = ex.new Ex11Inner();
//		((Ex11.Ex11Inner)iex).say();//不能转换, 不能指定类型Ex11.Ex11Inner
	}
}
