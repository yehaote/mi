package mi.practice.java.base.interfaces;
/**
 * 在多个接口, 或接口和被继承类中包含同一函数并没有问题
 * 但是不能有函数冲突, 同一函数返回不同的类型
 */
interface I1{
	void f();
}
interface I2{
	int f(int i);
}
interface I3{
	int f();
}
class C{
	public int f(){
		return 1;
	}
}
class C2 implements I1, I2{
	public void f(){}
	public int f(int i){
		return 1;
	}
}
class C3 extends C implements I2{
	public int f(int i){
		return 1;
	}
}
class C4 extends C implements I3{
	public int f(){
		return 1;
	}
}
//class C5 extends C implements I1{} //同一函数不同返回类型
//interface I4 extends I1, I3{} //同一函数不同返回类型
public class InterfaceCollision {
}
