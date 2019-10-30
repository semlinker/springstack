package com.semlinker.lombok;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public class NonNullDemo {
    @Getter
    @Setter
    @NonNull
    private String name;
}
