package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

import java.util.Arrays;

public class BoardDeck {
    private ResourceCard[] resourceCards=new ResourceCard[2];
    private GoldCard[] goldCards=new GoldCard[2];
    private ObjectiveCard[] commonGoals=new ObjectiveCard[2];
    private PlayingCard temp;

    public BoardDeck(){
        this.resourceCards= null;
        this.goldCards= null;
        this.commonGoals= null;
    }
    public BoardDeck(ResourceCard[] rc,GoldCard[] gc, ObjectiveCard[] oc){
        this.resourceCards= Arrays.copyOf(rc,2);
        this.goldCards= Arrays.copyOf(gc,2);
        this.commonGoals= Arrays.copyOf(oc,2);
    }
    //ci sara un metodo in game che estra 2 carte da ogni mazzo e le mette in un array che poi passa al costruttore della board

    public void setResourceCards(ResourceCard pc, int position){
        resourceCards[position]=pc;
    }
    public void setGoldCardsCards(GoldCard pc, int position){
        goldCards[position]=pc;
    }public void setObjectiveCards(ObjectiveCard pc, int position){
        commonGoals[position]=pc;
    }

    public PlayingCard draw(int position,DrawableDeck d){
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
    //le 4 carte nella board sono chiamate 1,2,3,4, passando la position il client puo decidere quale carta vuole percare tra le 4, quindi indirettamente nache il suo tipo
}
