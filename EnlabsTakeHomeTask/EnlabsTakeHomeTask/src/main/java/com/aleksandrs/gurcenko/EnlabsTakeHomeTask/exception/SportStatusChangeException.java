package com.aleksandrs.gurcenko.EnlabsTakeHomeTask.exception;

public class SportStatusChangeException extends RuntimeException{
    public SportStatusChangeException(String reason){
        super(reason);
    }
}
