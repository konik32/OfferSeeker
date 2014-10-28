package pl.edu.agh.offerseeker.crawler;

/**
 * Counts how many URLs have been already visited. The counter is thread-safe.
 * 
 * @author g.kostalkowicz
 * 
 */
public class VisitedUrlsCounter {

	private int counterLimit;
	private int counter;

	public VisitedUrlsCounter(int counterLimit) {
		this.counterLimit = counterLimit;
	}

	public void increment() {
		synchronized (this) {
			counter++;
		}
	}

	public boolean isLimitReached() {
		synchronized (this) {
			return counter >= counterLimit;
		}
	}

	public int get() {
		synchronized (this) {
			return counter;
		}
	}

}
