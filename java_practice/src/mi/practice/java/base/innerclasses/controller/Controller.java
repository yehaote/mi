package mi.practice.java.base.innerclasses.controller;

import java.util.ArrayList;
import java.util.List;
/**
 * 这里仅仅使用Event抽象类定义, 
 * 就是把变的事情跟不变的分隔开来的一种体现.
 * 
 * @author waf
 *
 */
public class Controller {
	private List<Event> eventList = new ArrayList<Event>();

	public void addEvent(Event event) {
		eventList.add(event);
	}

	public void run() {
		while (eventList.size() > 0) {
			for (Event event : new ArrayList<Event>(eventList)) {// 做一个拷贝,
																	// 这个迭代的时候不影响原来List
				if (event.ready()) {
					System.out.println(event);
					event.action();
					eventList.remove(event);
				}
			}
		}
	}
}
