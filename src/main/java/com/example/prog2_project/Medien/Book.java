package com.example.prog2_project.Medien;

import jakarta.persistence.Entity;

@Entity
public class Book extends Medium {

    public Book(String titel, String genre, String description, int bewertung, String art, boolean gesehen, String url) {
        super(titel, genre, description, bewertung, art, gesehen, url);
    }

    public Book() {
    }
}
