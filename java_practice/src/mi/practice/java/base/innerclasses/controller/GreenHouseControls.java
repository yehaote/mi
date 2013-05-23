package mi.practice.java.base.innerclasses.controller;

public class GreenHouseControls extends Controller {
	@SuppressWarnings("unused")
	private boolean light = false;

	public class LightOn extends Event {
		public LightOn(long delayTime) {
			super(delayTime);
		}

		@Override
		public void action() {
			light = true;
		}

		@Override
		public String toString() {
			return "Light is on";
		}
	}

	public class LightOff extends Event {
		public LightOff(long delayTime) {
			super(delayTime);
		}

		@Override
		public void action() {
			light = false;
		}

		@Override
		public String toString() {
			return "Light is off";
		}
	}

	@SuppressWarnings("unused")
	private boolean water = false;

	public class WaterOn extends Event {
		public WaterOn(long delayTime) {
			super(delayTime);
		}

		@Override
		public void action() {
			water = true;
		}

		@Override
		public String toString() {
			return "GreenHouse water is on";
		}
	}

	public class WaterOff extends Event {
		public WaterOff(long delayTime) {
			super(delayTime);
		}

		@Override
		public void action() {
			water = false;
		}

		@Override
		public String toString() {
			return "GreenHouse water is off";
		}
	}

	@SuppressWarnings("unused")
	private String thermostat = "Day";

	public class ThermostatNight extends Event {
		public ThermostatNight(long delayTime) {
			super(delayTime);
		}

		@Override
		public void action() {
			thermostat = "Night";
		}

		@Override
		public String toString() {
			return "Thermostat on night setting";
		}
	}

	public class ThermostatDay extends Event {
		public ThermostatDay(long delayTime) {
			super(delayTime);
		}

		@Override
		public void action() {
			thermostat = "Day";
		}

		@Override
		public String toString() {
			return "Thermostat on day setting";
		}
	}

	public class Bell extends Event {
		public Bell(long delayTime) {
			super(delayTime);
		}

		@Override
		public void action() {// 加入父类队列,一直响,在队列之中,响过一次继续加入队列
			addEvent(new Bell(delayTime));
		}

		@Override
		public String toString() {
			return "Bing";
		}
	}
	//一直循环的事件
	//先把要循环的事件放入eventList传进来
	//1. 初始化把需要循环的事件都放入父类队列去执行
	//2. action(), 开始执行自己队列中的事件, 并把它们加入到父类队列中
	//3. 开始自己, 把自己加入到父类队列中去
	public class Restart extends Event {
		private Event[] eventList;

		public Restart(long delayTime, Event[] eventList) {
			super(delayTime);
			this.eventList = eventList;
			for (Event event : eventList) {// 把当前队列加入到父类队列去
				addEvent(event);
			}
		}

		@Override
		public void action() {
			for (Event event : eventList) {
				event.start();// 开始执行每一个事件
				addEvent(event);// 把事件加入父类队列
			}
			start();// 开始自己
			addEvent(this);// 把自己加入到父类队列
		}

		@Override
		public String toString() {
			return "Restarting system";
		}
	}
	
	public static class Terminate extends Event {
		public Terminate(long delayTime) {
			super(delayTime);
		}

		@Override
		public void action() {
			System.exit(0);
		}

		@Override
		public String toString() {
			return "Terminating";
		}
	}
	
	@SuppressWarnings("unused")
	private boolean fans=false;
	public class FansOn extends Event{
		public FansOn(long delayTime){
			super(delayTime);
		}
		@Override
		public void action(){
			fans=true;
		}
		@Override
		public String toString(){
			return "Fans is on";
		}
	}
	public class FansOff extends Event{
		public FansOff(long delayTime){
			super(delayTime);
		}
		@Override
		public void action(){
			fans=false;
		}
		@Override
		public String toString(){
			return "Fans is off";
		}
	}
}