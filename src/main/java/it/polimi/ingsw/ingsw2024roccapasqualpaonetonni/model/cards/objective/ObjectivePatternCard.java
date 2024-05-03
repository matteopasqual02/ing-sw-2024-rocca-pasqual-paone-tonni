package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.objective;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.PlayerBoard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Seed;
import org.fusesource.jansi.Ansi;

import static org.fusesource.jansi.Ansi.ansi;

public class ObjectivePatternCard extends ObjectiveCard {
    private final Seed[][] pattern;

    public ObjectivePatternCard(int id, int points, Seed[][] pattern) {
        super(id, points);
        this.pattern = pattern;
        objectivePointsStrategy = new ObjectivePointsPattern();
    }
    @Override
    public int pointCard(PlayerBoard playerBoard){
        return points * objectivePointsStrategy.count(playerBoard,null,pattern);
    }

    public Seed[][] getPattern() {
        return pattern;
    }


    public String toString () {
        StringBuilder sb = new StringBuilder();
        Ansi.Color background = Ansi.Color.WHITE;

        int line =0;
        for(int i =0;i< pattern.length;i++){
            int count =0;
            for(int j=0;j< pattern[i].length;j++){
                if(pattern[i][j]!=null){count++;}
            }
            if(count!=0){
                sb.append(ansi().cursor(0,0).fg(background).bg(background).a("  "));
                for(int j=0;j< pattern[i].length;j++){
                    if(pattern[i][j]==null){
                        sb.append(ansi().cursor(0,0).fg(Ansi.Color.DEFAULT).bg(background).a(" "));
                    }else {
                        sb.append(ansi().cursor(0,0).fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(" "));
                        sb.append(ansi().cursor(0,0).fg(pattern[i][j].getByAnsi()).bg(Ansi.Color.DEFAULT).a(pattern[i][j].name().substring(0,1)));
                        sb.append(ansi().cursor(0,0).fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(" "));
                    }
                }
                sb.append(ansi().cursor(0,0).fg(background).bg(background).a("  "));
                if(line == 0){
                    sb.append(ansi().cursor(0,0).fg(background).bg(background).a("       "));
                }
                else if(line == 1){
                    sb.append(ansi().cursor(0,0).fg(background).bg(background).a("  "));
                    sb.append(ansi().cursor(0,0).fg(background).bg(Ansi.Color.DEFAULT).a(" "));
                    sb.append(ansi().cursor(0,0).fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(points));
                    sb.append(ansi().cursor(0,0).fg(background).bg(Ansi.Color.DEFAULT).a(" "));
                    sb.append(ansi().cursor(0,0).fg(background).bg(background).a("  "));
                }
                else if(line == 2){
                    sb.append(ansi().cursor(0,0).fg(background).bg(background).a("       "));
                }
                if(count!=0){
                    sb.append(ansi().cursor(0,0).fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a("\n"));
                }

                line++;
            }

        }

        return sb.toString();
    }
}
