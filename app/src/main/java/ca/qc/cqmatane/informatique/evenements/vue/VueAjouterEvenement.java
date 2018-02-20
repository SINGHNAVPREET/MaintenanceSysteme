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

public class VueAjouterEvenement extends AppCompatActivity {

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
    protected Button boutonValider;
    protected Button boutonAnnuler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_ajouter_evenement);

        champTitre = (EditText)findViewById(R.id.vue_texte_titre_ajout_evenement);
        champLieu = (EditText)findViewById(R.id.vue_texte_lieu_ajout_evenement);
        champJour = (EditText)findViewById(R.id.vue_texte_jour_ajout_evenement);
        champMois = (EditText)findViewById(R.id.vue_texte_mois_ajout_evenement);
        champAnnee = (EditText)findViewById(R.id.vue_texte_annee_ajout_evenement);
        champHeure = (EditText)findViewById(R.id.vue_texte_heure_ajout_evenement);
        champMinutes = (EditText)findViewById(R.id.vue_texte_minutes_ajout_evenement);
        champDescriptions = (EditText)findViewById(R.id.vue_texte_description_ajout_evenement);
        boutonValider = (Button)findViewById(R.id.action_ajouter_ajout_evenement);
        boutonValider.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                ajouterEvenement(view);
            }
        });
        boutonAnnuler = (Button)findViewById(R.id.action_annuler_ajout_evenement);
        boutonAnnuler.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                naviguerRetourEvenements();
            }
        });
    }
    protected void ajouterEvenement(View view){

        evenement = new Evenement();

        try {
            if (champTitre.getText().toString().trim().length()<1) {
                throw new Exception("Veuillez insérer un titre");
            }
            String titre = champTitre.getText().toString();
            evenement.setTitre(titre);

            if (champLieu.getText().toString().trim().length()<1) {
                throw new Exception("Veuillez insérer un lieu");
            }
            String lieu = champLieu.getText().toString();
            evenement.setLieu(lieu);

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
            evenement.setDate(jour,mois,annee,heure,minutes);

            if (champDescriptions.getText().toString().trim().length()<1) {
                accesseurDAO.ajouterEvenement(evenement);
               
            }
            else{
                String description = champDescriptions.getText().toString();
                evenement.setDescription(description);
                accesseurDAO.ajouterEvenement(evenement);
            }

        }catch (Exception ex){
            Toast test = Toast.makeText(getApplicationContext(),ex.getMessage(),Toast.LENGTH_LONG);
            test.show();
            return;
        }


    }

    public void naviguerRetourEvenements(){
        this.finish();
    }
}
