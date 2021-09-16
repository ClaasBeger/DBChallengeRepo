package shorteningservices.exception;

public class UserNotFoundException extends RuntimeException {
	public UserNotFoundException(int id) {
		super("Could not find User " + id);
	}
}