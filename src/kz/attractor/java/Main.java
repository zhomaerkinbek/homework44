package kz.attractor.java;

import kz.attractor.java.lesson44.Lesson44Server;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            new Lesson44Server("localhost", 9889).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        BookModel model = new BookModel();
//        System.out.println(model.getBook().isCoverSolid());
    }
}
