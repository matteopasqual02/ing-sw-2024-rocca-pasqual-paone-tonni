package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards;


//import static org.fusesource.jansi.Ansi.ansi;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Seed;
import org.fusesource.jansi.Ansi;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * The type Resource card.
 */
public class ResourceCard extends PlayingCard {
    /**
     * Instantiates a new Resource card.
     *
     * @param id     the id
     * @param seed   the seed
     * @param c      the c
     * @param points the points
     */
    public ResourceCard(int id, Seed seed, Corner[] c, int points){
        super(id,seed,c,points);
    }

    public int calculatePoints(PlayingCard[][] board, int[] seedCount, int x, int y) {
        return points;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        boolean flipped = isFlipped;
        Ansi.Color background;
        background = cardSeed.getByAnsi();

        //card
        return getString(sb, flipped, background);
    }
    public String toString(Boolean flipped) {
        StringBuilder sb = new StringBuilder();
        Ansi.Color background = cardSeed.getByAnsi();

        //front
        return getString(sb, flipped, background);
    }
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
            if (points == 0) {
                sb.append(ansi().fg(Ansi.Color.DEFAULT).bg(background).a("         "));
            } else {
                sb.append(ansi().fg(Ansi.Color.DEFAULT).bg(background).a("    ").a(points).a("    "));
            }
            if (corners[1] == null || corners[1].getSeed() == null) {
                sb.append(ansi().fg(background).bg(background).a("   "));
            } else {
                sb.append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
                sb.append(ansi().fg(corners[1].getSeed().getByAnsi()).bg(Ansi.Color.DEFAULT).a(corners[1].getSeed().name().substring(0, 1)));
                sb.append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
            }
            sb.append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a("\n"));

            //Second Line
            sb.append(ansi().fg(background).bg(background).a("               "));
            sb.append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a("\n"));

            //Third Line
            if (corners[3] == null || corners[3].getSeed() == null) {
                sb.append(ansi().fg(background).bg(background).a("   "));
            } else {
                sb.append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
                sb.append(ansi().fg(corners[3].getSeed().getByAnsi()).bg(Ansi.Color.DEFAULT).a(corners[3].getSeed().name().substring(0, 1)));
                sb.append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
            }
            sb.append(ansi().fg(Ansi.Color.DEFAULT).bg(background).a("         "));
            if (corners[2] == null || corners[2].getSeed() == null) {
                sb.append(ansi().cursor(0,0).fg(background).bg(background).a("   "));
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
            sb.append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(" E \n"));
        }
        return sb.toString();
    }

    public String toString(Boolean flipped, int line) {
        StringBuilder[] sb = new StringBuilder[3];
        for (int i = 0; i < sb.length; i++) {
            sb[i] = new StringBuilder();
        }
        Ansi.Color background = cardSeed.getByAnsi();

        //front
        if (!flipped) {
            //First Line
            if (corners[0] == null || corners[0].getSeed() == null) {
                sb[0].append(ansi().fg(background).bg(background).a("   "));
            } else {
                sb[0].append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
                sb[0].append(ansi().fg(corners[0].getSeed().getByAnsi()).bg(Ansi.Color.DEFAULT).a(corners[0].getSeed().name().substring(0, 1)));
                sb[0].append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
            }
            if (points == 0) {
                sb[0].append(ansi().fg(Ansi.Color.DEFAULT).bg(background).a("         "));
            } else {
                sb[0].append(ansi().fg(Ansi.Color.DEFAULT).bg(background).a("    ").a(points).a("    "));
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
                sb[2].append(ansi().fg(background).bg(background).a("   "));
            } else {
                sb[2].append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
                sb[2].append(ansi().fg(corners[3].getSeed().getByAnsi()).bg(Ansi.Color.DEFAULT).a(corners[3].getSeed().name().substring(0, 1)));
                sb[2].append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
            }
            sb[2].append(ansi().fg(Ansi.Color.DEFAULT).bg(background).a("         "));
            if (corners[2] == null || corners[2].getSeed() == null) {
                sb[2].append(ansi().cursor(0,0).fg(background).bg(background).a("   "));
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
