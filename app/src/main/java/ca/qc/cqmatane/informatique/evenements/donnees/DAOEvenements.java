package ca.qc.cqmatane.informatique.evenements.donnees;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.sql.PreparedStatement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import ca.qc.cqmatane.informatique.evenements.modele.Evenement;

/**
 * Created by 1743002 on 2017-09-07.
 */

public class DAOEvenements {

    private static DAOEvenements instance = null;
    private Evenement evenement;
    private List<Evenement> listeEvenements;
    private BaseDeDonnees accesseurBaseDeDonnees;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static  DAOEvenements getInstance(){

        if (instance == null){
            instance = new DAOEvenements();
        }
        return  instance;
    }

    public DAOEvenements() {
        super();

        try {
            accesseurBaseDeDonnees = BaseDeDonnees.getInstance();
            listeEvenements = new ArrayList<>();

            listeEvenements = listerEvenements();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  List<Evenement> listerEvenements(){

        try {

            String LISTER_EVENEMENTS = "SELECT * FROM evenement ORDER BY date";
            Cursor curseur = accesseurBaseDeDonnees.getReadableDatabase().rawQuery(LISTER_EVENEMENTS,null);

            listeEvenements.clear();
            int index_id = curseur.getColumnIndex("id_evenement");
            int index_titre = curseur.getColumnIndex("titre");
            int index_lieu = curseur.getColumnIndex("lieu");
            int index_date = curseur.getColumnIndex("date");
            int index_description = curseur.getColumnIndex("description");

            for (curseur.moveToFirst(); !curseur.isAfterLast();curseur.moveToNext()) {

                int id = curseur.getInt(index_id);
                String titre = curseur.getString(index_titre);
                String lieu = curseur.getString(index_lieu);
                String date = curseur.getString(index_date);
                String description = curseur.getString(index_description);


                Calendar calendrier = Calendar.getInstance();
                calendrier.setTime(dateFormat.parse(date));

                int jour = calendrier.get(Calendar.DATE);
                int mois = calendrier.get(Calendar.MONTH)+1;
                int annee = calendrier.get(Calendar.YEAR);
                int heure = calendrier.get(Calendar.HOUR_OF_DAY);
                int minutes = calendrier.get(Calendar.MINUTE);

                evenement = new Evenement(id,titre, lieu, jour, mois, annee, heure, minutes, 00, description);
                listeEvenements.add(evenement);
            }

            } catch (Exception e) {
                System.out.println("ERREUR : " + e.getMessage());
            }



        return  listeEvenements;

    }

    public Evenement trouverEvenement(int id){

        for (Evenement evenement : this.listeEvenements){
            if (evenement.getId() == id){
                return  evenement;
            }
        }
        return  null;
    }

    public void ajouterEvenement(Evenement evenement){
        try {

            ContentValues contentValues = new ContentValues();
            contentValues.put("titre", evenement.getTitre());
            contentValues.put("lieu", evenement.getLieu());
            contentValues.put("date", dateFormat.format(evenement.getDate().getTime()));
            contentValues.put("description", evenement.getDescription());
            accesseurBaseDeDonnees.getWritableDatabase().insertOrThrow("evenement","", contentValues);

        } catch (Exception e) {
            System.out.println("ERREUR : " + e.getMessage());
        }
    }

    public List<HashMap<String,String>> listerLesEvenementsEnHashMap(){

        List<Evenement> listeEvenements = listerEvenements();

        List<HashMap<String,String>> listeEvenementsEnHashmap = new ArrayList<>();

        for (Evenement evenement : listeEvenements){
            listeEvenementsEnHashmap.add(evenement.exporterEnHashmap());
        }

        return  listeEvenementsEnHashmap;
    }

    public void modifierEvenement(Evenement evenement){
        accesseurBaseDeDonnees.getWritableDatabase().execSQL("UPDATE evenement SET titre =\""+ evenement.getTitre() +
                "\", lieu = \""+ evenement.getLieu() + "\", date = \"" + dateFormat.format(evenement.getDate().getTime())
        + "\", description = \""+ evenement.getDescription() + "\" WHERE id_evenement = "+ evenement.getId());

    }

    public void supprimerEvenement(int id){
        accesseurBaseDeDonnees.getWritableDatabase().delete("evenement","id_evenement = " + id,null);
    }
}
