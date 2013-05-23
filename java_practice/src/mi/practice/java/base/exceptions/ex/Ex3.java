package mi.practice.java.base.exceptions.ex;

public class Ex3 {
	public static void main(String[] args){
		int[] ints = new int[2];
		try{
			System.out.println(ints[0]);
			System.out.println(ints[1]);
			System.out.println(ints[2]);
		}catch(ArrayIndexOutOfBoundsException e){
			e.printStackTrace(System.out);
		}
		
	}
}
