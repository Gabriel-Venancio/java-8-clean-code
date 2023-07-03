package main.exception;

public class SubscriptionsExpiredException extends RuntimeException {
    public SubscriptionsExpiredException() {
    }

    public SubscriptionsExpiredException(String message) {
        super(message);
    }
}
