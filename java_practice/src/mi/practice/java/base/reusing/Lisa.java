package mi.practice.java.base.reusing;
/**
 * 因为这里是重载而不是覆盖
 * 所以加上注解的话会编译报错
 */
public class Lisa extends Homer{
//	@Override
	void doh(Milhouse m){
		System.out.println("doh(Milhouse)");
	}
}
