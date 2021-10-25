package com.genericalexacc;

import java.util.List;

interface LaetCallable {
    int arity();
    Object call(Interpreter interpreter, List<Object> args);
}