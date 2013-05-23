package mi.practice.java.base.generics;

public class Hamster extends ComparablePet
	implements Comparable<ComparablePet>{
	@Override
	public int compareTo(ComparablePet arg){
		return 0;
	}
}
