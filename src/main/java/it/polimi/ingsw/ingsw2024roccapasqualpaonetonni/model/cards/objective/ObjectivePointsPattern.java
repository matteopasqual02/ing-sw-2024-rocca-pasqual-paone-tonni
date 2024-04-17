package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.objective;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.PlayerBoard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Seed;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.PlayingCard;

import java.util.ArrayList;
import java.util.List;

public class ObjectivePointsPattern implements ObjectivePointsStrategy{
    @Override
    public int count(PlayerBoard playerBoard, int[] countTypes, Seed[][] pattern){
        PlayingCard[][] board = playerBoard.getBoard();
        int totalPoints=0;
        List<PlayingCard> usedByObjectiveCard =  new ArrayList<>();

        for(int i=0; i< board.length - pattern.length +1; i++){
            for (int j=0; j< board[i].length - pattern[0].length + 1; j++){
                if(match(board,i,j,pattern,usedByObjectiveCard)){
                    totalPoints ++;
                    add(board, i, j, pattern, usedByObjectiveCard);
                }
            }
        }

        return totalPoints;
    }

    private void add(PlayingCard[][] board,int i,int j, Seed[][] pattern,List<PlayingCard> used){
        for(int k=0; k< pattern.length;k++){
            for(int w=0; w < pattern[k].length; w++){
                if(pattern[k][w]!=null &&
                   board[i+k][j+w]!=null &&
                   pattern[k][w].equals(board[i+k][j+w].getSeed())
                ){
                    used.add(board[i+k][j+w]);
                }
            }
        }
    }
    private Boolean match(PlayingCard[][] board,int i,int j, Seed[][] pattern,List<PlayingCard> used){
        for(int k=0; k< pattern.length;k++){
            for(int w=0; w < pattern[k].length; w++){
                if(pattern[k][w]!=null){
                    if( board[i+k][j+w]==null ){
                        return false;
                    }
                    int kk;
                    if( !pattern[k][w].equals(board[i+k][j+w].getSeed()) ){
                        return false;
                    }

                    if( used.contains(board[i+k][j+w]) ){
                        return false;
                    }

                }
            }
        }

        return true;
    }
}
