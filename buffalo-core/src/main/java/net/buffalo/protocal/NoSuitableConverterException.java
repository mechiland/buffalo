package net.buffalo.protocal;

public class NoSuitableConverterException extends RuntimeException {

	private static final long serialVersionUID = 792613973952907011L;

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
