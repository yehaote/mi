package mi.practice.java.base.reusing;
/**
 * 在某些情况下, 我们可以把类中引用的类设置成public
 * 然后让其他编程人员知道如何去使用它
 * 不过在大多数情况下, 成员应该设置成private
 * 
 * 继承表现is-a的关系
 * 组合表现has-a的关系
 * 
 * 包含练习13
 */
class Engine{
	public void start(){}
	public void rev(){}
	public void stop(){}
	public void service(){}
}

class Wheel{
	public void inflate(int psi){}
}

class Window{
	public void rollup(){}
	public void rolldown(){}
}

class Door{
	public Window window = new Window();
	public void open(){}
	public void close(){}
}

public class Car {
	public Engine engine = new Engine();
	public Wheel[] wheels = new Wheel[4];
	public Door left= new Door(), right = new Door();
	
	public Car(){
		for(int i=0;i<4;i++){
			wheels[i]= new Wheel();
		}
	}
	
	public static void main(String[] args){
		Car car = new Car();
		car.left.window.rollup();
		car.wheels[0].inflate(77);
		car.engine.service();
	}
}
