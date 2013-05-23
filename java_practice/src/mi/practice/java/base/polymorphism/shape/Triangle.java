package mi.practice.java.base.polymorphism.shape;

public class Triangle extends Shape{
	@Override
	public void draw(){
		System.out.println("Triangle.draw()");
	}
	@Override
	public void erase(){
		System.out.println("Triangle.erase()");
	}
	@Override
	public void turnaround(){
		System.out.println("Triangle.turnaround");
	}
}
