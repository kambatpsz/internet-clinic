package pl.kambat.api.controller.rest;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;
import pl.kambat.domain.exception.NotDataFoundException;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionRestHandlerTest {
    @Mock
    private WebRequest webRequest;

    @InjectMocks
    private GlobalExceptionRestHandler exceptionHandler;

    @Test
    void handleExceptionInternal_EntityNotFoundException_ReturnsNotFoundResponse() {

        //given
        EntityNotFoundException exception = new EntityNotFoundException("Entity not found");

        // when
        ResponseEntity<Object> responseEntity = exceptionHandler.handleExceptionInternal(
                exception, null, null, HttpStatus.NOT_FOUND, webRequest);

        // then
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }


    @Test
    void handleExceptionInternal_ConstraintViolationException_ReturnsBadRequestResponse() {
        // given
        ConstraintViolationException exception = new ConstraintViolationException(
                "Validation failed", Collections.emptySet());

        // when
        ResponseEntity<Object> responseEntity = exceptionHandler.handleExceptionInternal(
                exception, null, new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);

        // then
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void handleExceptionInternal_DataIntegrityViolationException_ReturnsBadRequestResponse() {
        // given
        DataIntegrityViolationException exception = new DataIntegrityViolationException(
                "Data integrity violation");

        // when
        ResponseEntity<Object> responseEntity = exceptionHandler.handleExceptionInternal(
                exception, null, new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);

        // then
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void handle_NotDataFoundException_ReturnsNotFoundResponse() {
        // given
        NotDataFoundException exception = new NotDataFoundException(
                "Requested data not found");

        // when
        ResponseEntity<Object> responseEntity = exceptionHandler.handle(exception);

        // then
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}