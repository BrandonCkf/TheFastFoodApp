package com.example.thefastfood.menus.listOffres;

import com.example.thefastfood.menus.dataBase.DatabaseManager;

public class ListOffresPop extends ListOffres {

    public ListOffresPop(DatabaseManager dm) {
        super();
        title = "Populaire";
        list = dm.readOffre("populaire = 1");
                //new ArrayList<>();
        //for(int i=1;i<10;i++)
            //list.add(new Offre(R.drawable.na, "Offre "+i));
    }
}