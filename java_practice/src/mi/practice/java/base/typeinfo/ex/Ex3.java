package mi.practice.java.base.typeinfo.ex;

import java.util.Arrays;
import java.util.List;
/**
 * 向下转换的时候如果类型不对会报
 * java.lang.ClassCastException
 * @author waf
 */
abstract class Shape{
	protected boolean isHighLight=false;
	void draw(){
		System.out.println(this+".draw()");
	}
	@Override
	abstract public String toString();
}
class Circle extends Shape{
	@Override
	public String toString() {
		if(isHighLight){
			return "Hightlighted Circle";
		}
		return "Circle";
	}
}
class Square extends Shape{
	@Override
	public String toString() {
		if(isHighLight){
			return "Hightlighted Square";
		}
		return "Square";
	}
}
class Triangle extends Shape{
	@Override
	public String toString() {
		if(isHighLight){
			return "Hightlighted Triangle";
		}
		return "Triangle";
	}
}
class Rhomboid extends Shape{
	@Override
	public String toString() {
		if(isHighLight){
			return "Hightlighted Rhomboid";
		}
		return "Rhomboid";
	}
}
class Shapes {
	//ex5
	public static void rotate(Shape shape){
		if(shape instanceof Circle){
			System.out.println("It's a circle");
		}else{
			System.out.println("Rorating "+shape.getClass());
		}
	}
	
	public static void main(String[] args){
		List<Shape> shapeList = Arrays.asList(new Circle(), new Square(), new Triangle());
		for(Shape shape : shapeList){
			shape.draw();
		}
	}
}
public class Ex3 {
	@SuppressWarnings("unused")
	public static void main(String[] args){
		Rhomboid rhomboid = new Rhomboid();
		Shape shape=rhomboid;
		Circle circle = (Circle) shape;
	}
}
