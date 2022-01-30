package com.aleksandrs.gurcenko.EnlabsTakeHomeTask.exception;

public class SportEventWithIdNotFoundException extends RuntimeException{
    public SportEventWithIdNotFoundException(String reason){
        super(reason);
    }
}
