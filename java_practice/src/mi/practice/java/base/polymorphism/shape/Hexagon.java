package mi.practice.java.base.polymorphism.shape;
/**
 * Ex6
 */
public class Hexagon extends Shape{
	@Override
	public void draw(){
		System.out.println("Hexagon.draw()");
	}
	@Override
	public void erase(){
		System.out.println("Hexagon.erase()");
	}
	@Override
	public void turnaround(){
		System.out.println("Hexagon.turnaround");
	}
}
