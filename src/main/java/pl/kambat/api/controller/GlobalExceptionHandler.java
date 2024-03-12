package pl.kambat.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import pl.kambat.domain.exception.DataAlreadyExistsException;
import pl.kambat.domain.exception.ForbiddenDataException;
import pl.kambat.domain.exception.NotDataFoundException;
import pl.kambat.domain.exception.ProcessingDataException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex) {
        return handleError(ex, "Other exception occurred");
    }

    @ExceptionHandler(NotDataFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleNoResourceFound(NotDataFoundException ex) {
        return handleError(ex, "Could not find data");
    }

    @ExceptionHandler(DataAlreadyExistsException.class)
    public ModelAndView handleResourceAlreadyExists(DataAlreadyExistsException ex) {
        return handleError(ex, "Data already exists");
    }

    @ExceptionHandler(ProcessingDataException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleProcessingException(ProcessingDataException ex) {
        return handleError(ex, "Processing exception occurred");
    }

    @ExceptionHandler(ForbiddenDataException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ModelAndView handleForbiddenDataException(ForbiddenDataException ex) {
        return handleError(ex, "Forbidden data");
    }

    private ModelAndView handleError(Exception ex, String message) {
        log.error(message, ex);
        ModelAndView modelView = new ModelAndView("error");
        modelView.addObject("errorMessage", String.format("%s: [%s]", message, ex.getMessage()));
        return modelView;
    }
}
