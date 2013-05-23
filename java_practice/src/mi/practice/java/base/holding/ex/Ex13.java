package mi.practice.java.base.holding.ex;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import mi.practice.java.base.innerclasses.controller.Event;

class Controller {
	private List<Event> eventList = new LinkedList<Event>();

	public void addEvent(Event event) {
		eventList.add(event);
	}

	public void run() {
		while (eventList.size()!=0) {
			Iterator<Event> it = eventList.iterator();
			while(it.hasNext()){
				Event event = it.next();
				if (event.ready()) {
					System.out.println(event);
					event.action();
					it.remove();
				}
			}
		}
	}
}
