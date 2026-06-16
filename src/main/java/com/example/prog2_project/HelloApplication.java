package com.example.prog2_project;

import com.example.prog2_project.Database.Database;
import com.example.prog2_project.Database.HibernateUtil;
import com.example.prog2_project.Medien.Book;
import com.example.prog2_project.Medien.Medium;
import com.example.prog2_project.Medien.Movie;
import com.example.prog2_project.Medien.Show;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;
import javafx.geometry.Pos;
import org.controlsfx.control.textfield.TextFields;


import java.io.IOException;
import java.util.*;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //
        Database ne = new Database(HibernateUtil.getSessionFactory());
        String css = getClass().getResource("/styles.css").toExternalForm();
        //
        int realIndex = maxID();
        //
        //Satges:
        Stage stage2 =  new Stage();
        Stage stage3 = new Stage();
        //
        //VBoxen:
        VBox box1= new VBox();
        VBox box2 = new VBox();
        VBox box3 = new VBox();
        VBox box4 = new VBox();
        VBox box5 = new VBox();
        //
        //HBoxen:
        HBox anord = new HBox();
        //
        //Scenes:
        Scene scene = new Scene(box2, 1000, 1000);
        Scene scene2 = new Scene(box3, 1000, 1000);
        Scene scene3 = new Scene(box4, 1000, 1000);
        Scene scene4 = new Scene(box5, 1000, 1000);
        Scene primaryScene = new Scene(box1, 1000, 1000);

        scene.getStylesheets().add(css);
        scene2.getStylesheets().add(css);
        scene3.getStylesheets().add(css);
        scene4.getStylesheets().add(css);
        primaryScene.getStylesheets().add(css);
        //
        //Buttons
        Button back = new Button("Back");
        Button sucheID = new Button("Search ID");
        Button sucheTitel = new Button("Search for Title");
        Button sucheGenre = new Button("Search by Genre");
        Button reload = new Button("Reload List");
        Button ratingSearch = new Button("Search by Rating");
        Button sortByID = new Button("Sort by ID");
        //
        Button findeMediumArt = new Button("Search by Medium");
        Button send = new Button("Save");
        //
        Button suche = new Button("Vault");
        Button eintrag = new Button("ADD");
        //
        //Labels:
        Label hauptBanner = new Label();
        hauptBanner.getStyleClass().add("title-label");
        hauptBanner.setText("THE MEDIA VAULT");
        //
        //Text
        Text unterteilung = new Text();
        unterteilung.getStyleClass().add("wabeldi");
        unterteilung.setText("Additional fields for entering a change:");
        //
        //TextFiekd
        TextField titel = new TextField();//Keine vorschläge
        titel.setPromptText("Title");

        TextField genre = new TextField();
        genre.setPromptText("Genre");
        TextFields.bindAutoCompletion(genre,
                "Bildung",
                "Fantasy",
                "Action",
                "Manifest",
                "Klassiker",
                "Novel",
                "Crime"
        );
        //
        TextField description = new TextField();//keine vorschläge
        description.setPromptText("Description");
        //
        TextField art = new TextField();
        art.setPromptText("Type");
        TextFields.bindAutoCompletion(art,
                "Book",
                "Show",
                "Movie"
        );
        //
        TextField url = new TextField();//Keine Vorschläge
        url.setPromptText("URL");
        //
        TextField fieldForTitle = new TextField();//Keine Vorschläge
        fieldForTitle.setPromptText("Title");
        //
        TextField findByArt = new TextField();
        findByArt.setPromptText("Type");  TextFields.bindAutoCompletion(findByArt,
                "Book",
                "Show",
                "Movie"
        );
        //
        //CheckBoxen:
        CheckBox gesehen = new CheckBox("Watched/Read?");
        //
        //Accordeon:
        Accordion accordion = new Accordion();
        //
        //Spinner:
        Spinner<Integer> rating = new Spinner<>(0,5,0);//
        Spinner<Integer> idForSuche = new Spinner<>(0,realIndex,0);//Ok lol das hat perfekt geklappt.
        //
        //Stage:
        box1.setSpacing(20);
        box1.setAlignment(Pos.CENTER);
        //
        box1.getChildren().addAll(hauptBanner, suche, eintrag);
        stage.setScene(primaryScene);
        stage.setTitle("Menu");
        stage.show();
        //
        suche.setOnAction(e -> {
            //
            Set<Medium> set;
            set= ne.selectAll();
            //
            stage.close();
            //
            back.setOnAction(event -> {
                //
                box4.getChildren().clear();
                accordion.getPanes().clear();
                titel.clear();
                genre.clear();
                stage.show();
                stage2.close();
                //
            });
            //
            VBox vbox2 = new VBox();
            vbox2.getChildren().addAll(idForSuche,titel,genre, findByArt);
            ScrollPane  scrollPane = new ScrollPane();
            //
            anord.getChildren().clear();
            anord.getChildren().addAll(sucheID, sucheTitel, sucheGenre,findeMediumArt,ratingSearch,sortByID,back);
            //
            box4.setFillWidth(true);
            //
            for (Medium m : set) {
                accordion.getPanes().add(converstMedium(set, accordion, ne, m, titel, description, genre, art, url,  rating, gesehen));
            }
            scrollPane.setContent(accordion);
            scrollPane.setFitToWidth(true);
            scrollPane.setFitToHeight(false);
            //
            ratingSearch.setOnAction(event -> {
                int ratingFromField = rating.getValue();
                Set<Medium> ratings = ne.selectAllWithRating(ratingFromField);
                //
                accordion.getPanes().clear();
                for (Medium m : ratings) {
                    accordion.getPanes().add(converstMedium(set, accordion, ne, m, titel, description, genre, art, url,  rating, gesehen));
                }
            });
            //
            sucheID.setOnAction(event -> {
                int ii = idForSuche.getValue();
                Medium m = ne.select(ii);
                System.out.println(m.toString());
                //
                accordion.getPanes().clear();
                accordion.getPanes().add(converstMedium(set, accordion, ne, m, titel, description, genre, art, url,  rating, gesehen));
            });
            //
            sucheTitel.setOnAction(event -> {
                //
                String kk = titel.getText();
                Set<Medium> lol= ne.selectAllwithTitel(kk);
                System.out.println(lol.toString());
                //
                accordion.getPanes().clear();
                for(Medium m : lol){
                    accordion.getPanes().add(converstMedium(set, accordion, ne, m, titel, description, genre, art, url,  rating, gesehen));
                }
            });
            //
            sucheGenre.setOnAction(event -> {
                //
                String ff = genre.getText();
                Set<Medium> hammer = ne.selectAllwithGenre(ff);
                System.out.println(hammer.toString());
                //
                accordion.getPanes().clear();
                for(Medium m : hammer){
                    accordion.getPanes().add(converstMedium(set, accordion, ne, m, titel, description, genre, art, url,  rating , gesehen));
                }
                //
            });
            //
            findeMediumArt.setOnAction(event -> {
                //
                String gg = findByArt.getText();
                Set<Medium> bitte = ne.selectAllwithArt(gg);
                System.out.println(bitte.toString());
                //
                accordion.getPanes().clear();
                for(Medium m : bitte){
                    accordion.getPanes().add(converstMedium(set, accordion, ne, m, titel, description, genre, art, url,  rating, gesehen));
                }
            });
            //
            reload.setOnAction(event ->{
                //
                box4.getChildren().clear();
                accordion.getPanes().clear();
                titel.clear();
                genre.clear();
                stage2.show();
                //
                for (Medium m : set) {
                    accordion.getPanes().add(converstMedium(set, accordion, ne, m, titel, description, genre, art, url,  rating, gesehen));
                }
                scrollPane.setContent(accordion);
                scrollPane.setFitToWidth(true);
                scrollPane.setFitToHeight(false);
                //
                box4.getChildren().clear();
                box4.getChildren().addAll(scrollPane,anord,vbox2, unterteilung, description ,rating,url, gesehen,reload);
                //
            });
            //
            sortByID.setOnAction(event -> {
                //set ist schon alles drinnen nur noch nicht sortiert
                List<Medium> sortedSet = new ArrayList<>(set);
                sortedSet.sort(Comparator.comparingInt(Medium::getId));
                //
                box4.getChildren().clear();
                accordion.getPanes().clear();
                titel.clear();
                genre.clear();
                stage2.show();
                //
                for (Medium m : sortedSet) {
                    accordion.getPanes().add(converstMedium(set, accordion, ne, m, titel, description, genre, art, url,  rating, gesehen));
                }
                scrollPane.setContent(accordion);
                scrollPane.setFitToWidth(true);
                scrollPane.setFitToHeight(false);
                //
                box4.getChildren().clear();
                box4.getChildren().addAll(scrollPane,anord,vbox2, unterteilung, description ,rating,url, gesehen,reload);

            });
            //
            box4.getChildren().clear();
            box4.getChildren().addAll(scrollPane,anord,vbox2, unterteilung, description ,rating,url, gesehen,reload);
            stage2.setTitle("Search");
            stage2.setScene(scene3);
            stage2.show();
            //
        });
        //
        eintrag.setOnAction(e -> {
            //
            stage.close();
            //
            box3.getChildren().addAll(titel, genre, description, rating, art, gesehen,url);
            //
            back.setOnAction(event -> {
                //
                box3.getChildren().clear();
                accordion.getPanes().clear();
                titel.clear();
                genre.clear();
                stage.show();
                stage3.close();
                //
            });
            //
            send.setOnAction(event -> {
                try {
                    String titelM =  titel.getText();
                    String genreM =  genre.getText();
                    String descriptionM =  description.getText();
                    String artM =  art.getText();
                    String urlM =  url.getText();
                    boolean gesehenM = gesehen.isSelected();
                    int value = rating.getValue();
                    //
                    if(titelM != null && !titelM.isEmpty() && genreM != null && !genreM.isEmpty() && descriptionM != null && !descriptionM.isEmpty() && artM != null && !artM.isEmpty() && urlM != null && !urlM.isEmpty()){
                        switch(artM) {
                            case "Book": {
                                Book book = new Book(titelM, genreM, descriptionM, value, artM, gesehenM, urlM);
                                //
                                ne.insert(book);
                                //
                                titel.clear();
                                genre.clear();
                                description.clear();
                                art.clear();
                                url.clear();
                                //
                                break;
                            }
                            case "Show": {
                                Show show = new Show(titelM, genreM, descriptionM, value, artM, gesehenM, urlM);
                                //
                                ne.insert(show);
                                //
                                titel.clear();
                                genre.clear();
                                description.clear();
                                art.clear();
                                url.clear();
                                //
                                break;
                                //
                            }
                            case "Movie": {
                                Movie movie2 = new Movie(titelM, genreM, descriptionM, value, artM, gesehenM, urlM);
                                //
                                ne.insert(movie2);
                                //
                                titel.clear();
                                genre.clear();
                                description.clear();
                                art.clear();
                                url.clear();
                                //
                                break;
                            }
                            default: {
                                System.out.println("Invalid art! ALLOWED: Book, Show, Movie");
                            }
                        }
                    }else {
                        System.out.println("Not all fields have been filled in");
                    }
                }
                catch (Exception e1){
                    e1.printStackTrace();
                }
            });
            //
            box3.getChildren().addAll(send, back);
            stage3.setTitle("ADD");
            stage3.setScene(scene2);
            stage3.show();
            //
        });
        //
    }
    //
    public Rating ratinInStars(int ratingInpurInt){
        //
        Rating stars =  new Rating();
        stars.setMax(5);
        stars.setRating(ratingInpurInt);
        stars.setPrefHeight(33);
        //
        return stars;
        //
    }
    //
    public int maxID() {
        //
        Database ne = new Database(HibernateUtil.getSessionFactory());
        int max = 0;
        Set<Medium> set = ne.selectAll();
        //
        for (Medium m : set) {
            if (m.getId() > max) {
                max = m.getId();
            }
        }
        //
        return max;
        //
    }
    //
    public ImageView urlConverter(String url){
        //
        Image image = new Image(url);
        ImageView  imageView = new ImageView(image);
        imageView.setFitWidth(150);
        imageView.setFitHeight(200);
        //
        return imageView;
    }
    //
    public TitledPane converstMedium(Set<Medium> set, Accordion accordion,Database ne,Medium m,TextField titel, TextField description, TextField genre, TextField art, TextField url, Spinner<Integer> rating,CheckBox gesehen){
        //
        VBox root = new VBox();
        HBox hBox = new HBox();
        //
        Button updateEintrag = new Button("Change");
        Button deleteEintrag = new Button("Delete");
        //
        updateEintrag.setOnAction(event -> {
            try {
                //
                if (!titel.getText().isEmpty()) {
                    m.setTitel(titel.getText());
                }
                //
                if (!description.getText().isEmpty()) {
                    m.setDescription(description.getText());
                }
                //
                if (!genre.getText().isEmpty()) {
                    m.setGenre(genre.getText());
                }
                //
                if (!art.getText().isEmpty()) {
                    m.setArt(art.getText());
                }//
                if (!url.getText().isEmpty()) {
                    m.setUrl(url.getText());
                }//
                m.setBewertung(rating.getValue());
                m.setGesehen(gesehen.isSelected());
                //
                ne.update(m);
                //
                accordion.getPanes().clear();
                accordion.getPanes().add(converstMedium(set,accordion, ne, m, titel, description, genre, art, url, rating, gesehen));
                //
            }
            catch (Exception e){
                e.printStackTrace();
            }
        });
        //
        deleteEintrag.setOnAction(event -> {
            //
            ne.delete(m.getId());
            set.remove(m);
            //
        });
        //
        hBox.getChildren().addAll(updateEintrag, deleteEintrag);
        //
        ImageView picture = urlConverter(m.getUrl());
        Label id = new Label("ID: " + m.getId());
        Label titel2 = new Label("Title: " + m.getTitel());
        Label description2 = new Label("Description: " + m.getDescription());
        Rating ratingLabel2 = ratinInStars(m.getBewertung());
        Label genre2 = new Label("Genre: " + m.getGenre());
        Label gesehen2 = new Label("Watched/Read: " + m.isGesehen());
        //
        root.getChildren().addAll(picture,id,titel2,description2,genre2,ratingLabel2,gesehen2,hBox);
        //
        TitledPane titledPane = new TitledPane();
        titledPane.setText(m.getTitel() +" "+"["+m.getArt()+"]");
        titledPane.setContent(root);

        return titledPane;
    }
    //
}
