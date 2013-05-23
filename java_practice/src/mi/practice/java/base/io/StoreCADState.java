package mi.practice.java.base.io;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * 关于类的静态化成员的序列化,
 * 可以听过序列化类对应的Class对象.
 * Class是Serializable的.
 * 下面的示例每一种Shape共享一个static color
 */
@SuppressWarnings("serial")
abstract class Shape implements Serializable {
	public static final int RED = 1, BLUE = 2, GREEN = 3;
	private int xPos, yPos, dimension;
	private static Random rand = new Random(47);
	private static int counter = 0;

	public abstract void setColor(int newColor);

	public abstract int getColor();

	public Shape(int xVal, int yVal, int dim) {
		xPos = xVal;
		yPos = yVal;
		dimension = dim;
	}

	@Override
	public String toString() {
		return getClass() + "color[" + getColor() + "] xPos[" + xPos
				+ "] yPos[" + yPos + "] dim[" + dimension + "]\n";
	}

	public static Shape randomFactory() {
		int xVal = rand.nextInt(100);
		int yVal = rand.nextInt(100);
		int dim = rand.nextInt(100);
		switch (counter++ % 3) {
		default:
		case 0:
			return new Circle(xVal, yVal, dim);
		case 1:
			return new Square(xVal, yVal, dim);
		case 2:
			return new Line(xVal, yVal, dim);
		}
	}
}

@SuppressWarnings("serial")
class Circle extends Shape {
	private static int color = RED;

	public Circle(int xVal, int yVal, int dim) {
		super(xVal, yVal, dim);
	}

	@Override
	public void setColor(int newColor) {
		color = newColor;
	}

	@Override
	public int getColor() {
		return color;
	}
}

@SuppressWarnings("serial")
class Square extends Shape {
	private static int color;

	public Square(int xVal, int yVal, int dim) {
		super(xVal, yVal, dim);
		color = RED;
	}

	@Override
	public void setColor(int newColor) {
		color = newColor;
	}

	@Override
	public int getColor() {
		return color;
	}
}

@SuppressWarnings("serial")
class Line extends Shape {
	private static int color = RED;
	
	// 序列化静态成员
	public static void serializeStaticState(ObjectOutputStream os)
			throws IOException {
		os.writeInt(color);
	}

	// 反序列化静态成员
	public static void deserializeStaticState(ObjectInputStream ois)
			throws IOException {
		color = ois.readInt();
	}

	public Line(int xVal, int yVal, int dim) {
		super(xVal, yVal, dim);
	}

	@Override
	public void setColor(int newColor) {
		color = newColor;
	}

	@Override
	public int getColor() {
		return color;
	}
}

public class StoreCADState {
	public static void main(String[] args) throws FileNotFoundException,
			IOException {
		List<Class<? extends Shape>> shapeTypes = new ArrayList<Class<? extends Shape>>();
		shapeTypes.add(Circle.class);
		shapeTypes.add(Square.class);
		shapeTypes.add(Line.class);
		List<Shape> shapes = new ArrayList<Shape>();
		for (int i = 0; i < 10; i++) {
			shapes.add(Shape.randomFactory());
		}
		// 把颜色都设置成绿色
		for (int i = 0; i < 10; i++) {
			((Shape) shapes.get(i)).setColor(Shape.GREEN);
		}
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(
				"CADState.out"));
		// 输出ShapeTypes, 都是Class
		out.writeObject(shapeTypes);
		// 输出Line的静态color
		Line.serializeStaticState(out);
		// 输出shapes
		out.writeObject(shapes);
		System.out.println(shapes);
	}
}
