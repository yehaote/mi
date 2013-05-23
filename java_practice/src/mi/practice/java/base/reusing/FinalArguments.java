package mi.practice.java.base.reusing;
/**
 * 可以在定义函数的时候把参数定义成final
 * 这样在函数体之类这个参数将具有final特性
 * 这个特性主要用于给佚名类传数据
 */
class Gizmo{
	public void spin(){}
}

public class FinalArguments {
	void with(final Gizmo g){
//		g = new Gizmo();
	}
	
	void without(Gizmo g){
		 g= new Gizmo();
		 g.spin();
	}
	
	void f(final int i){
//		i++;
	}
	
	int g(final int i){
		return i+1;
	}
	
	public static void main(String[] args){
		FinalArguments bf = new FinalArguments();
		bf.without(null);
		bf.with(null);
	}
}
