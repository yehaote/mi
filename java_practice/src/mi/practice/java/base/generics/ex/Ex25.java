package mi.practice.java.base.generics.ex;
interface OneI{
	void sayOne();
}
interface TwoI{
	void sayTwo();
}
class HaveOneAndTwo implements OneI, TwoI{
	@Override
	public void sayTwo() {
	}
	@Override
	public void sayOne() {
	}
}
public class Ex25 {
	public static <TypeOne extends OneI>
		void testOne(TypeOne one){
		one.sayOne();
	}
	public static <TypeTwo extends TwoI>
		void testTwo(TypeTwo two){
	two.sayTwo();
	}
	public static void main(String[] args){
		HaveOneAndTwo haveOneAndTwo = new HaveOneAndTwo();
		testOne(haveOneAndTwo);
		testTwo(haveOneAndTwo);
	}
}
