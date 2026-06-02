package com.example.prog2_project.Database;

import com.example.prog2_project.Medien.Medium;

import java.util.Set;

public interface DatabaseDAO {

    void insert(Medium media);

    void update(Medium medium);

    Medium select(int id);

    Set<Medium> selectAll();

    Set<Medium> selectAllwithTitel(String titel);

    Set<Medium> selectAllwithGenre(String genre);

    Set<Medium> selectAllwithArt(String art);

    void deleteWhereTitleIs(String titel);

    void deleteWhereGenreIs(String genre);

    void deleteWhereArtIs(String art);

    void delete(int id);
}
