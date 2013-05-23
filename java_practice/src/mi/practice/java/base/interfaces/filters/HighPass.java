package mi.practice.java.base.interfaces.filters;

public class HighPass extends Filter{
	double cutoff;
	public HighPass(double cutoff){
		this.cutoff=cutoff;
	}
	@Override
	public Waveform process(Waveform input){
		return input;
	}
}
