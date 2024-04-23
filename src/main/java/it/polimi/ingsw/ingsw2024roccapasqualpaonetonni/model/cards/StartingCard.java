package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Corner;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Seed;
import org.fusesource.jansi.Ansi;

import java.util.Arrays;

import static org.fusesource.jansi.Ansi.ansi;

public class StartingCard extends PlayingCard {
    private final Boolean[] center;
    private final Corner[] cornersBack;

    public StartingCard(int id, Boolean[] c, Corner[] cf, Corner[] cb){
        super(id, Seed.EMPTY,cf,0);
        this.center= Arrays.copyOf(c,4);
        this.cornersBack= Arrays.copyOf(cb,4);
        this.isFlipped=false;

    }
    public Boolean[] getCenter() { return center; }
    public Corner getCorner(int pos){
        if (isFlipped) {
            return cornersBack[pos - 1];
        }
        else {
            return corners[pos - 1];
        }
    }


    public String toString(boolean flipped) {
        StringBuilder sb = new StringBuilder();
        Ansi.Color background = Ansi.Color.WHITE;

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


            sb.append(ansi().cursor(0,0).fg(background).bg(background).a("         "));



            if (corners[1] == null || corners[1].getSeed() == null) {
                sb.append(ansi().cursor(0,0).fg(background).bg(background).a(" "));
                sb.append(ansi().cursor(0, 0).fg(Ansi.Color.DEFAULT).bg(background).a(" "));
                sb.append(ansi().cursor(0,0).fg(background).bg(background).a(" "));
            } else {
                sb.append(ansi().cursor(0,0).fg(background).bg(Ansi.Color.DEFAULT).a(" "));
                sb.append(ansi().cursor(4, 0).fg(corners[1].getSeed().getByAnsi()).bg(Ansi.Color.DEFAULT).a(corners[1].getSeed().name().substring(0, 1)));
                sb.append(ansi().cursor(0,0).fg(background).bg(Ansi.Color.DEFAULT).a(" "));
            }
            sb.append(ansi().cursor(0,0).fg(background).bg(Ansi.Color.DEFAULT).a("\n"));


            //Second Line -- Card Seeds
            sb.append(ansi().cursor(0,0).fg(background).bg(background).a("______"));


            int l=0, i=0;
            for(i=0; i<4; i++) {
                if(center[i]){
                    l++;
                }
            }

            switch (l) {
                case 1 -> {
                    for(int j=0; j<4; j++){
                        if(center[j]){
                            sb.append(ansi().cursor(0,0).fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(" "));
                            sb.append(ansi().cursor(0, 0).fg(Seed.getById(j).getByAnsi()).bg(Ansi.Color.DEFAULT).a(Seed.getById(j).name().charAt(0)));
                            sb.append(ansi().cursor(0,0).fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(" "));
                        }
                    }
                }

                case 2 -> {
                    int count=0;
                    for(int j=0; j<4; j++){
                        if(center[j]){

                            sb.append(ansi().cursor(0, 0).fg(Seed.getById(j).getByAnsi()).bg(Ansi.Color.DEFAULT).a(Seed.getById(j).name().charAt(0)));
                            count++;
                            if (count==1) {
                                sb.append(ansi().cursor(0,0).fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(" "));
                            }
                        }
                    }
                }

                case 3 -> {
                    for(int j=0; j<4; j++){
                        if(center[j]){
                            sb.append(ansi().cursor(0, 0).fg(Seed.getById(j).getByAnsi()).bg(Ansi.Color.DEFAULT).a(Seed.getById(j).name().charAt(0)));
                        }
                    }
                }
            }





            sb.append(ansi().cursor(0,0).fg(background).bg(background).a("______"));
            sb.append(ansi().cursor(0,0).bg(Ansi.Color.DEFAULT).a("\n"));

            //Third Line

            if (corners[3] == null || corners[3].getSeed() == null) {
                sb.append(ansi().cursor(0,0).fg(background).bg(background).a(" "));
                sb.append(ansi().cursor(0, 0).fg(Ansi.Color.DEFAULT).bg(background).a(" "));
                sb.append(ansi().cursor(0,0).fg(background).bg(background).a(" "));
            } else {
                sb.append(ansi().cursor(0,0).fg(background).bg(Ansi.Color.DEFAULT).a(" "));
                sb.append(ansi().cursor(0, 0).fg(corners[3].getSeed().getByAnsi()).bg(Ansi.Color.DEFAULT).a(corners[3].getSeed().name().substring(0, 1)));
                sb.append(ansi().cursor(0,0).fg(background).bg(Ansi.Color.DEFAULT).a(" "));
            }


            sb.append(ansi().cursor(0,0).fg(background).bg(background).a("         "));



            if (corners[2] == null || corners[2].getSeed() == null) {
                sb.append(ansi().cursor(0,0).fg(background).bg(background).a(" "));
                sb.append(ansi().cursor(0, 0).fg(Ansi.Color.DEFAULT).bg(background).a(" "));
                sb.append(ansi().cursor(0,0).fg(background).bg(background).a(" "));
            } else {
                sb.append(ansi().cursor(0,0).fg(background).bg(Ansi.Color.DEFAULT).a(" "));
                sb.append(ansi().cursor(0, 0).fg(corners[2].getSeed().getByAnsi()).bg(Ansi.Color.DEFAULT).a(corners[2].getSeed().name().substring(0, 1)));
                sb.append(ansi().cursor(0,0).fg(background).bg(Ansi.Color.DEFAULT).a(" "));
            }

            sb.append(ansi().cursor(0,0).bg(Ansi.Color.DEFAULT).a("\n"));



        } else {
            // Retro della carta
            //First Line
            if (cornersBack[0] == null || cornersBack[0].getSeed() == null) {
                sb.append(ansi().cursor(0,0).fg(background).bg(background).a(" "));
                sb.append(ansi().cursor(0, 0).fg(Ansi.Color.DEFAULT).bg(background).a(" "));
                sb.append(ansi().cursor(0,0).fg(background).bg(background).a(" "));
            } else {
                sb.append(ansi().cursor(0,0).fg(background).bg(Ansi.Color.DEFAULT).a(" "));
                sb.append(ansi().cursor(0, 0).fg(cornersBack[0].getSeed().getByAnsi()).bg(Ansi.Color.DEFAULT).a(cornersBack[0].getSeed().name().substring(0, 1)));
                sb.append(ansi().cursor(0,0).fg(background).bg(Ansi.Color.DEFAULT).a(" "));
            }

            sb.append(ansi().cursor(0,0).fg(Ansi.Color.DEFAULT).bg(background).a("         "));

            if (cornersBack[1] == null || cornersBack[1].getSeed() == null) {
                sb.append(ansi().cursor(0,0).fg(background).bg(background).a(" "));
                sb.append(ansi().cursor(0, 0).fg(Ansi.Color.DEFAULT).bg(background).a(" "));
                sb.append(ansi().cursor(0,0).fg(background).bg(background).a(" "));
            } else {
                sb.append(ansi().cursor(0,0).fg(background).bg(Ansi.Color.DEFAULT).a(" "));
                sb.append(ansi().cursor(0, 0).fg(cornersBack[1].getSeed().getByAnsi()).bg(Ansi.Color.DEFAULT).a(cornersBack[1].getSeed().name().substring(0, 1)));
                sb.append(ansi().cursor(0,0).fg(background).bg(Ansi.Color.DEFAULT).a(" "));
            }

            sb.append(ansi().cursor(0,0).fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a("\n"));

            //Second Line
            sb.append(ansi().cursor(0, 0).fg(background).bg(background).a("_______________"));
            sb.append(ansi().cursor(0, 0).fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a("\n"));

            //Third Line
            if (cornersBack[3] == null || cornersBack[3].getSeed() == null) {
                sb.append(ansi().cursor(0,0).fg(background).bg(background).a(" "));
                sb.append(ansi().cursor(0, 0).fg(Ansi.Color.DEFAULT).bg(background).a(" "));
                sb.append(ansi().cursor(0,0).fg(background).bg(background).a(" "));
            } else {
                sb.append(ansi().cursor(0,0).fg(background).bg(Ansi.Color.DEFAULT).a(" "));
                sb.append(ansi().cursor(0, 0).fg(cornersBack[3].getSeed().getByAnsi()).bg(Ansi.Color.DEFAULT).a(cornersBack[3].getSeed().name().substring(0, 1)));
                sb.append(ansi().cursor(0,0).fg(background).bg(Ansi.Color.DEFAULT).a(" "));
            }

            sb.append(ansi().cursor(0,0).fg(Ansi.Color.DEFAULT).bg(background).a("         "));

            if (cornersBack[2] == null || cornersBack[2].getSeed() == null) {
                sb.append(ansi().cursor(0,0).fg(background).bg(background).a(" "));
                sb.append(ansi().cursor(0, 0).fg(Ansi.Color.DEFAULT).bg(background).a(" "));
                sb.append(ansi().cursor(0,0).fg(background).bg(background).a(" "));
            } else {
                sb.append(ansi().cursor(0,0).fg(background).bg(Ansi.Color.DEFAULT).a(" "));
                sb.append(ansi().cursor(0, 0).fg(cornersBack[2].getSeed().getByAnsi()).bg(Ansi.Color.DEFAULT).a(cornersBack[2].getSeed().name().substring(0, 1)));
                sb.append(ansi().cursor(0,0).fg(background).bg(Ansi.Color.DEFAULT).a(" "));
            }

            sb.append(ansi().cursor(0,0).fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a("\n"));
        }

        return sb.toString();
    }
}
