package mi.practice.java.base.generics;

//import java.util.List;
/**
 * 看上去这样应该很清晰,
 * 一个使用W作为参数, 一个使用T类型的参数,
 * 问题是java的泛型是通过擦写实现的,
 * 到最后都会被擦写成List.......
 * 所以这是行不通的
 * (但是可以使用不同的方法名来实现这种效果, 见UseList2)
 * @author waf
 * @param <W>
 * @param <T>
 */
public class UseList<W, T> {
//	void f(List<W> v){};
//	void f(List<T> v){};
}
