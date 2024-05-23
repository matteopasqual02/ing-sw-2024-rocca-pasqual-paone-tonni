package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.objective;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.PlayerBoard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.Card;

/**
 * The type Objective card.
 */
public abstract class ObjectiveCard extends Card {
    /**
     * The Points.
     */
    protected final int points;
    /**
     * The Objective points strategy.
     */
    protected ObjectivePointsStrategy objectivePointsStrategy;

    /**
     * Instantiates a new Objective card.
     *
     * @param id     the id
     * @param points the points
     */
    public ObjectiveCard(int id, int points){
        super(id);
        this.points = points;
    }

    /**
     * Point card int.
     *
     * @param playerBoard the player board
     * @return the int
     */
    public int pointCard(PlayerBoard playerBoard){
        return 0;
    }

    /**
     * Get points int.
     *
     * @return the int
     */
    public int getPoints(){
        return points;
    }

    /**
     * To string string.
     *
     * @param line the line
     * @return the string
     */
    public String toString(int line) {
        return null;
    }
}

