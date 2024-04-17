package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

import org.fusesource.jansi.Ansi;

import java.util.Arrays;

import static org.fusesource.jansi.Ansi.ansi;

public class StartingCard extends PlayingCard{
    private final Boolean[] center;
    private final Corner[] cornersBack;

    public StartingCard(int id, Boolean[] c, Corner[] cf, Corner[] cb){
        super(id,Seed.EMPTY,cf,0);
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
            sb.append(ansi().cursor(0,0).fg(background).bg(background).a("_____________"));
            sb.append(ansi().cursor(0,0).bg(Ansi.Color.DEFAULT).a("\n"));

            //Second Line
            sb.append(ansi().cursor(0,0).fg(background).bg(background).a("|"));
            sb.append(ansi().cursor(0,0).fg(background).bg(Ansi.Color.DEFAULT).a(" "));
            if (corners[0] == null || corners[0].getSeed() == null) {
                sb.append(ansi().cursor(0, 0).fg(Ansi.Color.DEFAULT).bg(background).a(" "));
            } else {
                sb.append(ansi().cursor(0, 0).fg(corners[0].getSeed().getByAnsi()).bg(Ansi.Color.DEFAULT).a(corners[0].getSeed().name().substring(0, 1)));
            }

            sb.append(ansi().cursor(0,0).fg(background).bg(Ansi.Color.DEFAULT).a(" "));

            if (points == 0) {
                sb.append(ansi().cursor(1, 0).fg(Ansi.Color.DEFAULT).bg(background).a("     "));
            } else {
                sb.append(ansi().cursor(1, 0).fg(Ansi.Color.DEFAULT).bg(background).a("  ").a(points).a("  "));
            }

            sb.append(ansi().cursor(0,0).fg(background).bg(Ansi.Color.DEFAULT).a(" "));
            if (corners[1] == null || corners[1].getSeed() == null) {
                sb.append(ansi().cursor(4, 0).fg(Ansi.Color.DEFAULT).bg(background).a(" "));
            } else {
                sb.append(ansi().cursor(4, 0).fg(corners[1].getSeed().getByAnsi()).bg(Ansi.Color.DEFAULT).a(corners[1].getSeed().name().substring(0, 1)));
            }
            sb.append(ansi().cursor(0,0).fg(background).bg(Ansi.Color.DEFAULT).a(" "));
            sb.append(ansi().cursor(0,0).fg(background).bg(background).a("|"));
            sb.append(ansi().cursor(0,0).bg(Ansi.Color.DEFAULT).a("\n"));

            //Third Line
            sb.append(ansi().cursor(0,0).fg(background).bg(background).a("_____"));
            sb.append(ansi().cursor(0,0).fg(background).bg(Ansi.Color.DEFAULT).a(" "));
            sb.append(ansi().cursor(0,0).fg(background).bg(Ansi.Color.DEFAULT).a(getSeed().name().substring(0,1)));
            sb.append(ansi().cursor(0,0).fg(background).bg(Ansi.Color.DEFAULT).a(" "));
            sb.append(ansi().cursor(0,0).fg(background).bg(background).a("_____"));
            sb.append(ansi().cursor(0,0).bg(Ansi.Color.DEFAULT).a("\n"));

            //Fourth Line
            sb.append(ansi().cursor(0,0).fg(background).bg(background).a("|"));
            sb.append(ansi().cursor(0,0).fg(background).bg(Ansi.Color.DEFAULT).a(" "));
            if (corners[3] == null || corners[3].getSeed() == null) {
                sb.append(ansi().cursor(0, 0).fg(Ansi.Color.DEFAULT).bg(background).a(" "));
            } else {
                sb.append(ansi().cursor(0, 0).fg(corners[3].getSeed().getByAnsi()).bg(Ansi.Color.DEFAULT).a(corners[3].getSeed().name().substring(0, 1)));
            }
            sb.append(ansi().cursor(0,0).fg(background).bg(Ansi.Color.DEFAULT).a(" "));
            sb.append(ansi().cursor(1, 0).fg(Ansi.Color.DEFAULT).bg(background).a("     "));
            sb.append(ansi().cursor(0,0).fg(background).bg(Ansi.Color.DEFAULT).a(" "));
            if (corners[2] == null || corners[2].getSeed() == null) {
                sb.append(ansi().cursor(0, 0).fg(Ansi.Color.DEFAULT).bg(background).a(" "));
            } else {
                sb.append(ansi().cursor(0, 0).fg(corners[2].getSeed().getByAnsi()).bg(Ansi.Color.DEFAULT).a(corners[2].getSeed().name().substring(0, 1)));
            }
            sb.append(ansi().cursor(0,0).fg(background).bg(Ansi.Color.DEFAULT).a(" "));
            sb.append(ansi().cursor(0,0).fg(background).bg(background).a("|"));
            sb.append(ansi().cursor(0,0).bg(Ansi.Color.DEFAULT).a("\n"));

            //Fifth Line
            sb.append(ansi().cursor(0,0).fg(background).bg(background).a("_____________"));
            sb.append(ansi().cursor(0,0).bg(Ansi.Color.DEFAULT).a("\n"));


        } else {
            // Retro della carta
            //First Line
            sb.append(ansi().cursor(0, 0).fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(corners[5].getSeed().name().substring(0,1)));
            sb.append(ansi().cursor(0, 0).fg(Ansi.Color.DEFAULT).bg(background).a("       "));
            sb.append(ansi().cursor(0, 0).fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(" E ").a("\n"));
            //Second Line
            sb.append(ansi().cursor(0, 0).fg(background).bg(background).a("_____"));
            sb.append(ansi().cursor(0, 0).fg(background).bg(Ansi.Color.DEFAULT).a(" "));
            sb.append(ansi().cursor(0, 0).fg(background).bg(Ansi.Color.DEFAULT).a(cardSeed.name().substring(0,1)));
            sb.append(ansi().cursor(0, 0).fg(background).bg(Ansi.Color.DEFAULT).a(" "));
            sb.append(ansi().cursor(0, 0).fg(background).bg(background).a("_____"));
            sb.append(ansi().cursor(0, 0).fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a("\n"));

            //Third Line
            sb.append(ansi().cursor(0, 0).fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(" E "));
            sb.append(ansi().cursor(0, 0).fg(Ansi.Color.DEFAULT).bg(background).a("       "));
            sb.append(ansi().cursor(0, 0).fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(" E "));
        }

        return sb.toString();
    }

}
