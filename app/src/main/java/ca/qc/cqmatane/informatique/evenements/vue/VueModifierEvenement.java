package ca.qc.cqmatane.informatique.evenements.vue;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ca.qc.cqmatane.informatique.evenements.R;
import ca.qc.cqmatane.informatique.evenements.donnees.DAOEvenements;
import ca.qc.cqmatane.informatique.evenements.modele.Evenement;

public class VueModifierEvenement extends AppCompatActivity {

    protected DAOEvenements accesseurDAO = DAOEvenements.getInstance();
    protected EditText champTitre;
    protected EditText champLieu;
    protected EditText champJour;
    protected EditText champMois;
    protected EditText champAnnee;
    protected EditText champHeure;
    protected EditText champMinutes;
    protected EditText champDescriptions;
    protected Evenement evenement;
    protected Button boutonModifier;
    protected Button boutonAnnuler;
    protected Button boutonSupprimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_modifier_evenement);

        Bundle parametres = this.getIntent().getExtras();
        int id_evenement = Integer.parseInt((String) parametres.get("id_evenement"));
        evenement = (Evenement) accesseurDAO.trouverEvenement(id_evenement);

        champTitre = (EditText)findViewById(R.id.vue_texte_titre_modification_evenement);
        champTitre.setText(evenement.getTitre());
        champLieu = (EditText)findViewById(R.id.vue_texte_lieu_modification_evenement);
        champLieu.setText(evenement.getLieu());
        champJour = (EditText)findViewById(R.id.vue_texte_jour_modification_evenement);
        champJour.setText(String.format("%02d",evenement.getJour()));
        champMois = (EditText)findViewById(R.id.vue_texte_mois_modification_evenement);
        champMois.setText(String.format("%02d",evenement.getMois()));
        champAnnee = (EditText)findViewById(R.id.vue_texte_annee_modification_evenement);
        champAnnee.setText(String.valueOf(evenement.getAnnnee()));
        champHeure = (EditText)findViewById(R.id.vue_texte_heure_modification_evenement);
        champHeure.setText(String.format("%02d",evenement.getHeure()));
        champMinutes = (EditText)findViewById(R.id.vue_texte_minutes_modification_evenement);
        champMinutes.setText(String.format("%02d",evenement.getMinutes()));
        champDescriptions = (EditText)findViewById(R.id.vue_texte_description_modification_evenement);
        if (evenement.getDescription()!=null){
            champDescriptions.setText(evenement.getDescription());
        }
        boutonModifier = (Button)findViewById(R.id.action_modifier_modification_evenement);
        boutonModifier.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                modifierEvenement(view);
            }
        });

        boutonAnnuler = (Button)findViewById(R.id.action_annuler_modification_evenement);
        boutonAnnuler.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                naviguerRetourEvenements();
            }
        });
        boutonSupprimer = (Button)findViewById(R.id.action_supprimer_modification_evenement);
        boutonSupprimer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                supprimerEvenement(view);
                naviguerRetourEvenements();


            }
        });


    }

    protected void modifierEvenement(View view){

        try {
            if (champTitre.getText().toString().trim().length()<1) {
                throw new Exception("Veuillez insérer un titre");
            }
            String titre = champTitre.getText().toString();
            if (champLieu.getText().toString().trim().length()<1) {
                throw new Exception("Veuillez insérer un lieu");
            }
            String lieu = champLieu.getText().toString();

            if (champJour.getText().toString().isEmpty()) {
                throw new Exception("Jour incorrecte");
            }
            int jour = Integer.parseInt(champJour.getText().toString());
            if (jour < 1 || jour > 31 || titre.isEmpty()) {
                throw new Exception("Jour incorrect");
            }

            if (champMois.getText().toString().isEmpty()) {
                throw new Exception("Mois incorrect");
            }
            int mois = Integer.parseInt(champMois.getText().toString());
            if (mois < 1 || mois > 12) {
                throw new Exception("Mois incorrecte");
            }

            if (champAnnee.getText().toString().isEmpty()) {
                throw new Exception("Année incorrecte");
            }
            int annee = Integer.parseInt(champAnnee.getText().toString());
            if (annee < 0 || annee > 10000) {
                throw new Exception("Année incorrecte");
            }

            if (champHeure.getText().toString().isEmpty()) {
                throw new Exception("Heure incorrecte");
            }
            int heure = Integer.parseInt(champHeure.getText().toString());
            if (heure < 0 || heure > 23) {
                throw new Exception("Heure incorrecte");
            }

            if (champMinutes.getText().toString().isEmpty()) {
                throw new Exception("Minutes incorrecte");
            }
            int minutes = Integer.parseInt(champMinutes.getText().toString());
            if (minutes < 0 || minutes > 59) {
                throw new Exception("Minutes incorrecte");
            }

            String description = champDescriptions.getText().toString();

            evenement.setTitre(titre);
            evenement.setLieu(lieu);
            evenement.setDate(jour,mois,annee,heure,minutes);
            evenement.setDescription(description);

            accesseurDAO.modifierEvenement(evenement);
            naviguerRetourEvenements();
            Toast test = Toast.makeText(getApplicationContext(),"Evènement modifié",Toast.LENGTH_LONG);
            test.show();


        }catch (Exception ex){
            Toast test = Toast.makeText(getApplicationContext(),ex.getMessage(),Toast.LENGTH_LONG);
            test.show();
            return;
        }


    }

    protected void supprimerEvenement(View view){
        accesseurDAO.supprimerEvenement(evenement.getId());
        Toast test = Toast.makeText(getApplicationContext(),"Evènement supprimé",Toast.LENGTH_LONG);
        test.show();
    }

    protected void naviguerRetourEvenements(){
        this.finish();
    }


}
