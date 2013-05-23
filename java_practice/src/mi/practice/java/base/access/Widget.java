package mi.practice.java.base.access;
/**
 * 类前面也可以加访问修饰符
 * 一般来说只使用public和默认修饰符
 * (只有内嵌类InnerClass才会有private跟protected)
 * 1. 一个编译文件里面最多包含一个public修饰的类,
 *    跟任意个包访问权限的类
 * 2. 这个public类必须跟编译文件同名(大小写也必须相同)
 * 3. 一个编译文件可以不包含public类, 这个时候文件名随意
 */
public class Widget {

}
 
class Widget2{
	 
 }
