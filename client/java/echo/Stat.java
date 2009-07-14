/**
 * 
 */
package echo;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicLong;

public class Stat {
	private AtomicLong _count = new AtomicLong(0);
	private static Stat _instance = new Stat();
	private long _last = 0;
	private Map<String, Long> _stats = new HashMap<String, Long>();

	public static Stat getInstance() {
		return _instance;
	}

	private Stat() {
	}

	public void inc() {
		_count.incrementAndGet();
	}

	public void clear() {
		_last = _count.get();
	}

	public void collect(String name) {
		long count = _count.get() - _last;
		_stats.put(name, count);
	}

	public void print() {
		for (Entry<String, Long> entry : _stats.entrySet()) {
			System.out.println(entry.getKey() + " : " + entry.getValue());
		}
	}
}