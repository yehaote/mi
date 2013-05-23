package mi.practice.java.base.typeinfo.ex;

public class Ex4 {
	@SuppressWarnings("unused")
	public static void main(String[] args){
		Rhomboid rhomboid = new Rhomboid();
		Shape shape = rhomboid;
		if(shape instanceof Circle){
			System.out.println("shape is a circle");
			Circle circle = (Circle)shape;
		}
		if(shape instanceof Rhomboid){
			System.out.println("shape is a phomboid");
			Rhomboid rightType = (Rhomboid)shape;
			rightType.draw();
		}
	}
}
