package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.objective;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.PlayerBoard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Seed;
import org.fusesource.jansi.Ansi;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * The type Objective count card.
 */
public class ObjectiveCountCard extends ObjectiveCard {
    /**
     * The Count types.
     */
    private final int[] countTypes;

    /**
     * Instantiates a new Objective count card.
     *
     * @param id         the id
     * @param points     the points
     * @param countTypes the count types
     */
    public ObjectiveCountCard(int id, int points, int[] countTypes) {
        super(id, points);
        this.countTypes = countTypes;
        objectivePointsStrategy = new ObjectivePointsCount();
    }

    /**
     * Point card int.
     *
     * @param playerBoard the player board
     * @return the int
     */
    @Override
    public int pointCard(PlayerBoard playerBoard){
        return points * objectivePointsStrategy.count(playerBoard,countTypes,null);
    }

    /**
     * Get count types int [ ].
     *
     * @return the int [ ]
     */
    public int[] getCountTypes() {
        return countTypes;
    }

    /**
     * To string string.
     *
     * @return the string
     */
    public String toString () {
        StringBuilder sb = new StringBuilder();
        Ansi.Color background = Ansi.Color.WHITE;

        int j;
        for(j=0; j<7; j++) {
            if(countTypes[j]!=0) {
                background = Seed.getById(j).getByAnsi();
            }
        }

        //First Line
        sb.append(ansi().fg(background).bg(background).a("      "));
        sb.append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
        sb.append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(points));
        sb.append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
        sb.append(ansi().fg(background).bg(background).a("      "));
        sb.append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a("\n"));

        //Second Line
        sb.append(ansi().fg(background).bg(background).a("               "));
        sb.append(ansi().bg(Ansi.Color.DEFAULT).a("\n"));

        //Third Line
        sb.append(ansi().fg(background).bg(background).a("   "));
        int k=0;
        int k1=0;
        for(int i=0; i<7; i++) {
            if(countTypes[i]!=0 && countTypes[i]!=1) {
                if(k1==0) {
                    sb.append(ansi().fg(background).bg(background).a("   "));
                    k1++;
                }
                sb.append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(countTypes[i]).a(" "));
                sb.append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(Seed.getById(i).name().substring(0,1)));
            }
            else if(countTypes[i]==1) {
                sb.append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(countTypes[i]));
                sb.append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(Seed.getById(i).name().substring(0,1)));
                if(k<2) {
                    sb.append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(" "));
                    k++;
                }
            }
        }

        if (k!=0) {
            sb.append(ansi().fg(background).bg(background).a("    "));
        }
        else {
            sb.append(ansi().fg(background).bg(background).a("      "));
        }
        sb.append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a("\n"));

        return sb.toString();
    }

    /**
     * To string string.
     *
     * @param line the line
     * @return the string
     */
    public String toString (int line) {
        StringBuilder[] sb = new StringBuilder[3];
        Ansi.Color background = Ansi.Color.WHITE;
        for (int i = 0; i < sb.length; i++) {
            sb[i] = new StringBuilder();
        }

        int j;
        for(j=0; j<7; j++) {
            if(countTypes[j]!=0) {
                background = Seed.getById(j).getByAnsi();
            }
        }

        //First Line
        sb[0].append(ansi().fg(background).bg(background).a("      "));
        sb[0].append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
        sb[0].append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(points));
        sb[0].append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
        sb[0].append(ansi().fg(background).bg(background).a("      "));
        sb[0].append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(""));

        //Second Line
        sb[1].append(ansi().fg(background).bg(background).a("               "));
        sb[1].append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(""));

        //Third Line
        sb[2].append(ansi().fg(background).bg(background).a("   "));
        int k=0;
        int k1=0;
        for(int i=0; i<7; i++) {
            if(countTypes[i]!=0 && countTypes[i]!=1) {
                if(k1==0) {
                    sb[2].append(ansi().fg(background).bg(background).a("   "));
                    k1++;
                }
                sb[2].append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(countTypes[i]).a(" "));
                sb[2].append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(Seed.getById(i).name().substring(0,1)));
            }
            else if(countTypes[i]==1) {
                sb[2].append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(countTypes[i]));
                sb[2].append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(Seed.getById(i).name().substring(0,1)));
                if(k<2) {
                    sb[2].append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(" "));
                    k++;
                }
            }
        }

        if (k!=0) {
            sb[2].append(ansi().fg(background).bg(background).a("    "));
        }
        else {
            sb[2].append(ansi().fg(background).bg(background).a("      "));
        }
        sb[2].append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(""));

        sb[line].append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(""));
        return sb[line].toString();
    }
}
