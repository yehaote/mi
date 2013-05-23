package mi.practice.java.base.generics;

/**
 * 主观上想想, Cat继承ComparablePet,
 * 然后Cat应该跟Cat比较所以实现Comparable<Cat>
 * 可是这样问题就又出现了, 应该同时实现了Comparable<Cat>和Comparable<ComparablePet>
 * 这在Java中是不允许的
 */
//class Cat extends ComparablePet implements Comparable<Cat>{}
