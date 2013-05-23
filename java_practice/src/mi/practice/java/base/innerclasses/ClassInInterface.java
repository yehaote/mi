package mi.practice.java.base.innerclasses;
/**
 * 接口里不能放代码, 
 * 但是可以放内嵌类,
 * 因为接口里的所有成员都是static和public的,
 * 所以它不会违反接口的规则, 
 * 其实接口里的内嵌内只是用了接口的命名空间
 */
public interface ClassInInterface {
	void howdy();
	class Test implements ClassInInterface{
		public void howdy(){
			System.out.println("Howdy!");
		}
		public static void main(String[] args){
			new Test().howdy();
		}
	}
}
