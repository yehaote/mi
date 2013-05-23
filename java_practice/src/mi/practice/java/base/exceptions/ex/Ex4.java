package mi.practice.java.base.exceptions.ex;
@SuppressWarnings("serial")
class Ex4Exception extends Exception{
	String info;
	Ex4Exception(String info) {
		this.info=info;
	}
	void sayInfo(){
		System.out.println("sayInfo(): "+info);
	}
}
public class Ex4 {
	public static void main(String[] args){
		try{
			throw new Ex4Exception("hello ex4Exception");
		}catch(Ex4Exception e){
			e.sayInfo();
		}
	}
}
