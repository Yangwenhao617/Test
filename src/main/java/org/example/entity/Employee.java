package org.example.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Employee {
    private int id;
    private String name;
    private int age;
    private String position;
}
