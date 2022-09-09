package kz.attractor.java.lesson44;

import com.sun.net.httpserver.HttpExchange;
import kz.attractor.java.homework44.*;
import kz.attractor.java.server.Cookie;
import kz.attractor.java.server.Utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Lesson45Server extends Lesson44Server {
    private static Employee currentEmployee = new Employee("sample@sample.com","Некий сотрудник", "samplepassword");
    private boolean loginCheck = false;
    public Lesson45Server(String host, int port) throws IOException {
        super(host, port);
        registerGet("/", this::homeGetHandler);
        registerGet("/logout", this::logoutHandler);
        registerGet("/login", this::loginGet);
        registerPost("/login", this::loginPost);
        registerGet("/register", this::registrationGet);
        registerPost("/register", this::registrationPost);
        registerGet("/profile", this::profileGet);
        registerGet("/books", this::BooksHandler);
        registerGet("/getbook", this::BookGetHandler);
        registerGet("/returnbook", this::BookReturnHandler);
        registerGet("/employees", this::employeeHandler);

    }

    private void BookReturnHandler(HttpExchange exchange) {
        Map<String,Object> data = new HashMap<>();
        String queryParams = getQueryParams(exchange);
        var employees = new EmployeeModel().getEmployees();
        var books = new BookModel().getBooks();
        Map<String, String> params = Utils.parseUrlEncoded(queryParams, "&");
        if(params.get("bookid") != null){
            for(var employee: employees){
                if(employee.getId().equalsIgnoreCase(params.get("userid"))){
                    employee.getCurrentBooks().remove(params.get("bookid"));
                    employee.getIssuedBooks().add(params.get("bookid"));
                }
            }
            for(var book: books){
                if(book.getId().equalsIgnoreCase(params.get("bookid"))){
                    book.setEmployeeId("");
                    book.setIssued(false);
                }
            }
            EmployeeModel.writeFile(employees);
            BookModel.writeBooks(books);
        }else {
            respond404(exchange);
        }
        redirect303(exchange, "/profile");
    }

    private void BookGetHandler(HttpExchange exchange) {
        Map<String,Object> data = new HashMap<>();
        String queryParams = getQueryParams(exchange);
        var employees = new EmployeeModel().getEmployees();
        var books = new BookModel().getBooks();
        Map<String, String> params = Utils.parseUrlEncoded(queryParams, "&");
        if(params.get("bookid") != null){
            for(var employee: employees){
                if(employee.getId().equalsIgnoreCase(params.get("userid"))){
                    if(employee.getCurrentBooks().size() < 2){
                        employee.getCurrentBooks().add(params.get("bookid"));
                    }
                }
            }
            for(var book: books){
                if(book.getId().equalsIgnoreCase(params.get("bookid"))){
                    book.setEmployeeId(params.get("userid"));
                    book.setIssued(true);
                }
            }
            EmployeeModel.writeFile(employees);
            BookModel.writeBooks(books);
        }else {
            respond404(exchange);
        }
        redirect303(exchange, "/books");
    }

    private void logoutHandler(HttpExchange exchange) {

        Cookie sessionCookie = Cookie.make("userId", "");
        sessionCookie.setMaxAge(-1);
        setCookie(exchange, sessionCookie);
        currentEmployee.setSessionId("");
        loginCheck = false;
        redirect303(exchange, "/");
    }

    private void homeGetHandler(HttpExchange exchange) {
        Map<String,Object> data = new HashMap<>();
        if(!checkCookie(exchange)) {
            renderTemplate(exchange, "index.html", data);
        }else {
            data.put("login", loginCheck);
            data.put("employee", currentEmployee);
            renderTemplate(exchange, "index.html", data);
        }
    }

    private void profileGet(HttpExchange exchange) {
        Map<String,Object> data = new HashMap<>();
        if(!checkCookie(exchange)) {
            currentEmployee = new Employee("sample@sample.com", "Некий сотрудник", "samplepassword");
            data.put("employee", currentEmployee);
            data.put("sample", true);
            loginCheck = false;
            renderTemplate(exchange, "profile.html", data);
        } else {

            data.put("books", new BookModel().getBooks());
            data.put("employee", currentEmployee);
            data.put("login", loginCheck);
            renderTemplate(exchange, "profile.html", data);
        }

    }

    private void BooksHandler(HttpExchange exchange) {
        Map<String,Object> data = new HashMap<>();
        String queryParams = getQueryParams(exchange);
        Map<String, String> params = Utils.parseUrlEncoded(queryParams, "&");
        if(params.get("id") != null){
            var books = new BookModel().getBooks();
            for (Book book : books){
                if(book.getId().equalsIgnoreCase(params.get("id"))){
                    data.put("book", book);
                    break;
                }
            }
            if(data.get("book") != null){
                renderTemplate(exchange, "book.html", data);
            } else {
                respond404(exchange);
            }

        }else if(checkCookie(exchange)) {
            data.put("books", getBooksModel().getBooks());
            data.put("login", loginCheck);
            data.put("employee", currentEmployee);
            renderTemplate(exchange, "books.html", data);
        } else {
            renderTemplate(exchange, "books.html", getBooksModel());
        }
    }

    private void bookHandler(HttpExchange exchange) {
        renderTemplate(exchange, "book.html", new BookModel());
    }

    private void employeeHandler(HttpExchange exchange) {
        Map<String,Object> data = new HashMap<>();
        if(checkCookie(exchange)) {
            data.put("employees", new EmployeeModel().getEmployees());
            data.put("books", getBooksModel().getBooks());
            data.put("login", loginCheck);
            data.put("currentEmployee", currentEmployee);
            renderTemplate(exchange, "employee.html", data);
        } else {
            data.put("employees", new EmployeeModel().getEmployees());
            data.put("books", getBooksModel().getBooks());
            renderTemplate(exchange, "employee.html", data);
        }
    }

    private BookModel getBooksModel(){
        return new BookModel();
    }

    private void registrationGet(HttpExchange exchange) {
        renderTemplate(exchange, "registration.html", new RegistrationModel(false, false));
    }

    private void registrationPost(HttpExchange exchange) {
        var model = new EmployeeModel();
        boolean start = true;
        boolean status = false;
        boolean check = false;
        var employees = model.getEmployees();
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
        var employees = model.getEmployees();
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
            loginCheck = true;
            redirect303(exchange, "/profile");
        } else {
            loginCheck = false;
            status = true;
            renderTemplate(exchange, "login.html", new LoginModel(status));
        }

    }

    private void loginGet(HttpExchange exchange) {
        renderTemplate(exchange, "login.html", new LoginModel(false));
    }

    private boolean checkCookie(HttpExchange exchange){
        var parsed = Cookie.parse(getCookies(exchange));
        return parsed.get("userId") != null && parsed.get("userId").equalsIgnoreCase(currentEmployee.getSessionId());
    }

}