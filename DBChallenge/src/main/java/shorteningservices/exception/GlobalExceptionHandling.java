package shorteningservices.exception;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandling {

	@ResponseBody
	@ExceptionHandler(UserNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String customerNotFoundHandler(UserNotFoundException ex) {
		return ex.getMessage();
	}

	@ResponseBody
	@ExceptionHandler(StatisticsNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String StatisticsNotFoundHandler(StatisticsNotFoundException ex) {
		return ex.getMessage();
	}

	@ResponseBody
	@ExceptionHandler(URLNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String URLNotFoundHandler(URLNotFoundException ex) {
		return ex.getMessage();
	}


}