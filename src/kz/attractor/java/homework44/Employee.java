package kz.attractor.java.homework44;

import java.util.List;

public class Employee {
    private String id;
    private List<Book> currentBooks;
    private List<Book> issuedBooks;
    private String employeeName;
    private String password;

    public Employee(String id, List<Book> currentBooks, List<Book> issuedBooks, String employeeName, String password) {
        this.id = id;
        this.currentBooks = currentBooks;
        this.issuedBooks = issuedBooks;
        this.employeeName = employeeName;
        this.password = password;
    }

    public Employee(String id, String employeeName, String password) {
        this(id, null, null, employeeName, password);
    }
    public Employee() {
        this(null, null, null, null, null);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Book> getCurrentBooks() {
        return currentBooks;
    }

    public void setCurrentBooks(List<Book> currentBooks) {
        this.currentBooks = currentBooks;
    }

    public List<Book> getIssuedBooks() {
        return issuedBooks;
    }

    public void setIssuedBooks(List<Book> issuedBooks) {
        this.issuedBooks = issuedBooks;
    }
}
