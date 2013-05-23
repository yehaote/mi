package mi.practice.java.base.generics;
/**
 * Curiously Recurring Generic
 * 继承一个泛型类, 并使用自己作为泛型参数.
 * Java的泛型主要体现在参数, 返回值和成员类型的泛型化,
 * 哪怕最终他们都会擦写成Object类型
 * @author waf
 * @param <T>
 */
class GenericType<T>{}
public class CuriouslyRecurringGeneric 
	extends GenericType<CuriouslyRecurringGeneric>{

}
