package com.github.serivesmejia.binairenbt.exception;

public class UnmatchingTagIdException extends IllegalTagFormatException {
    public UnmatchingTagIdException() {
        super();
    }

    public UnmatchingTagIdException(String message) {
        super(message);
    }
}
