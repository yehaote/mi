package mi.practice.java.base.exceptions.ex;
class Ex2Item{
	void f(){}
}
public class Ex2 {
	@SuppressWarnings("null")
	public static void main(String[] args){
		Ex2Item item = null;
		try{
			item.f();
		}catch(NullPointerException e){
			e.printStackTrace(System.out);
		}
	}
}
