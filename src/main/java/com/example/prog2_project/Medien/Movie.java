package com.example.prog2_project.Medien;

import jakarta.persistence.Entity;

@Entity
public class Movie extends Medium {

    public Movie(String titel, String genre, String description, int bewertung, String art, boolean gesehen, String url) {
        super(titel, genre, description, bewertung, art, gesehen, url);
    }

    public Movie() {
    }
}
