package com.example.gamedemo.exception;

public class DatabasesException extends Exception {

    static final long serialVersionUID = -3387516993124229948L;

    public DatabasesException() {
    }

    public DatabasesException(String s) {
        super(s);
    }

    public DatabasesException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public DatabasesException(Throwable throwable) {
        super(throwable);
    }

    public DatabasesException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
