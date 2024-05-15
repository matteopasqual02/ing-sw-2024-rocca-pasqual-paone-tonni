package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Seed;
import org.fusesource.jansi.Ansi;

import java.util.Arrays;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * The type Gold card.
 */
public class GoldCard extends PlayingCard {
    /**
     * The Point condition.
     */
    private final String pointCondition;
    /**
     * The Place condition.
     */
    private final int[] placeCondition;

    /**
     * Instantiates a new Gold card.
     *
     * @param id     the id
     * @param seed   the seed
     * @param c      the c
     * @param points the points
     * @param cond   the cond
     * @param p      the p
     */
    public GoldCard(int id, Seed seed, Corner[] c, int points, String cond, int[] p){
        super(id,seed,c,points);
        pointCondition=cond;
        this.placeCondition= Arrays.copyOf(p,4);
    }

    public int[] checkRequirements(int[] available_seeds) {
        int[] result = new int[2];
        for (int i = 0; i < placeCondition.length; i++) {
            if (placeCondition[i] > available_seeds[i]) {
                result[1] = i;
                return result;
            }
        }
        result[0] = 1;
        return result;
    }

    public int calculatePoints(PlayingCard[][] board, int[] seedCount, int x, int y) {
        int curr_points = 0;
        PlayingCard cardOnBoard;
        switch(pointCondition) {
            case "corners":
                //count the number of corner covered by (4 corner) the current card
                int[][] positions = {{-1, -1, 1}, {-1, 1, 2}, {1, 1, 3}, {1, -1, 4}};
                for (int[] i : positions) {
                    cardOnBoard = board[x + i[0]][y + i[1]];
                    if (cardOnBoard != null) {
                        curr_points += points;
                    }
                }
                return curr_points;
            case "feather", "potion", "scroll":
                return points * seedCount[Seed.getByName(pointCondition).getId()];
            case null, default:
                return points;
        }
    }

    /**
     * Get place condition int [ ].
     *
     * @return the int [ ]
     */
    public int[] getPlaceCondition(){return placeCondition;}

    /**
     * Gets point condition.
     *
     * @return the point condition
     */
    public String getPointCondition() { return pointCondition; }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        boolean flipped = isFlipped;
        Ansi.Color background;
        background = cardSeed.getByAnsi();

        return getString(sb, flipped, background);
    }
    public String toString(Boolean flipped) {
        StringBuilder sb = new StringBuilder();
        Ansi.Color background = cardSeed.getByAnsi();

        return getString(sb, flipped, background);
    }

    /**
     * Gets string.
     *
     * @param sb         the sb
     * @param flipped    the flipped
     * @param background the background
     * @return the string
     */
    private String getString(StringBuilder sb, boolean flipped, Ansi.Color background) {
        if (!flipped) {
            //First Line
            if (corners[0] == null || corners[0].getSeed() == null) {
                sb.append(ansi().fg(background).bg(background).a("   "));
            } else {
                sb.append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
                sb.append(ansi().fg(corners[0].getSeed().getByAnsi()).bg(Ansi.Color.DEFAULT).a(corners[0].getSeed().name().substring(0, 1)));
                sb.append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
            }
            if (pointCondition == null) {
                sb.append(ansi().fg(Ansi.Color.BLACK).bg(background).a("    ").a(points).a("    "));
            } else {
                sb.append(ansi().fg(Ansi.Color.BLACK).bg(background).a("   ").a(points).a(" ").a(pointCondition.substring(0,1).toUpperCase()).a("   "));
            }
            if (corners[1] == null || corners[1].getSeed() == null) {
                sb.append(ansi().fg(background).bg(background).a("   "));
            } else {
                sb.append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
                sb.append(ansi().fg(corners[1].getSeed().getByAnsi()).bg(Ansi.Color.DEFAULT).a(corners[1].getSeed().name().substring(0, 1)));
                sb.append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
            }
            sb.append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a("\n"));

            //Second Line
            sb.append(ansi().fg(background).bg(background).a("               "));
            sb.append(ansi().bg(Ansi.Color.DEFAULT).a("\n"));

            //Third Line
            if (corners[3] == null || corners[3].getSeed() == null) {
                sb.append(ansi().fg(background).bg(background).a("     "));
            } else {
                sb.append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
                sb.append(ansi().fg(corners[3].getSeed().getByAnsi()).bg(Ansi.Color.DEFAULT).a(corners[3].getSeed().name().substring(0, 1)));
                sb.append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
                sb.append(ansi().fg(background).bg(background).a("  "));
            }

            //PlaceCondition centered
            int l = 0;
            for(int i=0;i<4;i++){
                l=l+placeCondition[i];
            }
            switch (l){
                case 3 ->{
                    sb.append(ansi().fg(background).bg(background).a(" "));
                    int i,j,k=0;
                    for(i=0; i<placeCondition.length; i++) {
                        for (j=0; j<placeCondition[i]; j++) {
                            sb.append(ansi().fg(Ansi.Color.BLACK).bg(background).a(Seed.getById(i).name().substring(0,1)));
                            k++;
                        }
                    }
                    for(j=k; k<6; k++) {
                        sb.append(ansi().fg(background).bg(background).a(" "));
                    }
                }
                case 4 ->{
                    int i,j,k=0;
                    int count=0;
                    for(i=0; i<placeCondition.length; i++) {
                        for (j=0; j<placeCondition[i]; j++) {
                            sb.append(ansi().fg(Ansi.Color.BLACK).bg(background).a(Seed.getById(i).name().substring(0,1)));
                            k++;
                            if(count==1) {
                                sb.append(ansi().bg(background).a(" "));
                            }
                            count++;
                        }
                    }
                    for(j=k; k<6; k++) {
                        sb.append(ansi().fg(background).bg(background).a(" "));
                    }
                }
                case 5 ->{
                    int i,j,k=0;

                    for(i=0; i<placeCondition.length; i++) {
                        for (j=0; j<placeCondition[i]; j++) {
                            sb.append(ansi().fg(Ansi.Color.BLACK).bg(background).a(Seed.getById(i).name().substring(0,1)));
                            k++;
                        }
                    }
                    for(j=k; k<6; k++) {
                        sb.append(ansi().fg(background).bg(background).a(" "));
                    }
                    sb.append(ansi().fg(background).bg(background).a(" "));
                }
            }

            if (corners[2] == null || corners[2].getSeed() == null) {
                sb.append(ansi().fg(background).bg(background).a("   "));
            } else {
                sb.append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
                sb.append(ansi().fg(corners[2].getSeed().getByAnsi()).bg(Ansi.Color.DEFAULT).a(corners[2].getSeed().name().substring(0, 1)));
                sb.append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
            }

            sb.append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a("\n"));

        } else {
            // Back
            //First Line
            sb.append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(" E "));
            sb.append(ansi().fg(Ansi.Color.DEFAULT).bg(background).a("         "));
            sb.append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(" E ").a("\n"));
            //Second Line
            sb.append(ansi().fg(background).bg(background).a("      "));
            sb.append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
            sb.append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(cardSeed.name().substring(0,1)));
            sb.append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
            sb.append(ansi().fg(background).bg(background).a("      "));
            sb.append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a("\n"));

            //Third Line
            sb.append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(" E "));
            sb.append(ansi().fg(Ansi.Color.DEFAULT).bg(background).a("         "));
            sb.append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(" E \n"));}

        return sb.toString();
    }

    public String toString(Boolean flipped,int line) {
        StringBuilder[] sb = new StringBuilder[3];
        for (int i = 0; i < sb.length; i++) {
            sb[i] = new StringBuilder();
        }
        Ansi.Color background = cardSeed.getByAnsi();

        if (!flipped) {
            //First Line
            if (corners[0] == null || corners[0].getSeed() == null) {
                sb[0].append(ansi().fg(background).bg(background).a("   "));
            } else {
                sb[0].append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
                sb[0].append(ansi().fg(corners[0].getSeed().getByAnsi()).bg(Ansi.Color.DEFAULT).a(corners[0].getSeed().name().substring(0, 1)));
                sb[0].append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
            }
            if (pointCondition == null) {
                sb[0].append(ansi().fg(Ansi.Color.BLACK).bg(background).a("    ").a(points).a("    "));
            } else {
                sb[0].append(ansi().fg(Ansi.Color.BLACK).bg(background).a("   ").a(points).a(" ").a(pointCondition.substring(0,1).toUpperCase()).a("   "));
            }
            if (corners[1] == null || corners[1].getSeed() == null) {
                sb[0].append(ansi().fg(background).bg(background).a("   "));
            } else {
                sb[0].append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
                sb[0].append(ansi().fg(corners[1].getSeed().getByAnsi()).bg(Ansi.Color.DEFAULT).a(corners[1].getSeed().name().substring(0, 1)));
                sb[0].append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
            }
            sb[0].append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(""));

            //Second Line
            sb[1].append(ansi().fg(background).bg(background).a("               "));
            sb[1].append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(""));

            //Third Line
            if (corners[3] == null || corners[3].getSeed() == null) {
                sb[2].append(ansi().fg(background).bg(background).a("     "));
            } else {
                sb[2].append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
                sb[2].append(ansi().fg(corners[3].getSeed().getByAnsi()).bg(Ansi.Color.DEFAULT).a(corners[3].getSeed().name().substring(0, 1)));
                sb[2].append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
                sb[2].append(ansi().fg(background).bg(background).a("  "));
            }

            //PlaceCondition centered
            int l = 0;
            for(int i=0;i<4;i++){
                l=l+placeCondition[i];
            }
            switch (l){
                case 3 ->{
                    sb[2].append(ansi().fg(background).bg(background).a(" "));
                    int i,j,k=0;
                    for(i=0; i<placeCondition.length; i++) {
                        for (j=0; j<placeCondition[i]; j++) {
                            sb[2].append(ansi().fg(Ansi.Color.BLACK).bg(background).a(Seed.getById(i).name().substring(0,1)));
                            k++;
                        }
                    }
                    for(j=k; k<6; k++) {
                        sb[2].append(ansi().fg(background).bg(background).a(" "));
                    }
                }
                case 4 ->{
                    int i,j,k=0;
                    int count=0;
                    for(i=0; i<placeCondition.length; i++) {
                        for (j=0; j<placeCondition[i]; j++) {
                            sb[2].append(ansi().fg(Ansi.Color.BLACK).bg(background).a(Seed.getById(i).name().substring(0,1)));
                            k++;
                            if(count==1) {
                                sb[2].append(ansi().bg(background).a(" "));
                            }
                            count++;
                        }
                    }
                    for(j=k; k<6; k++) {
                        sb[2].append(ansi().fg(background).bg(background).a(" "));
                    }
                }
                case 5 ->{
                    int i,j,k=0;

                    for(i=0; i<placeCondition.length; i++) {
                        for (j=0; j<placeCondition[i]; j++) {
                            sb[2].append(ansi().fg(Ansi.Color.BLACK).bg(background).a(Seed.getById(i).name().substring(0,1)));
                            k++;
                        }
                    }
                    for(j=k; k<6; k++) {
                        sb[2].append(ansi().fg(background).bg(background).a(" "));
                    }
                    sb[2].append(ansi().fg(background).bg(background).a(" "));
                }
            }

            if (corners[2] == null || corners[2].getSeed() == null) {
                sb[2].append(ansi().fg(background).bg(background).a("   "));
            } else {
                sb[2].append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
                sb[2].append(ansi().fg(corners[2].getSeed().getByAnsi()).bg(Ansi.Color.DEFAULT).a(corners[2].getSeed().name().substring(0, 1)));
                sb[2].append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
            }

            sb[2].append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(""));

        } else {
            // Back
            //First Line
            sb[0].append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(" E "));
            sb[0].append(ansi().fg(Ansi.Color.DEFAULT).bg(background).a("         "));
            sb[0].append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(" E ").a(""));
            //Second Line
            sb[1].append(ansi().fg(background).bg(background).a("      "));
            sb[1].append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
            sb[1].append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(cardSeed.name().substring(0,1)));
            sb[1].append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
            sb[1].append(ansi().fg(background).bg(background).a("      "));
            sb[1].append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(""));

            //Third Line
            sb[2].append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(" E "));
            sb[2].append(ansi().fg(Ansi.Color.DEFAULT).bg(background).a("         "));
            sb[2].append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(" E "));
        }

        String[] strings = new String[3];
        for(int i=0;i<3;i++){
            strings[i] = sb[i].toString();
        }

        return strings[line];
    }

}
