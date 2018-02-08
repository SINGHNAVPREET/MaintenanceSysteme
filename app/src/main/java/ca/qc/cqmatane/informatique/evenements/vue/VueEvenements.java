package ca.qc.cqmatane.informatique.evenements.vue;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

import ca.qc.cqmatane.informatique.evenements.R;
import ca.qc.cqmatane.informatique.evenements.donnees.BaseDeDonnees;
import ca.qc.cqmatane.informatique.evenements.donnees.DAOEvenements;

public class VueEvenements extends AppCompatActivity {

    protected DAOEvenements accesseurEvenements;
    protected ListView vueListeEvenements;
    protected List<HashMap<String,String>> listeEvenements;
    protected final static int ACTIVITE_AJOUTER_EVENEMENT = 1;
    protected final static int ACTIVITE_MODIFIER_EVENEMENT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_evenements);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BaseDeDonnees.getInstance(getApplicationContext());
        accesseurEvenements = DAOEvenements.getInstance();

        vueListeEvenements = (ListView)findViewById(R.id.vue_liste_evenements);
        vueListeEvenements.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int positionDansAdapteur, long positionItem) {
                ListView vueListeEvenements = (ListView) view.getParent();
                HashMap<String,String> evenement = (HashMap<String,String>) vueListeEvenements.getItemAtPosition((int) (positionItem));
                Intent intentionNaviguerVueModificationEvenement = new Intent(VueEvenements.this, VueModifierEvenement.class);
                intentionNaviguerVueModificationEvenement.putExtra("id_evenement",evenement.get("id_evenement"));
                startActivityForResult(intentionNaviguerVueModificationEvenement,ACTIVITE_MODIFIER_EVENEMENT);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.action_ajouter_evenement);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentionNaviguerModifierEvenement = new Intent(VueEvenements.this, VueAjouterEvenement.class);
                startActivityForResult(intentionNaviguerModifierEvenement,ACTIVITE_AJOUTER_EVENEMENT);
            }
        });

        afficherTousLesEvenements();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_vue_evenements, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int activite, int resultCode, Intent data) {
        switch (activite) {
            case ACTIVITE_AJOUTER_EVENEMENT :
                afficherTousLesEvenements();
                break;
            case ACTIVITE_MODIFIER_EVENEMENT :
                afficherTousLesEvenements();
                break;
        }
    }

    public void afficherTousLesEvenements(){

        listeEvenements = accesseurEvenements.listerLesEvenementsEnHashMap();

        SimpleAdapter adapteurVueListeEvenements = new SimpleAdapter(
            this,
                listeEvenements,
                android.R.layout.two_line_list_item,
                new  String[]{"titre","date"},
                new int[]{android.R.id.text1, android.R.id.text2}
        );

        vueListeEvenements.setAdapter(adapteurVueListeEvenements);

    }
}
