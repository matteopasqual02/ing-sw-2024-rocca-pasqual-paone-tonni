package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

import java.util.Arrays;

public class BoardDeck {
    private ResourceCard[] resourceCards=new ResourceCard[2];
    private GoldCard[] goldCards=new GoldCard[2];
    private ObjectiveCard[] commonGoals=new ObjectiveCard[2];
    private PlayingCard temp;

    public BoardDeck(ResourceCard[] rc,GoldCard[] gc, ObjectiveCard[] oc){
        this.resourceCards= Arrays.copyOf(rc,2);
        this.goldCards= Arrays.copyOf(gc,2);
        this.commonGoals= Arrays.copyOf(oc,2);
    }
    //ci sara un metodo in game che estra 2 carte da ogni mazzo e le mette in un array che poi passa al costruttore della board

    public PlayingCard draw(int position,DrawableDeck d){ //le 4 carte nella board sono chiamate 1,2,3,4, passando la position il client puo decidere quale carta vuole percare tra le 4, quindi indirettamente nache il suo tipo
        if(position==1 || position==2){
            temp=resourceCards[position-1];
            resourceCards[position-1]=d.drawFirstResource();
            return temp;
        }
        else {
            temp=goldCards[position-3];
            goldCards[position-1]=d.drawFirstGold();
            return temp;
        }
    }
}
