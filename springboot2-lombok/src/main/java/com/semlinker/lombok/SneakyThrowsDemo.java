package com.semlinker.lombok;

import lombok.SneakyThrows;

public class SneakyThrowsDemo {
    @SneakyThrows
    @Override
    protected Object clone() {
        return super.clone();
    }
}
