package mi.practice.java.base.typeinfo.ex;

import java.util.Arrays;
import java.util.List;

public class Ex6 {
	public static void HightCircle(Shape shape){
		if(shape instanceof Circle){
			shape.isHighLight=true;
		}
	}
	
	public static void main(String[] args){
		List<Shape> shapeList = Arrays.asList(new Circle(), new Square(), new Triangle());
		for(Shape shape : shapeList){
			HightCircle(shape);
			System.out.println(shape);
		}
	}
}
