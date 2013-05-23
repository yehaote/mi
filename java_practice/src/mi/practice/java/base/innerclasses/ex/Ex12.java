package mi.practice.java.base.innerclasses.ex;
interface IEx12{
	void modify();
}
public class Ex12 {
	private int i=5;
	private IEx12 iex(){
		return new IEx12() {
			@Override
			public void modify() {
				i++;
			}
		}; 
	}
	public void modify(){
		IEx12 iex = iex();
		iex.modify();
	}
	public void display(){
		System.out.println("i = "+i);
	}
	public static void main(String[] args){
		Ex12 ex = new Ex12();
		ex.display();
		ex.modify();
		ex.display();
	}
}
