package com.semlinker.lombok;

import lombok.ToString;

import java.time.LocalDate;

@ToString(exclude = {"dateOfBirth"})
public class ToStringDemo {
    String firstName;
    String lastName;
    LocalDate dateOfBirth;
}
