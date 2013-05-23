package mi.practice.java.base.innerclasses.ex;


interface IEx21{
	int value();
	class IEx21Nest implements IEx21{
		static void dispaly(IEx21 iex){
			System.out.println(iex.value());
		}
		private int i=11;
		public int value(){
			return i;
		}
	}
}
public class Ex21 {
	public static void main(String[] args){
		IEx21 iex = new IEx21.IEx21Nest();
		IEx21.IEx21Nest.dispaly(iex);
	}
}
