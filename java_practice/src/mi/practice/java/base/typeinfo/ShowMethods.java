package mi.practice.java.base.typeinfo;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.regex.Pattern;
import static mi.practice.java.base.util.Print.*;
/**
 * 通过getMethods()和getConstructors()可以获得
 * 声明的方法和构造函数
 * 获取到的这些对象还包括方法名, 参数跟放回值的信息
 * 打印method的信息可以看到除了限定符以外还包好方法名的前缀, 
 * 每一个方法都有相应的前缀, 如果是继承的没有覆盖的方法直接指向的
 * 是父类的前缀, 而自己覆盖的方法会直接显示到当前的前缀
 * 
 * 默认生成的构造函数的访问限定等于当前类的访问限定
 * @author waf
 */
public class ShowMethods {
	private static String usage="usage:\n"+
			"ShowMethods qualified.class.name\n" +
			"To show all method in class or:\n" +
			"ShowMethods qualified.class.name word\n" +
			"To search for methods involving 'word'";
	private static Pattern p = Pattern.compile("(\\w+\\.)|(final)|(native)");
	@SuppressWarnings({ "unused", "rawtypes" })
	public static void main(String[] args){
		if(args.length<1){
			print(usage);
			System.exit(0);
		}
		int lines=0;
		try{
			Class<?> c = Class.forName(args[0]);
			Method[] methods = c.getMethods();
			Constructor[] ctors=c.getConstructors();
			if(args.length==1){
				for(Method method:methods){
					print(p.matcher(method.toString()).replaceAll(""));
				}
				for(Constructor ctor:ctors){
					print(p.matcher(ctor.toString()).replaceAll(""));
				}
				lines=methods.length+ctors.length;
			}else{
				for(Method method:methods){
					if(method.toString().indexOf(args[1]) != -1){
						print(p.matcher(method.toString()).replaceAll(""));
						lines++;
					}
				}
				for(Constructor ctor:ctors){
					if(ctor.toString().indexOf(args[1]) != -1){
						print(p.matcher(ctor.toString()).replaceAll(""));
						lines++;
					}
				}
			}
		}catch (ClassNotFoundException e) {
			print("No such class: "+e);
		}
	}
}
