package net.buffalo.protocal;

public class NoSuitableConverterException extends RuntimeException {

	public NoSuitableConverterException() {
		super();
	}

	public NoSuitableConverterException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoSuitableConverterException(String message) {
		super(message);
	}

	public NoSuitableConverterException(Throwable cause) {
		super(cause);
	}
	
}
