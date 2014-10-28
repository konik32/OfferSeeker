package pl.edu.agh.offerseeker.exceptions;

public class CrawlerInternalException extends Exception {

	private static final long serialVersionUID = 5592749359783111654L;

	public CrawlerInternalException() {
		super();
	}

	public CrawlerInternalException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CrawlerInternalException(String message, Throwable cause) {
		super(message, cause);
	}

	public CrawlerInternalException(String message) {
		super(message);
	}

	public CrawlerInternalException(Throwable cause) {
		super(cause);
	}

}
