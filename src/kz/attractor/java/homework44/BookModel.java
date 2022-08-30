package kz.attractor.java.homework44;

import com.google.gson.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BookModel {
    private Book book;

    public BookModel() {
        book = readBook();
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public static Book readBook(){
        String json = getJson("./book.json");
        Gson gson = new Gson();
        return gson.fromJson(json, Book.class);
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
