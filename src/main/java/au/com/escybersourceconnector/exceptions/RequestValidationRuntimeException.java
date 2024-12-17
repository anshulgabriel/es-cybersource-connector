package au.com.escybersourceconnector.exceptions;

@SuppressWarnings("unused")
public class RequestValidationRuntimeException
    extends RuntimeException {

  public RequestValidationRuntimeException(final String message) {
    super(message);
  }

  public RequestValidationRuntimeException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public RequestValidationRuntimeException(final Throwable cause) {
    super(cause);
  }
}
