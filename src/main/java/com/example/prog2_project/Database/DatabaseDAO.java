package com.example.prog2_project.Database;

import com.example.prog2_project.Medien.Medium;

import java.util.Set;
/*Das hier ist nur ein DAO interface, ist nur dafür da damit ich persönlich keine Methoden vergesse die ich brauche/machen möchte. Das übergibbt mann dann der DB klasse.
* Diese zwingt einen dann die Methoden hier dort zu definieren.*/
public interface DatabaseDAO {

    void insert(Medium media);

    void update(Medium medium);

    Medium select(int id);

    Set<Medium> selectAll();

    Set<Medium> selectAllwithTitel(String titel);

    //
    Set<Medium> selectAllWithRating(int rating);

    Set<Medium> selectAllwithGenre(String genre);

    Set<Medium> selectAllwithArt(String art);

    void deleteWhereTitleIs(String titel);

    void deleteWhereGenreIs(String genre);

    void deleteWhereArtIs(String art);

    void delete(int id);
}
