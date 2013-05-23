package mi.practice.java.base.interfaces.interfaceprocessor;
import static mi.practice.java.base.util.Print.*;
public class Apply {
	public static void process(Processor p, Object s){
		print("Using Porcessor "+p.name());
		print(p.process(s));
	}
}
