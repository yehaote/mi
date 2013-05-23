package mi.practice.java.base.exceptions.ex;
@SuppressWarnings("serial")
class Ex1Exception extends Exception{
	Ex1Exception(String info){
		super(info);
	}
}
public class Ex1 {
	public static void main(String[] args){
		try{
			throw new Ex1Exception("try block");
		}catch (Ex1Exception e) {
			System.out.println(e.getMessage());
		}finally{
			
			System.out.println("I'am here");
		}
		
	}
}
