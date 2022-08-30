package kz.attractor.java.homework44;

import java.time.LocalDateTime;

public class Library {

    private Book book;
    private boolean issued;
    private LocalDateTime dateOfIssued;
    private LocalDateTime dateOfReturn;
    private Employee employee;

    public LocalDateTime getDateOfReturn() {
        return dateOfReturn;
    }

    public void setDateOfReturn(LocalDateTime dateOfReturn) {
        this.dateOfReturn = dateOfReturn;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public boolean isIssued() {
        return issued;
    }

    public void setIssued(boolean issued) {
        this.issued = issued;
    }

    public LocalDateTime getDateOfIssued() {
        return dateOfIssued;
    }

    public void setDateOfIssued(LocalDateTime dateOfIssued) {
        this.dateOfIssued = dateOfIssued;
    }


    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
