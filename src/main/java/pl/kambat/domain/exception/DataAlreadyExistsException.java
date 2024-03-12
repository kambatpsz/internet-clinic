package pl.kambat.domain.exception;

public class DataAlreadyExistsException extends RuntimeException{
    public DataAlreadyExistsException(final String message) {
        super(message);
    }
}
