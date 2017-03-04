/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Brion Mario, Apparecium Labs
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Reel {

    ArrayList<Symbol> symbols = new ArrayList<Symbol>();

    public Reel() {
        // cherry object
        Symbol cherry = new Symbol("images/cherry.png",2);

        Symbol lemon = new Symbol("images/lemon.png",3);

        Symbol plum = new Symbol("images/plum.png",4);

        Symbol watermelon = new Symbol("images/watermelon.png",5);

        Symbol bell = new Symbol("images/bell.png",6);

        Symbol redseven = new Symbol("images/redseven.png",7);

       symbols.add(cherry);
       symbols.add(watermelon);
       symbols.add(lemon);
       symbols.add(redseven);
       symbols.add(bell);
       symbols.add(plum);

    }

    public ArrayList Spin(ArrayList symbols){
        Collections.shuffle(symbols);
        return symbols;
    }

     
}
