package mi.practice.java.base.holding.ex;

import java.util.Iterator;
import java.util.Random;

import mi.practice.java.base.polymorphism.shape.Circle;
import mi.practice.java.base.polymorphism.shape.Hexagon;
import mi.practice.java.base.polymorphism.shape.Shape;
import mi.practice.java.base.polymorphism.shape.Square;
import mi.practice.java.base.polymorphism.shape.Triangle;

class RandomShapeGenerator implements Iterable<Shape> {
	private Random rand = new Random(47);
	private int length;

	public RandomShapeGenerator() {
		this(10);
	}

	public RandomShapeGenerator(int num) {
		length = num;
	}

	@Override
	public Iterator<Shape> iterator() {
		return new Iterator<Shape>() {
			private int index = 0;

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}

			@Override
			public Shape next() {
				if(index<length){
					index++;
					switch (rand.nextInt(4)) {
					default:
					case 0:
						return new Circle();
					case 1:
						return new Square();
					case 2:
						return new Triangle();
					case 3:
						return new Hexagon();
					}
				}
				return null;
			}

			@Override
			public boolean hasNext() {
				return index < length;
			}
		};
	}
}

public class Ex31 {
	public static void main(String[] args){
		RandomShapeGenerator rsg= new RandomShapeGenerator();
		for(Shape shape : rsg){
			shape.draw();
			shape.turnaround();
			shape.erase();
			System.out.println(shape);
		}
	}
}
