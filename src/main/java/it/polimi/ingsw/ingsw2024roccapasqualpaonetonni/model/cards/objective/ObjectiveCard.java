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

/*private int pointsPatternCard(PlayerBoard pb){
        PlayingCard[][] board = pb.getBoard();
        int totalpoints =0;
        List<PlayingCard> usedByObjectiveCard = new ArrayList<>();

        for(int i=0; i<board.length; i++){
            for(int j=0; j<board[i].length; j++){
                PlayingCard currentCard = board[i][j];
                if(currentCard!=null && primaryCard == currentCard.getSeed()
                    && !usedByObjectiveCard.contains(currentCard)){
                    //if the position is not null, the seed of the card we're looking at is the same as the primary
                    //and the card not used
                    switch (shape){
                        case "down" -> {
                            if( i+2 <board.length && j+2 < board[i].length &&
                                board[i+1][j+1] != null &&
                                board[i+1][j+1].getSeed() == primaryCard &&
                                board[i+2][j+2] != null &&
                                board[i+2][j+2].getSeed() == primaryCard &&
                                !usedByObjectiveCard.contains(board[i+1][j+1]) &&
                                !usedByObjectiveCard.contains(board[i+2][j+2])
                                ){
                                totalpoints += points;
                                usedByObjectiveCard.add(currentCard);
                                usedByObjectiveCard.add(board[i+1][j+1]);
                                usedByObjectiveCard.add(board[i+2][j+2]);
                            }
                        }
                        case "up" -> {
                            if( i-2 >= 0 && j+2 < board[i].length &&
                                board[i-1][j+1] != null &&
                                board[i-1][j+1].getSeed() == primaryCard &&
                                board[i-2][j+2] != null &&
                                board[i-2][j+2].getSeed() == primaryCard &&
                                !usedByObjectiveCard.contains(board[i-1][j+1]) &&
                                !usedByObjectiveCard.contains(board[i-2][j+2])
                            ){
                                totalpoints += points;
                                usedByObjectiveCard.add(currentCard);
                                usedByObjectiveCard.add(board[i-1][j+1]);
                                usedByObjectiveCard.add(board[i-2][j+2]);
                            }
                        }
                        case "ne" -> {
                            if( i+3 <= board.length && j-1 > 0 &&
                                board[i+1][j-1] != null &&
                                board[i+1][j-1].getSeed() == secondaryCard &&
                                board[i+3][j-1]!=null &&
                                board[i+3][j-1].getSeed() == secondaryCard &&
                                !usedByObjectiveCard.contains(board[i+1][j-1]) &&
                                !usedByObjectiveCard.contains(board[i+3][j-1])
                            ){
                                totalpoints += points;
                                usedByObjectiveCard.add(currentCard);
                                usedByObjectiveCard.add(board[i+1][j-1]);
                                usedByObjectiveCard.add(board[i+3][j-1]);
                            }

                        }
                        case "nw" -> {
                            if( i+3 <= board.length && j+1 <= board[i].length &&
                                board[i+1][j+1] != null &&
                                board[i+1][j+1].getSeed() == secondaryCard &&
                                board[i+3][j+1]!=null &&
                                board[i+3][j+1].getSeed() == secondaryCard &&
                                !usedByObjectiveCard.contains(board[i+1][j+1]) &&
                                !usedByObjectiveCard.contains(board[i+3][j+1])
                            ){
                                totalpoints += points;
                                usedByObjectiveCard.add(currentCard);
                                usedByObjectiveCard.add(board[i+1][j+1]);
                                usedByObjectiveCard.add(board[i+3][j+1]);
                            }
                        }
                        case "se" -> {
                            if( i-3 >= 0 && j-1 >= 0 &&
                                board[i-1][j-1] != null &&
                                board[i-1][j-1].getSeed() == secondaryCard &&
                                board[i-3][j-1]!=null &&
                                board[i-3][j-1].getSeed() == secondaryCard &&
                                !usedByObjectiveCard.contains(board[i-1][j-1]) &&
                                !usedByObjectiveCard.contains(board[i-3][j-1])
                            ){
                                totalpoints += points;
                                usedByObjectiveCard.add(currentCard);
                                usedByObjectiveCard.add(board[i-1][j-1]);
                                usedByObjectiveCard.add(board[i-3][j-1]);
                            }
                        }
                        case "sw" -> {
                            if(i-3 >= 0 && j+1 < board[i].length &&
                               board[i-1][j+1] != null &&
                               board[i-1][j+1].getSeed() == secondaryCard &&
                               board[i-3][j+1]!=null &&
                               board[i-3][j+1].getSeed() == secondaryCard &&
                               !usedByObjectiveCard.contains(board[i-1][j+1]) &&
                               !usedByObjectiveCard.contains(board[i-3][j+1])
                            ){
                                totalpoints += points;
                                usedByObjectiveCard.add(currentCard);
                                usedByObjectiveCard.add(board[i-1][j+1]);
                                usedByObjectiveCard.add(board[i-3][j+1]);
                            }
                        }

                    }

                }
            }
        }
        return totalpoints;
    }
    */