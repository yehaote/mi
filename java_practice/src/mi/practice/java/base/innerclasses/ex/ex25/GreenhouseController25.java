package mi.practice.java.base.innerclasses.ex.ex25;

import mi.practice.java.base.innerclasses.controller.Event;

public class GreenhouseController25 {
	public static void main(String[] args){
		GreenhouseControls25 gc = new GreenhouseControls25();
		gc.addEvent(gc.new Bell(900));
		Event[] eventList = {
				gc.new ThermostatNight(0),
				gc.new LightOn(200),
				gc.new LightOff(400),
				gc.new WaterOn(600),
				gc.new WaterMistOn(800),
				gc.new WaterMistOff(1000),
				gc.new WaterOff(1200),
				gc.new ThermostatDay(1400)
		};
		gc.addEvent(gc.new Restart(2000, eventList));
		gc.addEvent(new GreenhouseControls25.Terminate(1000000));
		gc.run();
	}
}
