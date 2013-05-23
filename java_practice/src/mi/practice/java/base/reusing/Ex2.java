package mi.practice.java.base.reusing;

public class Ex2 extends Detergent {
	@Override
	public void scrub(){
		append(" Ex2's scrub");
		super.scrub();
	}
	
	public void sterilize(){
		append(" sterilize()");
	}
	
	public static void main(String[] args){
		Ex2 ex = new Ex2();
		ex.scrub();
		ex.sterilize();
		System.out.println(ex);
	}
}
