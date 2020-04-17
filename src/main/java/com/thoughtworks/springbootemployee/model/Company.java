package com.thoughtworks.springbootemployee.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Company {
    private int id;
    private String name;
    private int employeesNumber;
    private List<Employee> employees;
}
