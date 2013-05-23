package mi.practice.java.base.polymorphism.shape;

public class Circle extends Shape{
	@Override
	public void draw(){
		System.out.println("Circle.draw()");
	}
	@Override
	public void erase(){
		System.out.println("Circle.erase()");
	}
	@Override
	public void turnaround(){
		System.out.println("Circle.turnaround");
	}
}
