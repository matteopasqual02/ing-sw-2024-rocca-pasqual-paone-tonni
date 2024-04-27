package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.objective;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.PlayerBoard;
import org.fusesource.jansi.Ansi;

import static org.fusesource.jansi.Ansi.ansi;

public class ObjectiveCountCard extends ObjectiveCard {
    private final int[] countTypes;

    public ObjectiveCountCard(int id, int points, int[] countTypes) {
        super(id, points);
        this.countTypes = countTypes;
        objectivePointsStrategy = new ObjectivePointsCount();
    }

    @Override
    public int pointCard(PlayerBoard playerBoard){
        return points * objectivePointsStrategy.count(playerBoard,countTypes,null);
    }

    public int[] getCountTypes() {
        return countTypes;
    }

    public String toString () {
        StringBuilder sb = new StringBuilder();
        Ansi.Color background = Ansi.Color.WHITE;

        //First Line
        sb.append(ansi().cursor(0,0).fg(background).bg(background).a("      "));
        sb.append(ansi().cursor(0,0).fg(background).bg(Ansi.Color.DEFAULT).a(" "));
        sb.append(ansi().cursor(0,0).fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(points));
        sb.append(ansi().cursor(0,0).fg(background).bg(Ansi.Color.DEFAULT).a(" "));
        sb.append(ansi().cursor(0,0).fg(background).bg(background).a("      "));
        sb.append(ansi().cursor(0,0).fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a("\n"));

        //Second Line
        sb.append(ansi().cursor(0,0).fg(background).bg(background).a("               "));
        sb.append(ansi().cursor(0,0).bg(Ansi.Color.DEFAULT).a("\n"));

        //Third Line
        sb.append(ansi().cursor(0,0).fg(background).bg(background).a("      "));

        for(int i=0; i<4; i++) {
            sb.append(ansi().cursor(0,0).fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(countTypes.length).a(" "));
        }

        sb.append(ansi().cursor(0,0).fg(background).bg(background).a("      "));
        sb.append(ansi().cursor(0,0).fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a("\n"));


        return sb.toString();
    }
}
