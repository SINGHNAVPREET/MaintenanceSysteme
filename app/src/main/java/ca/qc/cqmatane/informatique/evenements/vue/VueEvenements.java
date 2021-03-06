package ca.qc.cqmatane.informatique.evenements.vue;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Switch;
import android.widget.TextView;
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

    private static final String PREFS_NAME = "prefs";
    private static final String PREF_DARK_THEME = "dark_theme";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean useDarkTheme = preferences.getBoolean(PREF_DARK_THEME, false);

        if(useDarkTheme) {
            setTheme(R.style.AppTheme_Dark_NoActionBar);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_evenements);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Switch toggle = (Switch) findViewById(R.id.switch1);
        toggle.setChecked(useDarkTheme);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton view, boolean isChecked) {
                toggleTheme(isChecked);
            }
        });

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

    private void toggleTheme(boolean darkTheme) {
        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        editor.putBoolean(PREF_DARK_THEME, darkTheme);
        editor.apply();

        Intent intent = getIntent();
        finish();

        startActivity(intent);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
             ContextMenuInfo menuInfo) {
        if(v.getId() == R.id.vue_liste_evenements) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            menu.setHeaderTitle(listeEvenements.get(info.position).get("titre"));
            String[] menuItems = getResources().getStringArray(R.array.menu);
            menu.add(Menu.NONE, 0, 0, menuItems[0]);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int menuItemIndex = item.getItemId();
        String[] menuItems = getResources().getStringArray(R.array.menu);
        String menuItemName = menuItems[menuItemIndex];
        String listItemName = listeEvenements.get(info.position).get("titre");

        TextView text = (TextView) findViewById(R.id.action_settings);
        text.setText(String.format("Selected %s for item %s", menuItemName, listItemName));
        return true;

    }
}
