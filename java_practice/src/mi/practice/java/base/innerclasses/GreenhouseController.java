package mi.practice.java.base.innerclasses;

import mi.practice.java.base.innerclasses.controller.Event;
import mi.practice.java.base.innerclasses.controller.GreenHouseControls;
/**
 * Ex24
 */
public class GreenhouseController {
	public static void main(String[] args){
		GreenHouseControls gc = new GreenHouseControls();
		gc.addEvent(gc.new Bell(9000));
		Event[] eventList={
			gc.new ThermostatNight(0),
			gc.new LightOn(200),
			gc.new LightOff(400),
			gc.new WaterOn(600),
			gc.new WaterOff(800),
			gc.new FansOn(1000),
			gc.new FansOff(1200),
			gc.new ThermostatDay(1400)
		};
		gc.addEvent(gc.new Restart(2000, eventList));
		if(args.length==1){
			gc.addEvent(new GreenHouseControls.Terminate(new Long(args[0])));
		}
		gc.run();
	}
}
