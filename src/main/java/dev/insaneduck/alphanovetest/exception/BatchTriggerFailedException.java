package dev.insaneduck.alphanovetest.exception;

public class BatchTriggerFailedException extends RuntimeException{
    public BatchTriggerFailedException(String message)
    {
        super(message);
    }

}
