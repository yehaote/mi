package mi.practice.java.base.exceptions.ex;

public class Ex5 {
	static int i =10;
	static void f() throws Exception{
		if(i>0){
			throw new Exception();
		}
	}
	public static void main(String[] args){
		boolean goOn=true;
		while(goOn){
			try{
				System.out.println(i);
				f();
				goOn=false;
//				break; //或者可以使用break
			}catch(Exception e){
				e.printStackTrace(System.out);
				i--;
			}
		}
	}
}
