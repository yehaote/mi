package mi.practice.java.base.holding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class MultiIterableClass extends IterableClass {
	public Iterable<String> reversed() {
		return new Iterable<String>() {
			@Override
			public Iterator<String> iterator() {
				return new Iterator<String>() {
					private int index = words.length - 1;

					@Override
					public boolean hasNext() {
						return index > -1;
					}

					@Override
					public String next() {
						return words[index--];
					}

					@Override	
					public void remove() {
						throw new UnsupportedOperationException();
					}

				};
			}
		};
	}
	
	public Iterable<String> randomized(){
		return new Iterable<String>() {
			@Override
			public Iterator<String> iterator() {
				//注意这里是值拷贝
				List<String> shuffled = new ArrayList<String>(Arrays.asList(words));
				Collections.shuffle(shuffled, new Random());
				return shuffled.iterator();
			}
		};
	}
	
	public static void main(String[] args){
		MultiIterableClass mic = new MultiIterableClass();
		for(String s:mic){
			System.out.print(s+" ");
		}
		System.out.println();
		for(String s:mic.reversed()){
			System.out.print(s+" ");
		}
		System.out.println();
		for(String s:mic.randomized()){
			System.out.print(s+ " ");
		}
	}
}
