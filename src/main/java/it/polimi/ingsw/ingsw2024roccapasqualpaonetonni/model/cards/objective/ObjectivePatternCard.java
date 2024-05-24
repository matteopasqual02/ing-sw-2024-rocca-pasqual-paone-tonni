package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.objective;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.PlayerBoard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Seed;
import org.fusesource.jansi.Ansi;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * The type Objective pattern card.
 */
public class ObjectivePatternCard extends ObjectiveCard {
    /**
     * The Pattern.
     */
    private final Seed[][] pattern;

    /**
     * Instantiates a new Objective pattern card.
     *
     * @param id      the id
     * @param points  the points
     * @param pattern the pattern
     */
    public ObjectivePatternCard(int id, int points, Seed[][] pattern) {
        super(id, points);
        this.pattern = pattern;
        objectivePointsStrategy = new ObjectivePointsPattern();
    }

    /**
     * Point card int.
     *
     * @param playerBoard the player board
     * @return the int
     */
    @Override
    public int pointCard(PlayerBoard playerBoard){
        return points * objectivePointsStrategy.count(playerBoard,null,pattern);
    }

    /**
     * Get pattern seed [ ] [ ].
     *
     * @return the seed [ ] [ ]
     */
    public Seed[][] getPattern() {
        return pattern;
    }


    /**
     * To string string.
     *
     * @return the string
     */
    public String toString () {
        StringBuilder sb = new StringBuilder();
        Ansi.Color background = Ansi.Color.WHITE;

        int[] countTypes = new int[4];
        for (Seed[] seeds : pattern) {
            for (Seed seed : seeds) {
                if (seed != null) {
                    countTypes[seed.getId()]++;
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
        for (Seed[] seeds : pattern) {
            int count = 0;
            for (Seed seed : seeds) {
                if (seed != null) {
                    count++;
                }
            }
            if (count != 0) {
                sb.append(ansi().fg(background).bg(background).a("  "));
                for (Seed seed : seeds) {
                    if (seed == null) {
                        sb.append(ansi().fg(Ansi.Color.DEFAULT).bg(background).a(" "));
                    } else {
                        sb.append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(" "));
                        sb.append(ansi().fg(seed.getByAnsi()).bg(Ansi.Color.DEFAULT).a(seed.name().substring(0, 1)));
                        sb.append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(" "));
                    }
                }
                sb.append(ansi().fg(background).bg(background).a("  "));
                if (line == 0) {
                    sb.append(ansi().fg(background).bg(background).a("       "));
                } else if (line == 1) {
                    sb.append(ansi().fg(background).bg(background).a("  "));
                    sb.append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
                    sb.append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(points));
                    sb.append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
                    sb.append(ansi().fg(background).bg(background).a("  "));
                } else if (line == 2) {
                    sb.append(ansi().fg(background).bg(background).a("       "));
                }
                sb.append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a("\n"));
                line++;
            }
        }

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

        int[] countTypes = new int[4];
        for (Seed[] value : pattern) {
            for (Seed seed : value) {
                if (seed != null) {
                    countTypes[seed.getId()]++;
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
        for (Seed[] seeds : pattern) {
            int count = 0;
            for (Seed seed : seeds) {
                if (seed != null) {
                    count++;
                }
            }
            if (count != 0) {
                sb[currentLine].append(ansi().fg(background).bg(background).a("  "));
                for (Seed seed : seeds) {
                    if (seed == null) {
                        sb[currentLine].append(ansi().fg(Ansi.Color.DEFAULT).bg(background).a(" "));
                    } else {
                        sb[currentLine].append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(" "));
                        sb[currentLine].append(ansi().fg(seed.getByAnsi()).bg(Ansi.Color.DEFAULT).a(seed.name().substring(0, 1)));
                        sb[currentLine].append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(" "));
                    }
                }
                sb[currentLine].append(ansi().fg(background).bg(background).a("  "));
                if (currentLine == 0) {
                    sb[0].append(ansi().fg(background).bg(background).a("       "));
                } else if (currentLine == 1) {
                    sb[1].append(ansi().fg(background).bg(background).a("  "));
                    sb[1].append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
                    sb[1].append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(points));
                    sb[1].append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
                    sb[1].append(ansi().fg(background).bg(background).a("  "));
                } else {
                    sb[2].append(ansi().fg(background).bg(background).a("       "));
                }
                sb[currentLine].append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(""));
                currentLine++;
            }
        }

        return sb[line].toString();
    }

}
