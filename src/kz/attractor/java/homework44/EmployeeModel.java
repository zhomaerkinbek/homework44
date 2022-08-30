package kz.attractor.java.homework44;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class EmployeeModel {
    private Employee employee;
    private Book book;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public EmployeeModel() {
        employee = readEmployee();
        book = new BookModel().getBook();
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public static Employee readEmployee(){
        String json = getJson("./employee.json");
        Gson gson = new Gson();
        return gson.fromJson(json, Employee.class);
    }

    static String getJson(String fileName){
        String json = "";
        Path path = Paths.get(fileName);
        try {
            json = Files.readString(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }
}
