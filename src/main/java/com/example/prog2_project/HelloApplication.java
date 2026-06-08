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


import java.io.IOException;
import java.util.Set;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        /*Soll noch können:
         *
         * WILL UND MUSS TUN:
         * Kategorien/Genre verwalten fehlt vielleich je nach devnition
         * Läd bei vieleln einträgen recht lange, lösbar über threads?
         *
         * ERLEDIGT:
         * Suche ist vollständig von der logik.
         * Eintrag/Hinzufügen ist von der logik her Vollständig.
         * Löschen ist von der Logik her auch fertig.
         * CSS setzen (außer das mit dem zähler aber geht eigentlich)
         * Bewertung mit Sternen (Allerdings werden die Sterne nicht vollständig angeziegt und sind noch etwas zu nahjme beieinader)
         * Bearbeiten klappt.
         * */
        Database ne = new Database(HibernateUtil.getSessionFactory());//braucht noch sessionfactory (ne scheinbar dich nicht, kann die DB methoden ausführen)
        String css = getClass().getResource("/styles.css").toExternalForm();
        //
        int realIndex = maxID();
        //
        //Satges:
        Stage stage2 =  new Stage();
        Stage stage3 = new Stage();
        //
        //VBoxen:
        VBox box1= new VBox();//verwendet für Hauptsatge
        VBox box2 = new VBox();//verwedndet für scene.
        VBox box3 = new VBox();//verwendet für scene2
        VBox box4 = new VBox();//verwendet für scene3
        VBox box5 = new VBox();//Verwendet für
        //
        //HBoxen:
        HBox anord = new HBox();//Verwedet in suche
        //
        //Scenes:
        Scene scene = new Scene(box2, 1000, 1000);//verwendet für delete
        Scene scene2 = new Scene(box3, 1000, 1000);//verwendet für eintrag
        Scene scene3 = new Scene(box4, 1000, 1000);//verwendet für suche
        Scene scene4 = new Scene(box5, 1000, 1000);//verwendet für bearbeiten
        Scene primaryScene = new Scene(box1, 1000, 1000);

        scene.getStylesheets().add(css);
        scene2.getStylesheets().add(css);
        scene3.getStylesheets().add(css);
        scene4.getStylesheets().add(css);
        primaryScene.getStylesheets().add(css);
        //
        //Buttons
        Button back = new Button("Back");//Wird überall verwendet.
        Button sucheID = new Button("Search ID");//In Suche verwendet
        Button sucheTitel = new Button("Search for title");//In Suche verwendet
        Button sucheGenre = new Button("Search by genre");//In Suche verwendet.
        Button reload = new Button("Reload");
        //
        Button findeMediumArt = new Button("“Search by medium”");//In Suche verwendet
        Button send = new Button("Save");//In Eintrag verwendet.
        //
        Button suche = new Button("Vault");//Wird in der ersten Stage verwendet
        Button eintrag = new Button("ADD");//Wird in der ersten Stage verwendet
        //
        //Labels:
        Label hauptBanner = new Label();//Soll als Überschrift mittig oben in der ersten Scene angezeigt werden also auch wo Suche delte tec stehen (bearbeiten generell noch bauen)
        hauptBanner.getStyleClass().add("title-label");
        hauptBanner.setText("THE MEDIA VAULT");
        //
        //Text
        Text unterteilung = new Text();
        unterteilung.getStyleClass().add("wabeldi");//Warum klappt das ni
        unterteilung.setText("Additional fields for entering a change:");
        //
        //TextFiekd
        TextField titel = new TextField();
        titel.setPromptText("Title");
        TextField genre = new TextField();
        genre.setPromptText("Genre");
        TextField description = new TextField();//
        description.setPromptText("Description");
        TextField art = new TextField();
        art.setPromptText("Type");
        TextField url = new TextField();
        url.setPromptText("URL");
        TextField fieldForTitle = new TextField();
        fieldForTitle.setPromptText("Title");
        TextField findByArt = new TextField();
        findByArt.setPromptText("Type");
        //
        //CheckBoxen:
        CheckBox gesehen = new CheckBox("Watched/Read?");//Noch bei bearbeiten hinzufügen, relativ wichtig.
        //
        //Accordeon:
        Accordion accordion = new Accordion();
        //
        //Spinner:
        Spinner<Integer> rating = new Spinner<>(0,5,0);//
        Spinner<Integer> idForSuche = new Spinner<>(0,realIndex,0);//Ok lol das hat perfekt geklappt.
        //
        //Erste Stage: Hauptstage.
        box1.setSpacing(20);
        box1.setAlignment(Pos.CENTER);
        //
        box1.getChildren().addAll(hauptBanner, suche, eintrag);
        stage.setScene(primaryScene);
        stage.setTitle("Menu");//Setzt dem fenster einenNamen oben.
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
            anord.getChildren().addAll(sucheID, sucheTitel, sucheGenre,findeMediumArt,back);
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
                //Klappt
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
                //Klappt
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
                box4.getChildren().clear();//Eventuell auch weil ich das nicht gemacht habe ständig abgeschmiert.
                box4.getChildren().addAll(scrollPane,anord,vbox2, unterteilung, description ,rating,url, gesehen,reload);
                //
            });
            //
            box4.getChildren().clear();//Eventuell auch weil ich das nicht gemacht habe ständig abgeschmiert.
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
        stars.setMax(5);//sollte maximales ratin sein
        stars.setRating(ratingInpurInt);//Ist das rating in starnen dann die ich vom obejkt als int übergebe
        stars.setPrefHeight(33);
        //
        return stars;
        //
    }
    //
    public int maxID() {
        //
        Database ne = new Database(HibernateUtil.getSessionFactory());//Eventell besser machen.
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
        Pane root = new Pane();//Brauche ich schibar wenn ich ein bild anzeigen will geht icht eifnach sio in die HBox.
        Image image = new Image(url);
        ImageView  imageView = new ImageView(image);
        imageView.setFitWidth(150);
        imageView.setFitHeight(200);
        root.getChildren().add(imageView);

        return imageView;
    }
    //
    public TitledPane converstMedium(Set<Medium> set, Accordion accordion,Database ne,Medium m,TextField titel, TextField description, TextField genre, TextField art, TextField url, Spinner<Integer> rating,CheckBox gesehen){
        //
        VBox root = new VBox();
        HBox hBox = new HBox();
        //
        Button updateEintrag = new Button("Change");//Wird in der ersten Stage verwendet
        Button deleteEintrag = new Button("Delete");//Wird in der ersten Stage verwendet
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
            //Hier vielleich t dann beim setz noch den eintrag löschen des die ensprechnde id oder index zu dem hat onst ist der gelöschte dennoch in der angezeigten liste auch wen er aus der eigentlichen db weg ist.
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
