package com.aleksandrs.gurcenko.EnlabsTakeHomeTask.exception;

public class NoSportEventsFoundByStatusException extends RuntimeException{
    public NoSportEventsFoundByStatusException(String reason){
        super(reason);
    }
}
