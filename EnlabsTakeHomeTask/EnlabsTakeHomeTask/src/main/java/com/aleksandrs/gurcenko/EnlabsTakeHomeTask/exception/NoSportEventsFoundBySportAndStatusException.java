package com.aleksandrs.gurcenko.EnlabsTakeHomeTask.exception;

public class NoSportEventsFoundBySportAndStatusException extends RuntimeException{
    public NoSportEventsFoundBySportAndStatusException(String reason){
        super(reason);
    }
}