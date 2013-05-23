package mi.practice.java.base.reusing;

public class Ex18 {
	static int i=9;
	static final int I1 =i++;
	final int I2=i++;
	
	@SuppressWarnings("static-access")
	public static void main(String[] args){
		Ex18 ex1 = new Ex18();
		System.out.println(ex1.i+" "+ex1.I1+" "+ex1.I2);
		ex1.i++;
//		ex1.I1++;
//		ex1.I2++;
		Ex18 ex2 = new Ex18();
		System.out.println(ex2.i+" "+ex2.I1+" "+ex2.I2);
	}
}
