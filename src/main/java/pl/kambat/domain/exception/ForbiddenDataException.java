package pl.kambat.domain.exception;

public class ForbiddenDataException extends RuntimeException{
    public ForbiddenDataException(final String message) {
        super(message);
    }
}
