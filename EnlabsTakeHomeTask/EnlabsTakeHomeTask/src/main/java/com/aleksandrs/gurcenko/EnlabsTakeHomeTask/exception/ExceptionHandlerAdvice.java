package com.aleksandrs.gurcenko.EnlabsTakeHomeTask.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(SportStatusChangeException.class)
    public ResponseEntity handleSportStatusChangeException(SportStatusChangeException e){
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(e.getMessage());
    }

    @ExceptionHandler(SportEventWithIdNotFoundException.class)
    public ResponseEntity handleSportEventWithIdNotFoundException(SportEventWithIdNotFoundException e){
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(e.getMessage());
    }

    @ExceptionHandler(NoSportEventsFoundByStatusException.class)
    public ResponseEntity handleNoSportEventsFoundByStatusException(NoSportEventsFoundByStatusException e) {
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(e.getMessage());
    }

    @ExceptionHandler(NoSportEventsFoundBySportException.class)
    public ResponseEntity handleNoSportEventsFoundBySportException(NoSportEventsFoundBySportException e) {
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(e.getMessage());
    }

    @ExceptionHandler(NoSportEventsFoundBySportAndStatusException.class)
    public ResponseEntity handleNoSportEventsFoundBySportAndStatusException(NoSportEventsFoundBySportAndStatusException e) {
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(e.getMessage());
    }


}
