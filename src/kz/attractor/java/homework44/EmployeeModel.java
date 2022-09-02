package kz.attractor.java.homework44;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class EmployeeModel {
    private ArrayList<Employee> employee;
    public EmployeeModel() {
        employee = new ArrayList(List.of(readEmployee()));
    }

    public ArrayList<Employee> getEmployee() {
        return employee;
    }

    public void setEmployee(ArrayList<Employee> employee) {
        this.employee = employee;
    }

    public static Employee[] readEmployee(){
        Path path = Paths.get("./employee.json");
        String json = "";
        try{
            json = Files.readString(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.fromJson(json, Employee[].class);
    }

    public static void writeFile(ArrayList<Employee> employee){
        Path path = Paths.get("./employee.json");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Employee[] result = employee.stream().toArray(Employee[]::new);
        String json = gson.toJson(result);
        try{
            byte[] arr = json.getBytes();
            Files.write(path, arr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
