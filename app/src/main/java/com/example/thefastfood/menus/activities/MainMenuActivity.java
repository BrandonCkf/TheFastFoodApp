package com.example.thefastfood.menus.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.example.thefastfood.MainActivity;
import com.example.thefastfood.R;
import com.example.thefastfood.menus.DataBase.CreateurMenu;
import com.example.thefastfood.menus.DataBase.DatabaseManager;
import com.example.thefastfood.menus.ListOffres.ListOffres;
import com.example.thefastfood.menus.ListOffres.ListOffresDrink;
import com.example.thefastfood.menus.ListOffres.ListOffresHome;
import com.example.thefastfood.menus.adapter.OffreAdapter;
import com.example.thefastfood.menus.item.Offre;

import java.util.ArrayList;

public class MainMenuActivity extends AppCompatActivity {

    TextView titleTV;
    GridView gridView;
    ArrayList<ListOffres> packOffres;
    int idPack;
    MyGestureDetectorListener myGestureDetectorListener;
    DatabaseManager databaseManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        //
        Log.i("Main", "onCreate");
        // initialisation de la BDD et remplissage si c'est le premier lancement
        initializeDB();

        // Crée le gestionnaires d'actions
        myGestureDetectorListener = new MyGestureDetectorListener(this);

        // Création des offres
        packOffres = new ArrayList<ListOffres>();
        packOffres.add(new ListOffresHome(databaseManager));
        packOffres.add(new ListOffresDrink(databaseManager));
        idPack = 0;

        // Recuperes la textview
        titleTV = findViewById(R.id.categorieMenu);
        titleTV.setText(packOffres.get(idPack).getTitle());

        // Recuperes la GridView et lui assigne un adapter
        gridView = findViewById(R.id.menu_gridview);
        gridView.setAdapter(new OffreAdapter(this, packOffres.get(idPack).getList()));




    }

    private void initializeDB(){
        // Initialisation du gestionnaire de la BDD
        databaseManager = new DatabaseManager(this);

        final String SHARED_PREFERENCES_NAME = "DB";

        // Persistence pour savoir si la base à déjà été remplie
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE);

        final String FLAG_DB = "rempli";

        if(!sharedPreferences.getBoolean(FLAG_DB, false)){
            Log.i("SharedP", "false");
            // On fait l'insertion des offres
            CreateurMenu.initialInsert(databaseManager);
            // On mets à jour l'etat du remplissage
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(FLAG_DB, true);
            editor.apply();
            Log.i("SharedP2", String.valueOf(sharedPreferences.getBoolean(FLAG_DB, false)));
        }





    }


    public void clickCardTest(View view){
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
        CardView c = (CardView) view;


    }

    /**
     * Gestion des evenements
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        myGestureDetectorListener.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    /**
     * Classe privée qui va gerer les geste communs
     */
    private class MyGestureDetectorListener extends GestureDetector{
        private static final int SWIPE_MIN_DISTANCE = 120;
        private static final int SWIPE_THRESHOLD_VELOCITY = 200;


        public MyGestureDetectorListener(final Context context) {
            super(context, new GestureDetector.SimpleOnGestureListener(){
                /**
                 * Notified of a fling event when it occurs with the initial on down {@link MotionEvent}
                 * and the matching up {@link MotionEvent}. The calculated velocity is supplied along
                 * the x and y axis in pixels per second.
                 *
                 * @param e1        The first down motion event that started the fling.
                 * @param e2        The move motion event that triggered the current onFling.
                 * @param velocityX The velocity of this fling measured in pixels per second
                 *                  along the x axis.
                 * @param velocityY The velocity of this fling measured in pixels per second
                 *                  along the y axis.
                 * @return true if the event is consumed, else false
                 */
                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

                    if (Math.abs(e1.getY()-e2.getY()) < SWIPE_MIN_DISTANCE) {
                        if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY){
                            Log.d("Log.DEBUG","// Left swipe...");

                            if (idPack<packOffres.size()-1){
                                idPack += 1;
                                titleTV.setText(packOffres.get(idPack).getTitle());
                                gridView.setAdapter(new OffreAdapter(context, packOffres.get(idPack).getList()));
                            }
                        }

                        if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY){
                            Log.d("Log.DEBUG","// Right swipe...");
                            if (idPack>0){
                                idPack -= 1;
                                titleTV.setText(packOffres.get(idPack).getTitle());
                                gridView.setAdapter(new OffreAdapter(context, packOffres.get(idPack).getList()));
                            }
                        }

                    }



                    return false;
                }
            });
        }


    }


}