package kz.attractor.java.homework44;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Employee {
    private String id;
    private String sessionId;
    private List<String> currentBooks;
    private List<String> issuedBooks;
    private String employeeName;
    private String password;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Employee(String id, List<String> currentBooks, List<String> issuedBooks, String employeeName, String password) {
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

    public List<String> getCurrentBooks() {
        return currentBooks;
    }

    public void setCurrentBooks(List<String> currentBooks) {
        this.currentBooks = currentBooks;
    }

    public List<String> getIssuedBooks() {
        return issuedBooks;
    }

    public void setIssuedBooks(List<String> issuedBooks) {
        this.issuedBooks = issuedBooks;
    }

    public void makeSessionId() {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            setSessionId(convertToString(md.digest(employeeName.getBytes())));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
    private String convertToString(byte[] array) {
        return IntStream.range(0, array.length / 4)
                .map(i -> array[i])
                .map(i -> (i < 0) ? i + 127 : i)
                .mapToObj(Integer::toHexString)
                .collect(Collectors.joining());
    }
}
