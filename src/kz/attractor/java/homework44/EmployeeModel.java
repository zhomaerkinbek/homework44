package kz.attractor.java.homework44;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class EmployeeModel {
    private static ArrayList<Employee> employee;
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
    public static Optional<Employee> getUserFromEmail(String email){
        return employee.stream().filter(e -> e.getId().equalsIgnoreCase(email)).findFirst();
    }
}
