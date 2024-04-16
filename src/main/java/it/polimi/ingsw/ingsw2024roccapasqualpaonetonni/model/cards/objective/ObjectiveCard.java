package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.objective;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.Card;

public abstract class ObjectiveCard extends Card {
    protected final int points;
    protected ObjectivePointsStrategy objectivePointsStrategy;

    public ObjectiveCard(int id, int points){
        super(id);
        this.points = points;
    }

    public int pointCard(Player player){
        return 0;
    }

    public int getPoints(){
        return points;
    }
}

