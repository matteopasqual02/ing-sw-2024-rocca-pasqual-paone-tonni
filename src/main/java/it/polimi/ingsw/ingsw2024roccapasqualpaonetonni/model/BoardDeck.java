package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.GoldCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.PlayingCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.ResourceCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.objective.ObjectiveCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.NoCardException;

import java.io.Serializable;

/**
 * The type Board deck.
 */
public class BoardDeck implements Serializable {
    /**
     * The Cards 1.
     */
    private final PlayingCard[] cards1;
    /**
     * The Cards 2.
     */
    private final PlayingCard[] cards2;
    /**
     * The Common goals.
     */
    private final ObjectiveCard[] commonGoals;
    /**
     * The Model.
     */
    private final Game model;

    /**
     * Instantiates a new Board deck.
     *
     * @param model the model
     */
    public BoardDeck(Game model){
        this.cards1 = new PlayingCard[2];
        this.cards2 = new PlayingCard[2];
        this.commonGoals =new ObjectiveCard[2];
        this.model = model;
    }

    /**
     * Instantiates a new Board deck.
     *
     * @param rc    the rc
     * @param gc    the gc
     * @param oc    the oc
     * @param model the model
     */
    public BoardDeck(ResourceCard[] rc, GoldCard[] gc, ObjectiveCard[] oc, Game model){
        this.cards1 = rc;
        this.cards2 = gc;
        this.commonGoals = oc;
        this.model = model;
    }

    /**
     * Set resource cards.
     *
     * @param pc       the pc
     * @param position the position
     */
    public void setResourceCards(ResourceCard pc, int position){
        cards1[position]=pc;
    }

    /**
     * Set gold cards.
     *
     * @param pc       the pc
     * @param position the position
     */
    public void setGoldCards(GoldCard pc, int position){
        cards2[position]=pc;
    }

    /**
     * Set objective cards.
     *
     * @param pc       the pc
     * @param position the position
     */
    public void setObjectiveCards(ObjectiveCard pc, int position){
        commonGoals[position]=pc;
    }

    /**
     * Draw playing card.
     *
     * @param position the position
     * @return the playing card
     * @throws NoCardException the no card exception
     */
    public PlayingCard draw(int position) throws NoCardException {
        PlayingCard temp;

        if(position==1 || position==2){
            temp = cards1[position-1];
            if (temp == null) {
                throw new NoCardException("No card in position " + position);
            }
            try {
                cards1[position-1] = model.getGameDrawableDeck().drawFirstResource();
            }
            catch(Exception e){
                try {
                    cards1[position-1] = model.getGameDrawableDeck().drawFirstGold();
                }
                catch(Exception e1){
                    e1.printStackTrace();
                }
            }
        }
        else {
            temp=cards2[position-3];
            if (temp == null) {
                throw new NoCardException("No card in position " + position);
            }
            try {
                cards2[position-3]=model.getGameDrawableDeck().drawFirstGold();
            }
            catch(Exception e){
                try {
                    cards2[position-3] = model.getGameDrawableDeck().drawFirstResource();
                }
                catch(Exception e1){
                    e1.printStackTrace();
                }
            }
        }
        return temp;
    }

    /**
     * Gets common objective.
     *
     * @param i the
     * @return the common objective
     */
    public ObjectiveCard getCommonObjective(int i) {
        if (i == 0 || i == 1) {
            return commonGoals[i];
        } else {
            return null;
        }
    }

    /**
     * Gets card.
     *
     * @param position the position
     * @return the card
     */
    public PlayingCard getCard(int position) {
        if (position == 1 || position == 2) {
            return cards1[position-1];
        }
        else if (position == 3 || position == 4) {
            return cards2[position-3];
        }
        else {
            return null;
        }
    }

    /**
     * Is empty boolean.
     *
     * @return the boolean
     */
    public boolean isEmpty() {
        return cards1[0] == null
                && cards1[1] == null
                && cards2[0] == null
                && cards2[1] == null;
    }

    /**
     * Get drawable deck drawable deck.
     *
     * @return the drawable deck
     */
    public DrawableDeck getDrawableDeck(){
        return model.getGameDrawableDeck();
    }

}
