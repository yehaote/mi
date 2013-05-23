package mi.practice.java.base.innerclasses.ex;
class Ex15Super{
	protected int i=0;
	Ex15Super(int i){
		this.i=i;
		System.out.println("Ex15Super(int), i = "+i);
	}
	void display(){
		System.out.println(" i = "+i);
	}
}
public class Ex15 {
	Ex15Super getSuper(final int num){
		return new Ex15Super(num){
			{
				i=i*i;
			}
		};
	}
	public static void main(String[] args){
		Ex15 ex = new Ex15();
		Ex15Super sup = ex.getSuper(10);
		sup.display();
	}
}
