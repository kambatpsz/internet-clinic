package pl.kambat.domain.exception;

public class NotDataFoundException extends RuntimeException{
    public NotDataFoundException(final String message) {
        super(message);
    }
}
