package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

public class ResourceCard extends PlayingCard{
    public ResourceCard(int id, Seed seed, Corner[] c,int points){
        super(id,seed,c,points);
    }

    public int calculatePoints(PlayingCard[][] board, int[] seedCount, int x, int y) {
        return points;
    }
    public boolean checkRequirements(int[] seedCount) {return true;}
}

// Resurce Card is actually a PlayingCard Without beeing an abstract class
