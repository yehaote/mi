package mi.practice.java.base.innerclasses.ex;
interface IEx10{
	void say();
}
public class Ex10 {
	void innerSay(boolean b){
		if(b){
			class Ex10Inner implements IEx10{
				public void say(){
					System.out.println("Ex10.Ex10Inner.say()");
				}
			}
			IEx10 iex = new Ex10Inner();
			iex.say();
		}
	}
	public static void main(String[] args){
		Ex10 ex = new Ex10();
		ex.innerSay(true);
		ex.innerSay(false);
	}
}
