package vn.vivas.core.exception;

public class ServiceException extends RuntimeException {
	String message;
	Exception cause;
	public ServiceException(String message, Exception cause) {
		super();
		this.message = message;
		this.cause = cause;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Exception getCause() {
		return cause;
	}
	public void setCause(Exception cause) {
		this.cause = cause;
	}
}
