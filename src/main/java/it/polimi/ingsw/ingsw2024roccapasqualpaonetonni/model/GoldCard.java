package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

import org.fusesource.jansi.Ansi;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;

import static org.fusesource.jansi.Ansi.ansi;

public class GoldCard extends PlayingCard{
    private final String pointCondition;
    private final int[] placeCondition;
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
                int[][] positons = {{-1, -1, 1}, {-1, 1, 2}, {1, 1, 3}, {1, -1, 4}};
                for (int[] i : positons) {
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

    public int[] getPlaceCondition(){return placeCondition;}
    public String getPointCondition() { return pointCondition; }


    public String toString(boolean flipped) {
        StringBuilder sb = new StringBuilder();
        Ansi.Color background = cardSeed.getByAnsi();

        if (!flipped) {


            //First Line

            if (corners[0] == null || corners[0].getSeed() == null) {
                sb.append(ansi().cursor(0,0).fg(background).bg(background).a(" "));
                sb.append(ansi().cursor(0, 0).fg(Ansi.Color.DEFAULT).bg(background).a(" "));
                sb.append(ansi().cursor(0,0).fg(background).bg(background).a(" "));
            } else {
                sb.append(ansi().cursor(0,0).fg(background).bg(Ansi.Color.DEFAULT).a(" "));
                sb.append(ansi().cursor(0, 0).fg(corners[0].getSeed().getByAnsi()).bg(Ansi.Color.DEFAULT).a(corners[0].getSeed().name().substring(0, 1)));
                sb.append(ansi().cursor(0,0).fg(background).bg(Ansi.Color.DEFAULT).a(" "));
            }


            if (pointCondition == null) {
                sb.append(ansi().cursor(1, 0).fg(Ansi.Color.BLACK).bg(background).a("    ").a(points).a("    "));
            } else {
                sb.append(ansi().cursor(1, 0).fg(Ansi.Color.BLACK).bg(background).a("   ").a(points).a(" ").a(pointCondition.substring(0,1).toUpperCase()).a("   "));
            }

            sb.append(ansi().cursor(0,0).fg(background).bg(Ansi.Color.DEFAULT).a(" "));
            if (corners[1] == null || corners[1].getSeed() == null) {
                sb.append(ansi().cursor(4, 0).fg(Ansi.Color.DEFAULT).bg(background).a(" "));
            } else {
                sb.append(ansi().cursor(4, 0).fg(corners[1].getSeed().getByAnsi()).bg(Ansi.Color.DEFAULT).a(corners[1].getSeed().name().substring(0, 1)));
            }
            sb.append(ansi().cursor(0,0).fg(background).bg(Ansi.Color.DEFAULT).a(" "));
            sb.append(ansi().cursor(0,0).bg(Ansi.Color.DEFAULT).a("\n"));

            //Second Line
            sb.append(ansi().cursor(0,0).fg(background).bg(background).a("_______________"));
            sb.append(ansi().cursor(0,0).bg(Ansi.Color.DEFAULT).a("\n"));

            //Third Line
            sb.append(ansi().cursor(0,0).fg(background).bg(Ansi.Color.DEFAULT).a(" "));
            if (corners[3] == null || corners[3].getSeed() == null) {
                sb.append(ansi().cursor(0, 0).fg(Ansi.Color.DEFAULT).bg(background).a(" "));
            } else {
                sb.append(ansi().cursor(0, 0).fg(corners[3].getSeed().getByAnsi()).bg(Ansi.Color.DEFAULT).a(corners[3].getSeed().name().substring(0, 1)));
            }

            sb.append(ansi().cursor(0,0).fg(background).bg(Ansi.Color.DEFAULT).a(" "));
            sb.append(ansi().cursor(0,0).fg(background).bg(background).a("  "));

            //PlaceCondition centered
            int l = 0;
            for(int i=0;i<4;i++){
                l=l+placeCondition[i];
            }


            switch (l){
                case 3 ->{
                    sb.append(ansi().cursor(1,0).fg(background).bg(background).a(" "));

                    int i,j,k=0;
                    for(i=0; i<placeCondition.length; i++) {
                        for (j=0; j<placeCondition[i]; j++) {
                            sb.append(ansi().cursor(0, 0).fg(Ansi.Color.BLACK).bg(background).a(Seed.getById(i).name().substring(0,1)));
                            k++;
                        }

                    }

                    for(j=k; k<6; k++) {
                        sb.append(ansi().cursor(0,0).fg(background).bg(background).a(" "));
                    }

                }
                case 4 ->{
                    int i,j,k=0;
                    int count=0;
                    for(i=0; i<placeCondition.length; i++) {
                        for (j=0; j<placeCondition[i]; j++) {
                            sb.append(ansi().cursor(0, 0).fg(Ansi.Color.BLACK).bg(background).a(Seed.getById(i).name().substring(0,1)));
                            k++;
                            if(count==1) {
                                sb.append(ansi().cursor(0,0).bg(background).a(" "));
                            }
                            count++;
                        }
                    }

                    for(j=k; k<6; k++) {
                        sb.append(ansi().cursor(0,0).fg(background).bg(background).a(" "));
                    }

                }
                case 5 ->{
                    int i,j,k=0;

                    for(i=0; i<placeCondition.length; i++) {
                        for (j=0; j<placeCondition[i]; j++) {
                            sb.append(ansi().cursor(0, 0).fg(Ansi.Color.BLACK).bg(background).a(Seed.getById(i).name().substring(0,1)));
                            k++;
                        }

                    }

                    for(j=k; k<6; k++) {
                        sb.append(ansi().cursor(0,0).fg(background).bg(background).a(" "));
                    }

                    sb.append(ansi().cursor(0,0).fg(background).bg(background).a(" "));
                }
            }




            sb.append(ansi().cursor(0,0).fg(background).bg(Ansi.Color.DEFAULT).a(" "));

            if (corners[2] == null || corners[2].getSeed() == null) {
                sb.append(ansi().cursor(0, 0).fg(Ansi.Color.DEFAULT).bg(background).a(" "));
            } else {
                sb.append(ansi().cursor(0, 0).fg(corners[2].getSeed().getByAnsi()).bg(Ansi.Color.DEFAULT).a(corners[2].getSeed().name().substring(0, 1)));
            }
            sb.append(ansi().cursor(0,0).fg(background).bg(Ansi.Color.DEFAULT).a(" "));
            sb.append(ansi().cursor(0,0).bg(Ansi.Color.DEFAULT).a("\n"));



        } else {
            // Retro della carta
            //First Line
            sb.append(ansi().cursor(0, 0).fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(" E "));
            sb.append(ansi().cursor(0, 0).fg(Ansi.Color.DEFAULT).bg(background).a("         "));
            sb.append(ansi().cursor(0, 0).fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(" E ").a("\n"));
            //Second Line
            sb.append(ansi().cursor(0, 0).fg(background).bg(background).a("______"));
            sb.append(ansi().cursor(0, 0).fg(background).bg(Ansi.Color.DEFAULT).a(" "));
            sb.append(ansi().cursor(0, 0).fg(background).bg(Ansi.Color.DEFAULT).a(cardSeed.name().substring(0,1)));
            sb.append(ansi().cursor(0, 0).fg(background).bg(Ansi.Color.DEFAULT).a(" "));
            sb.append(ansi().cursor(0, 0).fg(background).bg(background).a("______"));
            sb.append(ansi().cursor(0, 0).fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a("\n"));

            //Third Line
            sb.append(ansi().cursor(0, 0).fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(" E "));
            sb.append(ansi().cursor(0, 0).fg(Ansi.Color.DEFAULT).bg(background).a("         "));
            sb.append(ansi().cursor(0, 0).fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(" E "));
            sb.append(ansi().cursor(0,0).bg(Ansi.Color.DEFAULT).a("\n"));
        }

        return sb.toString();
    }

}


