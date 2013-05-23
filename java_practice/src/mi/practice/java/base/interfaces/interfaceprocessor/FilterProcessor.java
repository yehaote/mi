package mi.practice.java.base.interfaces.interfaceprocessor;

import mi.practice.java.base.interfaces.filters.BandPass;
import mi.practice.java.base.interfaces.filters.Filter;
import mi.practice.java.base.interfaces.filters.HighPass;
import mi.practice.java.base.interfaces.filters.LowPass;
import mi.practice.java.base.interfaces.filters.Waveform;
/**
 * 适配器模式(adapter desgin pattern)
 * 写个适配器实现接口, 然后可以使用委托之类的办法完成具体实现
 */
class FilterAdapter implements Processor {
	Filter filter;

	public FilterAdapter(Filter filter) {
		this.filter = filter;
	}

	@Override
	public String name() {
		return filter.name();
	}

	@Override
	public Waveform process(Object input) {
		return filter.process((Waveform) input);
	}
}

public class FilterProcessor {
	public static void main(String[] args) {
		Waveform w = new Waveform();
		Apply.process(new FilterAdapter(new LowPass(1.0)), w);
		Apply.process(new FilterAdapter(new HighPass(2.0)), w);
		Apply.process(new FilterAdapter(new BandPass(3.0, 4.0)), w);
	}
}
