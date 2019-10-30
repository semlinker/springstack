package com.semlinker.lombok;

import lombok.Getter;

public class LazyGetterDemo {
    public static void main(String[] args) {
        LazyGetterDemo m = new LazyGetterDemo();
        System.out.println("Main instance is created");
        m.getLazy();
    }

    @Getter
    private final String notLazy = createValue("not lazy");

    @Getter(lazy = true)
    private final String lazy = createValue("lazy");

    private String createValue(String name) {
        System.out.println("createValue(" + name + ")");
        return null;
    }
}
