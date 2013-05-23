package mi.practice.java.base.typeinfo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Collections;
import java.util.List;
/**
 * 也可以使用动态代理的方法来实现实例化空对象,
 * 这样可以为每一种对象实现都提供一个控制,
 * 示例中就是一个空的SnowRemoveRobot对象,
 * 这样的动态代理的方法, 要提供一个方法来产生对象
 * @author waf
 */
class NullRobotProxyHandler implements InvocationHandler{
	String nullName;
	private Robot proxied = new NRobot();
	NullRobotProxyHandler(Class<? extends Robot> type) {
		nullName=type.getSimpleName()+" NullRobot";
	}
	private class NRobot implements Null, Robot{
		@Override
		public String name() {
			return nullName;
		}
		@Override
		public String model() {
			return nullName;
		}
		@Override
		public List<Operation> operations() {
			return Collections.emptyList();
		}
	}
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		return method.invoke(proxied, args);
	}
	
}
public class NullRobot {
	public static Robot newNullRoot(Class<? extends Robot> type){
		return (Robot) Proxy.newProxyInstance(NullRobot.class.getClassLoader(), 
				new Class[]{Null.class, Robot.class}, new NullRobotProxyHandler(type));
	}
	public static void main(String[] args){
		Robot[] bots = {new SnowRemoveRobot("SnowBee"),newNullRoot(SnowRemoveRobot.class)};
		for(Robot robot : bots){
			Robot.Test.test(robot);
		}
	}
}
