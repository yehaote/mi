package mi.practice.java.base.reusing;
import static mi.practice.java.base.util.Print.*;

/**
 * 演示回收
 * Java没有析构函数, 不能保证对象在回收的时候释放资源
 * Java的垃圾回收机制更加像忘了它, 而不是直接清楚
 * 所以特别是在调用外部资源的时候, 我们需要让客户端编程人员
 * 主动去调用函数进行资源回收
 * 回收的调用顺序应该跟初始化的时候是相反的
 */
class Shape{
	Shape(int i){
		print("Shape constructor");
	}
	void dispose(){
		print("Shape dispose");
	}
}

class Circle extends Shape{
	Circle(int i){
		super(i);
		print("Drawing Circle");
	}
	
	@Override
	void dispose(){
		print("Erasing Circle");
		super.dispose();
	}
}

class Triangle extends Shape{
	Triangle(int i){
		super(i);
		print("Drawing Triangle");
	}
	
	@Override
	void dispose(){
		print("Erasing Triangle");
		super.dispose();
	}
}

class Line extends Shape{
	private int start, end;
	Line(int start, int end){
		super(start);
		this.start=start;
		this.end=end;
		print("Drawing Line: "+start+", "+end);
	}
	
	@Override
	void dispose(){
		print("Erasing Line: "+start+", "+end);
		super.dispose();
	}
}

public class CADSystem extends Shape{
	private Circle circle;
	private Triangle triangle;
	private Line[] lines = new Line[3];
	public CADSystem(int i){
		super(i+1);
		for(int j=0;j<lines.length;j++){
			lines[j] = new Line(j, j*j);
		}
		circle = new Circle(1);
		triangle = new Triangle(1);
		
		print("Combined constructor");
	}
	
	@Override
	public void dispose(){
		print("CADSystem.dispose()");
		//The order of cleanup is the reverse
		//of the order of initialization
		triangle.dispose();
		circle.dispose();
		for(int i=lines.length-1;i>=0;i--){
			lines[i].dispose();
		}
		super.dispose();
	}
	
	public static void main(String[] args){
		CADSystem cad = new CADSystem(7);
		try{
			//需要做的事情
		}finally{
			cad.dispose();
		}
	}
}
