package mi.practice.java.base.innerclasses.ex.ex25;

import mi.practice.java.base.innerclasses.controller.Event;
import mi.practice.java.base.innerclasses.controller.GreenHouseControls;

public class GreenhouseControls25 extends GreenHouseControls{
	@SuppressWarnings("unused")
	private boolean waterMist=false;
	public class WaterMistOn extends Event{
		public WaterMistOn(long delayTime){
			super(delayTime);
		}
		@Override
		public void action(){
			waterMist=true;
		}
		@Override
		public String toString(){
			return "Water mist generators is on";
		}
	}
	public class WaterMistOff extends Event{
		public WaterMistOff(long delayTime){
			super(delayTime);
		}
		@Override
		public void action(){
			waterMist=false;
		}
		@Override
		public String toString(){
			return "Water mist generators is off";
		}
	}
}
