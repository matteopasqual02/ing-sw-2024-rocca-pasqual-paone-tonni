package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ObjectiveCard extends Card{
    private final int points;
    private final  boolean isCount; //se è una carta obiettivo di tipo conteggio isCount=true, se è di tipo pattern isCount=false
    private final Seed type;
    private final String shape;
    private final Seed primaryCard;
    private final Seed secondaryCard;

    public ObjectiveCard(int id, int points, boolean isCount, Seed type, Seed[] psCards, String shape){
        super(id);
        this.points = points;
        this.isCount = isCount;
        this.type = type;
        this.primaryCard = psCards[0];
        this.secondaryCard = psCards[1];
        this.shape = shape;
    }

    public int pointCard(PlayerBoard pb){
        if(isCount){
            //card that count the number of seed or potion,....,mixed
            return pointsCountCard(pb);
        }
        else {
            //card that have a pattern
            return pointsPatternCard(pb);
        }
    }
    private int pointsCountCard(PlayerBoard pb){
        switch (type){
            case RED,BLUE,PURPLE,GREEN ->{
                return points * (pb.getPlayer().getCountSeed()[type.getId()] / 3);
            }
            case POTION,SCROLL,FEATHER ->{
                return points * (pb.getPlayer().getCountSeed()[type.getId()] / 2);
            }
            case MIXED -> {
                int[] seed ={pb.getPlayer().getCountSeed()[Seed.SCROLL.getId()],
                            pb.getPlayer().getCountSeed()[Seed.FEATHER.getId()],
                            pb.getPlayer().getCountSeed()[Seed.POTION.getId()]};
                return points * Arrays.stream(seed).min().orElse(0);
            }

        }
        return 0;
    }
    private int pointsPatternCard(PlayerBoard pb){
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

/* not used -- if needed for testing use it*/
    public int getPoints(){
        return points;
    }
    public boolean getIsCount(){
        return isCount;
    }
    public Seed getType(){
        return type;
    }
    public String getShape(){
        return shape;
    }
    public Seed getPrimaryCard(){
        return primaryCard;
    }
    public Seed getSecondaryCard(){
        return secondaryCard;
    }

}
