package com.example.prog2_project.Medien;


import jakarta.persistence.*;

//Entweder generisch oder abstract
@Entity
public class Medium {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String titel;

    @Column
    private String genre;

    @Column
    private String  description;

    @Column
    private int bewertung;

    @Column
    private String art;

    @Column
    private boolean gesehen;

    @Column
    private String url;//dafür da die url eines bildes zu spiechern

    public Medium(String titel, String genre, String description, int bewertung, String art, boolean gesehen, String url) {
        this.titel = titel;
        this.genre = genre;
        this.description = description;
        this.bewertung = bewertung;
        this.art = art;
        this.gesehen = gesehen;
        this.url = url;
    }

    public Medium() {
    }

    //Sollte ich vermutlich so machen das es einheitlich ausgibt so ist das bei unterschiedlichen werten unterdschiedlich versetzt.
    @Override
    public String toString() {
        return "|ID:| " + getId() + "\n" + "|TITEL:| " + getTitel() + "\n" +"|GENRE:| " + getGenre() + "\n" + "|DESCRIPTION:| " + getDescription() + "\n" +"|BEWERTUNG:| " + getBewertung() + "\n" + "|ART:| " + getArt() + "\n" +"\n";//Warum muss ich 2x"\n" machen ? einmal reicht doch eigentlich.
    }


    public int getId() {
        return id;
    }


    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getBewertung() {
        return bewertung;
    }

    public void setBewertung(int bewertung) {
        this.bewertung = bewertung;
    }

    public String getArt() {
        return art;
    }

    public void setArt(String art) {
        this.art = art;
    }

    public boolean isGesehen() {
        return gesehen;
    }

    public void setGesehen(boolean gesehen) {
        this.gesehen = gesehen;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
