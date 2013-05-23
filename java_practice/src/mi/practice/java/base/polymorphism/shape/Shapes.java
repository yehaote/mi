package mi.practice.java.base.polymorphism.shape;
/**
 * java的多态性是通过动态绑定实现的
 * 也就是迟绑定(late-binding)
 * static, final 方式是early-binding
 * private方法默认是final的
 * ex2, ex3, ex4
 */
public class Shapes {
	private static RandomShapeGenerator gen = new RandomShapeGenerator();
	public static void main(String[] args){
		Shape[] s = new Shape[20];
		for(int i=0;i<s.length;i++){
			s[i]=gen.next();
		}
		for(Shape shape:s){
//			shape.draw();
			shape.turnaround();
		}
	}
}
