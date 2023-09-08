package com.mlorenzo.reactiveexamples.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {
	private Integer id;
    private String firstName;
    private String lastName;

    public String sayMyName(){
        return "My Name is " + firstName + " " + lastName + ".";
    }
}
