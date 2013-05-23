package mi.practice.java.base.reusing;
import static mi.practice.java.base.util.Print.*;
/**
 * Java在继承类中添加重载的函数, 不会隐藏父类的方法
 * 这点跟C++不一样
 */
class Homer{
	char doh(char c){
		print("doh(char)");
		return 'd';
	}
	
	float doh(float f){
		print("doh(float)");
		return 1.0f;
	}
}

class Milhouse{}

class Bart extends Homer{
	void doh(Milhouse m){
		print("doh(Milhouse)");
	}
}

public class Hide {
	public static void main(String[] args){
		Bart b = new Bart();
		b.doh(1);
		b.doh('x');
		b.doh(1.0f);
		b.doh(new Milhouse());
	}
}
