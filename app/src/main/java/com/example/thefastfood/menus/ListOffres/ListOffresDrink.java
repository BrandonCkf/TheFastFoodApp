package com.example.thefastfood.menus.ListOffres;

import com.example.thefastfood.R;
import com.example.thefastfood.menus.item.Offre;

import java.util.ArrayList;

public class ListOffresDrink extends ListOffres {

    public ListOffresDrink() {
        super();
        title = "Drink";
        list = new ArrayList<>();
        for(int i=1;i<5;i++)
            list.add(new Offre(R.drawable.na, "Offre2 "+i));
    }
}
