package mi.practice.java.base.util;
/**
 * 在接口中也可以使用泛型
 * @author waf
 * @param <T>
 */
public interface Generator<T> {
	T next();
}
