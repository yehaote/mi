package mi.practice.java.base.typeinfo.ex;

import java.util.Arrays;
import java.util.List;

public class Ex5 {
	public static void main(String[] args){
		List<Shape> shapeList = Arrays.asList(new Circle(), new Square(), new Triangle());
		for(Shape shape : shapeList){
			Shapes.rotate(shape);
		}
	}
}
