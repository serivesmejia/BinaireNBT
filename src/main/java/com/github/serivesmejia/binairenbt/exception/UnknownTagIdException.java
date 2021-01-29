package com.github.serivesmejia.binairenbt.exception;

public class UnknownTagIdException extends IllegalTagFormatException {
    public UnknownTagIdException() {
        super();
    }

    public UnknownTagIdException(String message) {
        super(message);
    }
}
