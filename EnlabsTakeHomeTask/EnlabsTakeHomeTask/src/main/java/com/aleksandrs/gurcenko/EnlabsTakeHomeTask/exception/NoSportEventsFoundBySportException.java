package com.aleksandrs.gurcenko.EnlabsTakeHomeTask.exception;

public class NoSportEventsFoundBySportException extends RuntimeException{
    public NoSportEventsFoundBySportException(String reason){
        super(reason);
    }
}
