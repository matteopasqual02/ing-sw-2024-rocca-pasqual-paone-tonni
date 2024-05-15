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

        int[] countTypes = new int[4];
        for(int i=0; i<pattern.length; i++) {
            for(int j=0; j<pattern[i].length; j++) {
                if(pattern[i][j]!=null) {
                    countTypes[pattern[i][j].getId()]++;
                }
            }
        }
        int max=0,index=0;
        for(int j=0; j<countTypes.length; j++) {
            if(countTypes[j]>max){
                max = countTypes[j];
                index = j;
            }
        }
        background = Seed.getById(index).getByAnsi();

        int line =0;
        for(int i =0;i< pattern.length;i++){
            int count =0;
            for(int j=0;j< pattern[i].length;j++){
                if(pattern[i][j]!=null){count++;}
            }
            if(count!=0){
                sb.append(ansi().fg(background).bg(background).a("  "));
                for(int j=0;j< pattern[i].length;j++){
                    if(pattern[i][j]==null){
                        sb.append(ansi().fg(Ansi.Color.DEFAULT).bg(background).a(" "));
                    }else {
                        sb.append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(" "));
                        sb.append(ansi().fg(pattern[i][j].getByAnsi()).bg(Ansi.Color.DEFAULT).a(pattern[i][j].name().substring(0,1)));
                        sb.append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(" "));
                    }
                }
                sb.append(ansi().fg(background).bg(background).a("  "));
                if(line == 0){
                    sb.append(ansi().fg(background).bg(background).a("       "));
                }
                else if(line == 1){
                    sb.append(ansi().fg(background).bg(background).a("  "));
                    sb.append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
                    sb.append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(points));
                    sb.append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
                    sb.append(ansi().fg(background).bg(background).a("  "));
                }
                else if(line == 2){
                    sb.append(ansi().fg(background).bg(background).a("       "));
                }
                sb.append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a("\n"));
                line++;
            }
        }

        return sb.toString();
    }

    public String toString (int line) {
        StringBuilder[] sb = new StringBuilder[3];
        Ansi.Color background = Ansi.Color.WHITE;
        for (int i = 0; i < sb.length; i++) {
            sb[i] = new StringBuilder();
        }

        int[] countTypes = new int[4];
        for(int i=0; i<pattern.length; i++) {
            for(int j=0; j<pattern[i].length; j++) {
                if(pattern[i][j]!=null) {
                    countTypes[pattern[i][j].getId()]++;
                }
            }
        }
        int max=0,index=0;
        for(int j=0; j<countTypes.length; j++) {
            if(countTypes[j]>max){
                max = countTypes[j];
                index = j;
            }
        }
        background = Seed.getById(index).getByAnsi();

        int currentLine =0;
        for(int i =0;i< pattern.length;i++){
            int count =0;
            for(int j=0;j< pattern[i].length;j++){
                if(pattern[i][j]!=null){count++;}
            }
            if(count!=0){
                sb[currentLine].append(ansi().fg(background).bg(background).a("  "));
                for(int j=0;j< pattern[i].length;j++){
                    if(pattern[i][j]==null){
                        sb[currentLine].append(ansi().fg(Ansi.Color.DEFAULT).bg(background).a(" "));
                    }else {
                        sb[currentLine].append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(" "));
                        sb[currentLine].append(ansi().fg(pattern[i][j].getByAnsi()).bg(Ansi.Color.DEFAULT).a(pattern[i][j].name().substring(0,1)));
                        sb[currentLine].append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(" "));
                    }
                }
                sb[currentLine].append(ansi().fg(background).bg(background).a("  "));
                if(currentLine == 0){
                    sb[0].append(ansi().fg(background).bg(background).a("       "));
                }
                else if(currentLine == 1){
                    sb[1].append(ansi().fg(background).bg(background).a("  "));
                    sb[1].append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
                    sb[1].append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(points));
                    sb[1].append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
                    sb[1].append(ansi().fg(background).bg(background).a("  "));
                }
                else {
                    sb[2].append(ansi().fg(background).bg(background).a("       "));
                }
                sb[currentLine].append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(""));
                currentLine++;
            }
        }

        return sb[line].toString();
    }

}
