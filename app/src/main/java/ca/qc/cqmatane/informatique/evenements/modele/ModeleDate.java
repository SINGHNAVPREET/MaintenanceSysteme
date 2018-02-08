package ca.qc.cqmatane.informatique.evenements.modele;

import java.util.Calendar;

/**
 * Created by 1743002 on 2017-09-14.
 */

public class ModeleDate {

    public static String dateFrancaise(Calendar date){

        String dateConvertie = String.valueOf(date.get(Calendar.DATE)) + " " + trouverMois(date.get(Calendar.MONTH)+1)
                + " " + String.valueOf(date.get(Calendar.YEAR)) + " " + String.format("%02d",date.get(Calendar.HOUR_OF_DAY))
                + ":" + String.format("%02d",date.get(Calendar.MINUTE));

        return dateConvertie;
    }

    public static String trouverMois(int numMois){

        String mois = "";

        switch (numMois){
            case 1 :
                mois = "Janvier";
                break;
            case 2 :
                mois = "Février";
                break;
            case 3 :
                mois = "Mars";
                break;
            case 4 :
                mois = "Avril";
                break;
            case 5 :
                mois = "Mai";
                break;
            case 6 :
                mois = "Juin";
                break;
            case 7 :
                mois = "Juillet";
                break;
            case 8 :
                mois = "Août";
                break;
            case 9 :
                mois = "Septembre";
                break;
            case 10 :
                mois = "Octobre";
                break;
            case 11 :
                mois = "Novembre";
                break;
            case 12 :
                mois = "Décembre";
                break;
        }

        return  mois;
    }

    public Calendar construireDate(int jour, int mois, int annee, int heure, int minutes, int secondes){
        Calendar date = Calendar.getInstance();
        date.set(annee,mois-1,jour,heure,minutes,secondes);
        return date;
    }
}
