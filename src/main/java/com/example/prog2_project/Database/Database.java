package com.example.prog2_project.Database;

import com.example.prog2_project.Medien.Medium;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Database implements DatabaseDAO {

    private SessionFactory sessionFactory;

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
        List<Medium> list = session
                .createQuery("FROM Medium", Medium.class)
                .getResultList();
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
