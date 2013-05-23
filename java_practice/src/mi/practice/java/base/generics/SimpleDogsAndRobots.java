package mi.practice.java.base.generics;
/**
 * 其实也可以不使用泛型而直接使用接口作为引用类型
 * @author waf
 */
class CommunicateSimply{
	static void perform(Performs performer){
		performer.speak();
		performer.sit();
	}
}
public class SimpleDogsAndRobots {
	public static void main(String[] args){
		CommunicateSimply.perform(new PerformingDog());
		CommunicateSimply.perform(new Robot());
	}
}
