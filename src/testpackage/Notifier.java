package testpackage;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.Iterator;

public class Notifier {
	private ArrayList listeners = new ArrayList();
	private String listenerMethod;

	public Notifier(String name) {
		this.listenerMethod = name;
	}

	public void addListener(Object not) {
		this.listeners.add(not);
	}

	public void removeListener(Object not) {
		this.listeners.remove(not);
	}

	public void notify(EventObject event) {
		Iterator itr = listeners.iterator();
		while (itr.hasNext()) {
			try {
				Object listener = itr.next();
				Class clss = listener.getClass();
				Method method = clss.getMethod(this.listenerMethod,
						new Class[] { event.getClass() });
				method.invoke(listener, new Object[] { event });
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}