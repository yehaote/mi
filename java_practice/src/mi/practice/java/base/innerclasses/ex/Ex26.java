package mi.practice.java.base.innerclasses.ex;

import mi.practice.java.base.innerclasses.ex.WithInner2.Inner2;

class WithInner {
	class Inner {
		int num;

		Inner(int i) {
			num = i;
		}
		
		void sayNum(){
			System.out.println("num = "+num);
		}
	}
}


class WithInner2 {
	class Inner2 extends WithInner.Inner{
		Inner2(WithInner withInner, int i) {
			withInner.super(i);
		}
	}
}
public class Ex26 {
	public static void main(String[] args){
		WithInner wi = new WithInner();
		WithInner2 wi2 = new WithInner2();
		Inner2 inner2 = wi2.new Inner2(wi, 7);
		inner2.sayNum();
	}
	
}
