package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Seed;

import java.util.Arrays;

/**
 * The type Playing card.
 */
public abstract class PlayingCard extends Card {
    /**
     * The Card seed.
     */
    protected final Seed cardSeed;
    /**
     * The Points.
     */
    protected final int points;
    /**
     * The Is flipped.
     */
    protected boolean isFlipped;
    /**
     * The Corners.
     */
    protected Corner[] corners;
    /**
     * The Coordinates.
     */
    protected int[] coordinates;

    /**
     * Instantiates a new Playing card.
     *
     * @param id     the id
     * @param seed   the seed
     * @param c      the c
     * @param points the points
     */
    public PlayingCard(int id, Seed seed, Corner[] c,int points) {
        super(id);
        this.cardSeed = seed;
        this.corners = Arrays.copyOf(c,4);
        this.isFlipped = false;
        this.points = points;
        this.coordinates = new int[] {-1,-1};
    }

    /**
     * Flip.
     */
    public void flip() {
        isFlipped= !isFlipped;
    }

    /**
     * Is flipped boolean.
     *
     * @return the boolean
     */
    public Boolean isFlipped(){
        return isFlipped;
    }

    /**
     * Gets corner.
     *
     * @param pos the pos
     * @return the corner
     */
// since the convention we are using is that the corners start from one, the attribute need to be decremented
    public Corner getCorner(int pos) {
        return corners[pos - 1];
    }

    /**
     * Get seed seed.
     *
     * @return the seed
     */
    public Seed getSeed(){
        return cardSeed;
    }

    /**
     * Get coordinates int [ ].
     *
     * @return the int [ ]
     */
    public int[] getCoordinates() {
        return coordinates;
    }

    /**
     * Sets coordinates.
     *
     * @param x the x
     * @param y the y
     */
    public void setCoordinates(int x, int y) {
        coordinates[0] = x;
        coordinates[1] = y;
    }

    /**
     * Check requirements int [ ].
     *
     * @param seedCount the seed count
     * @return the int [ ]
     */
    public int[] checkRequirements(int[] seedCount) {
        return new int[] {1,0};
    }

    /**
     * Calculate points int.
     *
     * @param board     the board
     * @param seedCount the seed count
     * @param x         the x
     * @param y         the y
     * @return the int
     */
    public int calculatePoints(PlayingCard[][] board, int[] seedCount, int x, int y){return 0;}

    /**
     * Gets points.
     *
     * @return the points
     */
    public int getPoints() {
        return points;
    }

    /**
     * To string string.
     *
     * @param flip the flip
     * @param line the line
     * @return the string
     */
    public String toString(Boolean flip, int line) {
        return null;
    }

    /**
     * To string string.
     *
     * @param flip the flip
     * @return the string
     */
    public String toString(Boolean flip) {
        return null;
    }
}
