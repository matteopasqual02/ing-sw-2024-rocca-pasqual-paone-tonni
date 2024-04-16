package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

import java.util.Arrays;

public abstract class PlayingCard extends Card {
    protected final Seed cardSeed;
    protected final int points;
    protected boolean isFlipped;
    protected Corner[] corners;
    protected int[] coordinates;

    public PlayingCard(int id, Seed seed, Corner[] c,int points) {
        super(id);
        this.cardSeed = seed;
        this.corners = Arrays.copyOf(c,4);
        this.isFlipped = false;
        this.points = points;
        this.coordinates = new int[] {-1,-1};
    }

    public void flip() {
        isFlipped= !isFlipped;
    }

    public Boolean isFlipped(){
        return isFlipped;
    }

    // since the convention we are using is that the corners start from one, the attribute need to be decremented
    public Corner getCorner(int pos) {
        return corners[pos - 1];
    }

    public Seed getSeed(){
        return cardSeed;
    }

    public int[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(int x, int y) {
        coordinates[0] = x;
        coordinates[1] = y;
    }

    public int[] checkRequirements(int[] seedCount) {
        return new int[] {1,0};
    }

    public int calculatePoints(PlayingCard[][] board, int[] seedCount, int x, int y){return 0;}

    public int getPoints() {
        return points;
    }

}
