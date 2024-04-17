package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

import javafx.scene.control.ColorPicker;
import org.fusesource.jansi.Ansi;

import java.nio.charset.StandardCharsets;

import static org.fusesource.jansi.Ansi.ansi;

public class ResourceCard extends PlayingCard {
    public ResourceCard(int id, Seed seed, Corner[] c, int points) {
        super(id, seed, c, points);
    }

    public int calculatePoints(PlayingCard[][] board, int[] seedCount, int x, int y) {
        return points;
    }

    public String toString(boolean flipped) {
        StringBuilder sb = new StringBuilder();
        Ansi.Color background = cardSeed.getByAnsi();

        if (!flipped) {
            sb.append(ansi().cursor(0,0).fg(Ansi.Color.DEFAULT).a("_______\n"));
            sb.append(ansi().cursor(0,0).fg(Ansi.Color.DEFAULT).a("|"));
            if (corners[0] == null || corners[0].getSeed() == null) {
                sb.append(ansi().cursor(0, 0).fg(Ansi.Color.DEFAULT).bg(background).a(" "));
            } else {
                sb.append(ansi().cursor(0, 0).fg(corners[0].getSeed().getByAnsi()).bg(Ansi.Color.DEFAULT).a(corners[0].getSeed().name().substring(0, 1)));
            }

            if (points == 0) {
                sb.append(ansi().cursor(1, 0).fg(Ansi.Color.DEFAULT).bg(background).a("   "));
            } else {
                sb.append(ansi().cursor(1, 0).fg(Ansi.Color.DEFAULT).bg(background).a(" ").a(points).a(" "));
            }

            if (corners[1] == null || corners[1].getSeed() == null) {
                sb.append(ansi().cursor(4, 0).fg(Ansi.Color.DEFAULT).bg(background).a(" "));
            } else {
                sb.append(ansi().cursor(4, 0).fg(corners[1].getSeed().getByAnsi()).bg(Ansi.Color.DEFAULT).a(corners[1].getSeed().name().substring(0, 1)));
            }
            sb.append(ansi().cursor(0,0).fg(Ansi.Color.DEFAULT).a("|\n"));

            sb.append(ansi().cursor(0,0).fg(Ansi.Color.DEFAULT).a("|"));
            if (corners[3] == null || corners[3].getSeed() == null) {
                sb.append(ansi().cursor(0, 0).fg(Ansi.Color.DEFAULT).bg(background).a(" "));
            } else {
                sb.append(ansi().cursor(0, 0).fg(corners[3].getSeed().getByAnsi()).bg(Ansi.Color.DEFAULT).a(corners[3].getSeed().name().substring(0, 1)));
            }

            sb.append(ansi().cursor(1, 0).fg(Ansi.Color.DEFAULT).bg(background).a("   "));

            if (corners[2] == null || corners[2].getSeed() == null) {
                sb.append(ansi().cursor(0, 0).fg(Ansi.Color.DEFAULT).bg(background).a(" "));
            } else {
                sb.append(ansi().cursor(0, 0).fg(corners[2].getSeed().getByAnsi()).bg(Ansi.Color.DEFAULT).a(corners[2].getSeed().name().substring(0, 1)));
            }
            sb.append(ansi().cursor(0,0).fg(Ansi.Color.DEFAULT).a("|\n"));
            sb.append(ansi().cursor(0,0).fg(Ansi.Color.DEFAULT).a("-------"));


        } else {
            // Retro della carta
            sb.append(ansi().cursor(0, 0).fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a("E"));
            sb.append(ansi().cursor(0, 0).fg(Ansi.Color.DEFAULT).bg(background).a("   "));
            sb.append(ansi().cursor(0, 0).fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a("E").a("\n"));
            sb.append(ansi().cursor(0, 0).fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a("E"));
            sb.append(ansi().cursor(0, 0).fg(Ansi.Color.DEFAULT).bg(background).a("   "));
            sb.append(ansi().cursor(0, 0).fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a("E"));
        }

        return sb.toString();
    }


}



// Resource Card is actually a PlayingCard Without being an abstract class


