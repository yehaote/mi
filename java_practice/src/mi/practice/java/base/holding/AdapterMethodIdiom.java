package mi.practice.java.base.holding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
/**
 * 如果当前已经继承了Iterable, 
 * 但是你想提供更多的Iterable可以使用这个方法
 * 适配器方法模式
 * 提供了默认的Iterable, 又提供了一个反向的Iterable
 */
@SuppressWarnings("serial")
class ReversibleArrayList<T> extends ArrayList<T> {
	public ReversibleArrayList(Collection<T> c) {
		super(c);
	}

	public Iterable<T> reversed() {
		return new Iterable<T>() {
			@Override
			public Iterator<T> iterator() {
				return new Iterator<T>() {
					int current = size() - 1;

					@Override
					public boolean hasNext() {
						return current > -1;
					}

					@Override
					public T next() {
						return get(current--);
					}

					@Override
					public void remove() {
						throw new UnsupportedOperationException();
					}
				};
			}

		};
	}

}

public class AdapterMethodIdiom {
	public static void main(String[] args) {
		ReversibleArrayList<String> ral = new ReversibleArrayList<String>(
				Arrays.asList("To be or not to be".split(" ")));
		for (String s : ral) {
			System.out.print(s + " ");
		}
		System.out.println();
		for (String s : ral.reversed()) {
			System.out.print(s + " ");
		}
	}
}
