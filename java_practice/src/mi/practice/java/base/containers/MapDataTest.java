package mi.practice.java.base.containers;

import java.util.Iterator;

import mi.practice.java.base.util.CountingGenerator;
import mi.practice.java.base.util.Generator;
import mi.practice.java.base.util.MapData;
import mi.practice.java.base.util.Pair;
import mi.practice.java.base.util.RandomGenerator;

class Letters implements Generator<Pair<Integer, String>>, Iterable<Integer> {
	private int size = 9;
	private int number = 1;
	private char letter = 'A';

	@Override
	public Iterator<Integer> iterator() {
		// TODO Auto-generated method stub
		return new Iterator<Integer>() {
			@Override
			public boolean hasNext() {
				return number < size;
			}

			@Override
			public Integer next() {
				return number++;
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

	@Override
	public Pair<Integer, String> next() {
		return new Pair<Integer, String>(number++, "" + letter++);
	}

}

public class MapDataTest {
	public static void main(String[] args) {
		// Pair generator:
		System.out.println(MapData.map(new Letters(), 11));
		// Two separate generators:
		System.out.println(MapData.map(new CountingGenerator.Character(),
				new RandomGenerator.String(3), 8));
		// A key Generator and a single value
		System.out.println(MapData.map(new CountingGenerator.Character(),
				"value", 6));
		// An Iterable and a value generator
		System.out.println(MapData.map(new Letters(),
				new RandomGenerator.String(3)));
		// An Iterable and a single value
		System.out.println(MapData.map(new Letters(), "Pop"));
	}
}
