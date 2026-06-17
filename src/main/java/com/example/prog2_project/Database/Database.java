package com.example.prog2_project.Database;

import com.example.prog2_project.Medien.Medium;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Database implements DatabaseDAO {
    /*Das hier ist die eigentliche Datenbank klasse, sie "Übersetzt" die Obejkte meines codes dann ensprechend.*/
    private SessionFactory sessionFactory;
    /**
    * Einige der Funktionen sind alte Überbleibsel aus vorangegangenen Versionen, der Grund warum sie behalten wurden ist schlicht. Der Coed wird Privat weiter geführt (https://github.com/BackupFoundLostCode?tab=repositories) und man sie vielleicht im zukunft doch nutzt in irgendeiner weise.
    * Was macht public Database(): Sie erzeugt eine Session zur DB, diese kann dann genutzt werden um die befehle zur DB auszuführen, der Vorteil ist das man nicht immer wieder eine nue Verbindung zu öffen, das wird nähmlich tuer.
    * Was macht insert(): Insert hat die session und macht in dieser session dann transaktionen. Hier sind die sachen, die nach .beginTransaction(); teil der transaktion
    *                     Wichtig ist dann das session.persist(media); das ein Objekt dann in der DB speichert und mit session.getTransaction().commit(); dann persistent wird. Am ende wird nur noch die verbindung geschlossen.
    * Was macht update(): Update unterschiedet sich von insert() eigentlich nur durch den befehl session.merge(medium); er updatet die Column in der DB entsprechend der neuen daten des übergebenem Objects.
    * Was macht select(): Diese Funktion bekommt eine id als int übergeben, interessanter weise reicht hier nur ein einfaches session.get() mit Verweis auf die klasse und der übergeben id. Intern wird abgeglichen und dann in dem Objekt gespeichert und dieses zurückgegeben.
    * Was macht delete(): Ablauf ist genau wie in inster() und select() nur mit session.remove(data); was den datensatz in der DB löscht.
     * Was macht selectAll(): Die Metode gibt ein Set zurück, die eingetliche idee war ein setz zu nutzen um keine Doppelten einträge zu bekommen, da die id aber immer eine andere ist war das nicht so erfolgreich. Die Architektur wurde dennoch beibehalten.
    *                        Zuerst wird eine Liste erzeugt, diese macht auc nur eine ähnliche abfrage nur mit dem unterschid das diese nach den Objekten ansich fraht nicht nach einem bstimmten und diese einträge dann in der liste landen. Das ganze wird dann einem Set pnergeben, und returnt.
    *                        Der grund warum es eien Kosnolen output gibt ist nur zur kontrolle das es auch tut was es soll und läd. Diese methode wird recht häufig genutzt dahher war das in ihrer entsehungszeit von höherer beduetung als jetzt.
    * Was macht selectAllwithTitel(String titel): Der unterschid zu selectAll() ist kein sondrlich großer, nur dass es eine weiteres Set gibt welches durch die For die einträge speicert deren titel mit dem übegeben string übereinstimmt, return wird dann das Set mit den einträgen zu dem titel.
    * Was macht selectAllWithRating: Diese macht exakt das gleiche wie electAllwithTitel(String titel) nur eben mit einem anderen übergabeparameter für das Rating.
    * Was macht selectAllwithGenre: "..." anderem Übergabeparmeter für Genre.
    * Was macht selectAllwithArt: "..." anderem Übergabeparmeter für Art.
    * Was macht deleteWhereTitleIs: Funktioniert ebenfalss fast wie selectAll() mit dem unterschied das man nur ein set hat und in der for bei gefundenem element session.remove(m); nutzt um das ganze von dergegnerellen DB zu entfernen.* Was macht deleteWhereGenreIs: Unterschid zur deleteWhereTitleIs it nur der Übergabeparemeter.
    * Was macht deleteWhereArtIs: Unterschid zur deleteWhereTitleIs it nur der Übergabeparemeter.
     **/
    public Database(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    //
    @Override
    public void insert(Medium media) {
        Session session = sessionFactory.openSession();
        //
        session.beginTransaction();
        session.persist(media);
        session.getTransaction().commit();
        session.close();
        //
    }
    //
    @Override
    public void update(Medium medium){
        //
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.merge(medium);
        session.getTransaction().commit();
        session.close();
        //
    }
    //
    @Override
    public Medium select(int id) {
        Session session = sessionFactory.openSession();
        Medium data= session.get(Medium.class, id);
        //
        session.close();
        System.out.println("Titel: "+ data.getTitel() +" Genre: "+ data.getGenre() + " Description: "+ data.getDescription() + " Bewertung: "+ data.getBewertung());
        //
        return data;
    }
    //
    @Override
    public Set<Medium> selectAll() {
        Session session = sessionFactory.openSession();
        //
        List<Medium> list = session.createQuery("FROM Medium", Medium.class).getResultList();
        //
        Set<Medium> all = new LinkedHashSet<>(list);
        //
        for (Medium m : all) {
            System.out.println(
                    "|Titel: " + m.getTitel() + " |Genre: " + m.getGenre() + " |Beschreibung: " + m.getDescription() + " |Bewertung: " + m.getBewertung() + "|"
            );
        }
        //
        session.close();
        return all;
        //
    }
    //
    @Override
    public Set<Medium> selectAllwithTitel(String titel){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        //
        List<Medium> list = session
                .createQuery("FROM Medium", Medium.class)
                .getResultList();
        //
        Set<Medium> all = new LinkedHashSet<>(list);
        Set<Medium> allwithTitel = new LinkedHashSet<>();
        //
        for (Medium m : all) {
            if (m.getTitel().equals(titel)) {
                allwithTitel.add(m);
            }
        }
        //
        session.close();
        return allwithTitel;
        //
    }
    //
    @Override
    public Set<Medium> selectAllWithRating(int rating){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        //
        //
        List<Medium> list = session
                .createQuery("FROM Medium", Medium.class)
                .getResultList();
        //
        Set<Medium> all = new LinkedHashSet<>(list);
        Set<Medium> allwithRating = new LinkedHashSet<>();
        //
        for (Medium m : all) {
            if (m.getBewertung() == rating) {
                allwithRating.add(m);
            }
        }
        //
        session.close();
        return allwithRating;
    }
    //
    @Override
    public Set<Medium> selectAllwithGenre(String genre){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        //
        List<Medium> list = session
                .createQuery("FROM Medium", Medium.class)
                .getResultList();
        //
        Set<Medium> all = new LinkedHashSet<>(list);
        Set<Medium> allwithTitel = new LinkedHashSet<>();
        //
        for (Medium m : all) {
            if (m.getGenre().equals(genre)) {
                allwithTitel.add(m);
            }
        }
        //
        session.close();
        return allwithTitel;
        //
    }
    //
    @Override
    public Set<Medium> selectAllwithArt(String art){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        //
        List<Medium> list = session
                .createQuery("FROM Medium", Medium.class)
                .getResultList();
        //
        Set<Medium> all = new LinkedHashSet<>(list);
        Set<Medium> allwithTitel = new LinkedHashSet<>();
        //
        for (Medium m : all) {
            if (m.getArt().equals(art)) {
                allwithTitel.add(m);
            }
        }
        //
        session.close();
        return allwithTitel;
        //
    }
    //
    @Override
    public void deleteWhereTitleIs(String titel){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        //
        List<Medium> list = session
                .createQuery("FROM Medium", Medium.class)
                .getResultList();
        //
        Set<Medium> all = new HashSet<>(list);
        //
        for (Medium m : all) {
            if (m.getTitel().equals(titel)) {
                session.remove(m);
            }
        }
        session.getTransaction().commit();
        session.close();
        //
    }
    //
    @Override
    public void deleteWhereGenreIs(String genre){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        //
        List<Medium> list = session
                .createQuery("FROM Medium", Medium.class)
                .getResultList();
        //
        Set<Medium> all = new HashSet<>(list);
        Set<Medium> allwithTitel = new HashSet<>();
        //
        for (Medium m : all) {
            if (m.getGenre().equals(genre)) {
                session.remove(m);
            }
        }
        session.getTransaction().commit();
        session.close();
    }
    //
    @Override
    public void deleteWhereArtIs(String art){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        //
        List<Medium> list = session
                .createQuery("FROM Medium", Medium.class)
                .getResultList();

        Set<Medium> all = new HashSet<>(list);
        //
        for (Medium m : all) {
            if (m.getArt().equals(art)) {
                session.remove(m);
            }
        }
        session.getTransaction().commit();
        session.close();
        //
    }
    //
    @Override
    public void delete(int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        //
        Medium data= session.get(Medium.class, id);
        session.remove(data);
        session.getTransaction().commit();
        session.close();
        //
    }
    //
}
