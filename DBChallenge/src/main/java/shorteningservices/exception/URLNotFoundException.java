package shorteningservices.exception;

public class URLNotFoundException  extends RuntimeException {
	
	public URLNotFoundException(int id) {
		super("Could not find URL " + id);
	}
}
