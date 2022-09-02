package kz.attractor.java;

import kz.attractor.java.homework44.Employee;
import kz.attractor.java.homework44.EmployeeModel;
import kz.attractor.java.lesson44.Lesson45Server;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            new Lesson45Server("localhost", 9889).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        var model = new EmployeeModel();
//        var employees = model.getEmployee();
//        System.out.println(employees.size());
//        employees.add(new Employee("zhoma@gmail.com", "zhoomart", "qwer"));
//        System.out.println(employees.size());
//        EmployeeModel.writeFile(employees);
    }
}