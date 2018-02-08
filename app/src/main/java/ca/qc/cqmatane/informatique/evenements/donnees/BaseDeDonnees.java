package ca.qc.cqmatane.informatique.evenements.donnees;

/**
 * Created by 1743002 on 2017-09-09.
 */

import android.database.sqlite.SQLiteOpenHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class BaseDeDonnees extends SQLiteOpenHelper {

    private static BaseDeDonnees instance = null;

    public static BaseDeDonnees getInstance(Context contexte)
    {

        if(null == instance) instance = new BaseDeDonnees(contexte);
        return instance;
    }

    public static BaseDeDonnees getInstance()
    {
        return instance;
    }

    public BaseDeDonnees(Context contexte) {
        super(contexte, "evenement", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "create table evenement(id_evenement INTEGER PRIMARY KEY, titre TEXT, lieu TEXT, date DATETIME, description TEXT)";
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        String DETRUIRE_TABLE = "drop table evenement";
        db.execSQL(DETRUIRE_TABLE);
        String CREATE_TABLE = "create table evenement(id_evenement INTEGER PRIMARY KEY AUTOINCREMENT, titre TEXT, lieu TEXT, date DATETIME, description TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {

        String DELETE = "delete from evenement where 1 = 1";
        db.execSQL(DELETE);

        String INSERT_1 = "insert into evenement(titre, lieu, date) VALUES(\"RDV Dentiste\",\"45 rue Gustave Eiffel\",\"2018-12-26 14:30:00\")";
        String INSERT_2 = "insert into evenement(titre, lieu, date) VALUES(\"RDV Coiffeur\",\"44 avenue Lucas Marrel\",\"2016-09-07 20:45:00\")";
        String INSERT_3 = "insert into evenement(titre, lieu, date, description) VALUES(\"Chercher enfants\",\"22 rue des Champs\",\"2013-05-05 13:58:00\",\"Penser a apporter le goûter\")";

        db.execSQL(INSERT_1);
        db.execSQL(INSERT_2);
        db.execSQL(INSERT_3);

    }

}
