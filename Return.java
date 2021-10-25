package com.genericalexacc;

public class Return extends RuntimeException{
    final Object value;

    Return(Object value) {
        super(null, null, false, false);
        this.value = value;
    }
}
