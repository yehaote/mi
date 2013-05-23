package mi.practice.java.base.generics;
/**
 * 使用泛型以后想对于SimpleHolder来说, 
 * 取出对象的时候强转消失了
 * 查看生成的字节码文件可以发现其实GenericHolder和SimpleHolder
 * 所编译产生的代码是什么什么区别的, 
 * Generic应该算是一种编译器蜜糖
 * @author waf
 * @param <T>
 */
public class GenericHolder<T> {
	private T obj;
	public void set(T obj){
		this.obj=obj;
	}
	public T get(){
		return obj;
	}
	@SuppressWarnings("unused")
	public static void main(String[] args){
		GenericHolder<String> holder = new GenericHolder<String>();
		holder.set("Item");
		String s=holder.get();
	}
}
