package ru.practicum.shareit.exception;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.spi.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {
    @ExceptionHandler({InappropriateUser.class, ObjectNotFoundException.class})
    public ResponseEntity<ErrorMessage> notFound(RuntimeException e) {
        log.error("Not found " + e.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage(e.getMessage()));
    }

    @ExceptionHandler(AlreadyUsedEmail.class)
    public ResponseEntity<ErrorMessage> alreadyUsedEmail(AlreadyUsedEmail e) {
        log.error("already used email: " + e.getMessage());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorMessage("Already used email:" + e.getMessage()));
    }

    @ExceptionHandler({BadRequestException.class, ItemIsUnavailable.class, BookingStatusAlreadySet.class,
            MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorMessage> badRequest(RuntimeException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(e.getMessage()));
    }

    @ExceptionHandler(UnsupportedStatus.class)
    public ResponseEntity<ErrorResponse> unsupportedStatus(UnsupportedStatus e, HttpServletRequest request) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        String timestamp = formatter.format(ZonedDateTime.now());
        String path = request.getRequestURI();
        String error = e.getReasonPhrase();
        String message = e.getMessage();
        HttpStatus status = HttpStatus.BAD_REQUEST;

        ErrorResponse errorResponse = new ErrorResponse(timestamp, status.value(), error, message, path);
        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler (Exception.class)
    public ResponseEntity<ErrorMessage> ourException(RuntimeException e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorMessage(e.getMessage()));
    }
}