package shorteningservices.exception;

public class StatisticsNotFoundException extends RuntimeException{
	
	public StatisticsNotFoundException(int id) {
		super("Could not find Stats " + id);
	}
}
