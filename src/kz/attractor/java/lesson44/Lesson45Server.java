package kz.attractor.java.lesson44;

import com.sun.net.httpserver.HttpExchange;
import kz.attractor.java.homework44.*;
import kz.attractor.java.server.ContentType;
import kz.attractor.java.server.Cookie;
import kz.attractor.java.server.ResponseCodes;
import kz.attractor.java.server.Utils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class Lesson45Server extends Lesson44Server {
    private static Employee currentEmployee = new Employee("sample@sample.com","Некий сотрудник", "samplepassword");
    public Lesson45Server(String host, int port) throws IOException {
        super(host, port);

        registerGet("/login", this::loginGet);
        registerPost("/login", this::loginPost);
        registerGet("/register", this::registrationGet);
        registerPost("/register", this::registrationPost);
        registerGet("/profile", this::profileGet);

    }

    private void profileGet(HttpExchange exchange) {
        var parsed = Cookie.parse(getCookies(exchange));
        if(parsed.get("userId") == null || !parsed.get("userId").equalsIgnoreCase(currentEmployee.getSessionId())) {
            currentEmployee = new Employee("sample@sample.com", "Некий сотрудник", "samplepassword");
        }
        renderTemplate(exchange, "profile.html", currentEmployee);

    }

    private void registrationGet(HttpExchange exchange) {
        renderTemplate(exchange, "registration.html", new RegistrationModel(false, false));
    }

    private void registrationPost(HttpExchange exchange) {
        var model = new EmployeeModel();
        boolean start = true;
        boolean status = false;
        boolean check = false;
        var employees = model.getEmployee();
        String raw = getBody(exchange);

        Map<String, String> parsed = Utils.parseUrlEncoded(raw, "&");
        for (Employee employee : employees) {
            if(employee.getId().equalsIgnoreCase(parsed.get("email"))){
                check = true;
            }
        }
        if(!check){
            status = true;
            var newEmployee = new Employee(parsed.get("email"), parsed.get("name"), parsed.get("password"));
            employees.add(newEmployee);
            EmployeeModel.writeFile(employees);
        }
        renderTemplate(exchange, "registration.html", new RegistrationModel(start, status));
    }

    private void loginPost(HttpExchange exchange) {
        var model = new EmployeeModel();
        boolean status = false;
        boolean check = false;
        Employee currentEmp = new Employee();
        var employees = model.getEmployee();
        String raw = getBody(exchange);
        Map<String, String> parsed = Utils.parseUrlEncoded(raw, "&");
        for (Employee employee : employees) {
            if(employee.getId().equalsIgnoreCase(parsed.get("email"))){
                if(employee.getPassword().equalsIgnoreCase(parsed.get("password"))){
                    check = true;
                    employee.makeSessionId();
                    currentEmp = employee;
                }
            }
        }
        EmployeeModel.writeFile(employees);
        if(check){
            Cookie sessionCookie = Cookie.make("userId", currentEmp.getSessionId());
            sessionCookie.setHttpOnly(true);
            sessionCookie.setMaxAge(600);
            setCookie(exchange, sessionCookie);
            currentEmployee = currentEmp;
            redirect303(exchange, "/profile");
        } else {
            status = true;
            renderTemplate(exchange, "login.html", new LoginModel(status));
        }

    }

    private void loginGet(HttpExchange exchange) {
        renderTemplate(exchange, "login.html", new LoginModel(false));
    }

}