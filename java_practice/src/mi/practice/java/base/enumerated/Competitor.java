package mi.practice.java.base.enumerated;

public interface Competitor<T extends Competitor<T>> {
	Outcome compete(T competitor);
}
