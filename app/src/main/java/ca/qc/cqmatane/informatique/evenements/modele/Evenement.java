package ca.qc.cqmatane.informatique.evenements.modele;

import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by 1743002 on 2017-09-06.
 */

public class Evenement {

    private int id;
    private String titre;
    private String lieu;
    private Calendar date = Calendar.getInstance();
    private String description;

    public Evenement() {

    }

    public Evenement(int id, String titre, String lieu, int jour, int mois, int annee, int heure, int minute, int seconde, String description) throws ParseException {

        this.id = id;
        this.titre = titre;
        this.lieu = lieu;
        this.date.set(annee,mois-1,jour,heure,minute,seconde);
        this.description = description;
    }

    public Evenement(int id, String titre, String lieu, int jour, int mois, int annee, int heure, int minute, int seconde) throws ParseException {

        this.id = id;
        this.titre = titre;
        this.lieu = lieu;
        this.date.set(annee,mois-1,jour,heure,minute,seconde);
        this.description="";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(int jour, int mois, int annee, int heure, int minute) {
        this.date.set(annee,mois-1,jour,heure,minute,00);
    }

    public int getJour(){
        return date.get(Calendar.DATE);
    }

    public int getMois(){
        return date.get(Calendar.MONTH)+1;
    }

    public int getAnnnee(){
        return date.get(Calendar.YEAR);
    }

    public int getHeure(){
        return date.get(Calendar.HOUR_OF_DAY);
    }

    public int getMinutes(){
        return date.get(Calendar.MINUTE);
    }


    @Override
    public String toString() {
        return "Evenement{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", lieu='" + lieu + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                '}';
    }

    public HashMap<String,String> exporterEnHashmap(){

        HashMap<String,String> listeEvenements = new HashMap<>();

        listeEvenements.put("id_evenement",String.valueOf(this.id));
        listeEvenements.put("titre", this.titre);
        listeEvenements.put("lieu",this.lieu);
        listeEvenements.put("date",ModeleDate.dateFrancaise(this.date));
        listeEvenements.put("description",this.description);

        return  listeEvenements;
    }

}
