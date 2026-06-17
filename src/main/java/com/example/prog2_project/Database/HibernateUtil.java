package com.example.prog2_project.Database;

import com.example.prog2_project.Medien.Medium;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
/*Diese Klasse dient als, man könnte es vermittler zwichen der Hibernate.cfg.xml und der Anwendung. es "Übersetzt" i grunde dann das die verbindung funktioniert.*/
public class HibernateUtil {

    private static final SessionFactory sessionFactory = build();

    private static SessionFactory build() {
        try {
            return new Configuration()
                    .configure("hibernate.cfg.xml")
                    .addAnnotatedClass(Medium.class)
                    .buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
