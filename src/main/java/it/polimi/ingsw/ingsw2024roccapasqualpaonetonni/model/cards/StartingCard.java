package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Seed;
import org.fusesource.jansi.Ansi;

import java.util.Arrays;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * The type Starting card.
 */
public class StartingCard extends PlayingCard {
    /**
     * The Center.
     */
    private final Boolean[] center;
    /**
     * The Corners back.
     */
    private final Corner[] cornersBack;

    /**
     * Instantiates a new Starting card.
     *
     * @param id the id
     * @param c  the c
     * @param cf the cf
     * @param cb the cb
     */
    public StartingCard(int id, Boolean[] c, Corner[] cf, Corner[] cb){
        super(id, Seed.EMPTY,cf,0);
        this.center= Arrays.copyOf(c,4);
        this.cornersBack= Arrays.copyOf(cb,4);
        this.isFlipped=false;

    }

    /**
     * Get center boolean [ ].
     *
     * @return the boolean [ ]
     */
    public Boolean[] getCenter() {
        return center;
    }

    /**
     * Get corner.
     *
     * @param pos the pos
     * @return the corner
     */
    @Override
    public Corner getCorner(int pos){
        if (isFlipped) {
            return cornersBack[pos - 1];
        }
        else {
            return corners[pos - 1];
        }
    }


    /**
     * To string string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        boolean flipped = isFlipped;
        StringBuilder sb = new StringBuilder();
        Ansi.Color background = Ansi.Color.WHITE;

        if (!flipped) {
            //First Line
            if (corners[0] == null || corners[0].getSeed() == null) {
                sb.append(ansi().fg(background).bg(background).a("   "));
            } else {
                sb.append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
                sb.append(ansi().fg(corners[0].getSeed().getByAnsi()).bg(Ansi.Color.DEFAULT).a(corners[0].getSeed().name().substring(0, 1)));
                sb.append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
            }
            sb.append(ansi().fg(background).bg(background).a("         "));
            if (corners[1] == null || corners[1].getSeed() == null) {
                sb.append(ansi().fg(background).bg(background).a("   "));
            } else {
                sb.append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
                sb.append(ansi().fg(corners[1].getSeed().getByAnsi()).bg(Ansi.Color.DEFAULT).a(corners[1].getSeed().name().substring(0, 1)));
                sb.append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
            }
            sb.append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a("\n"));

            //Second Line -- Card Seeds
            sb.append(ansi().fg(background).bg(background).a("      "));

            int l=0, i;
            for(i=0; i<4; i++) {
                if(center[i]){
                    l++;
                }
            }
            switch (l) {
                case 1 -> {
                    for(int j=0; j<4; j++){
                        if(center[j]){
                            sb.append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(" "));
                            sb.append(ansi().fg(Seed.getById(j).getByAnsi()).bg(Ansi.Color.DEFAULT).a(Seed.getById(j).name().charAt(0)));
                            sb.append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(" "));
                        }
                    }
                }
                case 2 -> {
                    int count=0;
                    for(int j=0; j<4; j++){
                        if(center[j]){
                            sb.append(ansi().fg(Seed.getById(j).getByAnsi()).bg(Ansi.Color.DEFAULT).a(Seed.getById(j).name().charAt(0)));
                            count++;
                            if (count==1) {
                                sb.append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(" "));
                            }
                        }
                    }
                }

                case 3 -> {
                    for(int j=0; j<4; j++){
                        if(center[j]){
                            sb.append(ansi().fg(Seed.getById(j).getByAnsi()).bg(Ansi.Color.DEFAULT).a(Seed.getById(j).name().charAt(0)));
                        }
                    }
                }
            }

            sb.append(ansi().fg(background).bg(background).a("      "));
            sb.append(ansi().bg(Ansi.Color.DEFAULT).a("\n"));

            //Third Line
            if (corners[3] == null || corners[3].getSeed() == null) {
                sb.append(ansi().fg(background).bg(background).a("   "));
            } else {
                sb.append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
                sb.append(ansi().fg(corners[3].getSeed().getByAnsi()).bg(Ansi.Color.DEFAULT).a(corners[3].getSeed().name().substring(0, 1)));
                sb.append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
            }
            sb.append(ansi().fg(background).bg(background).a("         "));
            if (corners[2] == null || corners[2].getSeed() == null) {
                sb.append(ansi().fg(background).bg(background).a("   "));
            } else {
                sb.append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
                sb.append(ansi().fg(corners[2].getSeed().getByAnsi()).bg(Ansi.Color.DEFAULT).a(corners[2].getSeed().name().substring(0, 1)));
                sb.append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
            }
            sb.append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a("\n"));

        } else {
            // Retro della carta
            //First Line
            if (cornersBack[0] == null || cornersBack[0].getSeed() == null) {
                sb.append(ansi().fg(background).bg(background).a("   "));
            } else {
                sb.append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
                sb.append(ansi().fg(cornersBack[0].getSeed().getByAnsi()).bg(Ansi.Color.DEFAULT).a(cornersBack[0].getSeed().name().substring(0, 1)));
                sb.append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
            }
            sb.append(ansi().cursor(0,0).fg(Ansi.Color.DEFAULT).bg(background).a("         "));
            if (cornersBack[1] == null || cornersBack[1].getSeed() == null) {
                sb.append(ansi().fg(background).bg(background).a("   "));
            } else {
                sb.append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
                sb.append(ansi().fg(cornersBack[1].getSeed().getByAnsi()).bg(Ansi.Color.DEFAULT).a(cornersBack[1].getSeed().name().substring(0, 1)));
                sb.append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
            }
            sb.append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a("\n"));

            //Second Line
            sb.append(ansi().fg(background).bg(background).a("               "));
            sb.append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a("\n"));

            //Third Line
            if (cornersBack[3] == null || cornersBack[3].getSeed() == null) {
                sb.append(ansi().fg(background).bg(background).a("   "));
            } else {
                sb.append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
                sb.append(ansi().fg(cornersBack[3].getSeed().getByAnsi()).bg(Ansi.Color.DEFAULT).a(cornersBack[3].getSeed().name().substring(0, 1)));
                sb.append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
            }
            sb.append(ansi().fg(Ansi.Color.DEFAULT).bg(background).a("         "));
            if (cornersBack[2] == null || cornersBack[2].getSeed() == null) {
                sb.append(ansi().fg(background).bg(background).a("   "));
            } else {
                sb.append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
                sb.append(ansi().fg(cornersBack[2].getSeed().getByAnsi()).bg(Ansi.Color.DEFAULT).a(cornersBack[2].getSeed().name().substring(0, 1)));
                sb.append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
            }
            sb.append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a("\n"));
        }
        return sb.toString();
    }

    /**
     * To string string.
     *
     * @param flipped the flipped
     * @param line    the line
     * @return the string
     */
    public String toString(Boolean flipped, int line) {
        StringBuilder[] sb = new StringBuilder[3];
        Ansi.Color background = Ansi.Color.WHITE;
        for (int i = 0; i < sb.length; i++) {
            sb[i] = new StringBuilder();
        }

        if (!flipped) {
            //First Line
            if (corners[0] == null || corners[0].getSeed() == null) {
                sb[0].append(ansi().fg(background).bg(background).a("   "));
            } else {
                sb[0].append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
                sb[0].append(ansi().fg(corners[0].getSeed().getByAnsi()).bg(Ansi.Color.DEFAULT).a(corners[0].getSeed().name().substring(0, 1)));
                sb[0].append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
            }
            sb[0].append(ansi().fg(background).bg(background).a("         "));
            if (corners[1] == null || corners[1].getSeed() == null) {
                sb[0].append(ansi().fg(background).bg(background).a("   "));
            } else {
                sb[0].append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
                sb[0].append(ansi().fg(corners[1].getSeed().getByAnsi()).bg(Ansi.Color.DEFAULT).a(corners[1].getSeed().name().substring(0, 1)));
                sb[0].append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
            }
            sb[0].append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(""));

            //Second Line -- Card Seeds
            sb[1].append(ansi().fg(background).bg(background).a("      "));

            int l=0, i;
            for(i=0; i<4; i++) {
                if(center[i]){
                    l++;
                }
            }
            switch (l) {
                case 1 -> {
                    for(int j=0; j<4; j++){
                        if(center[j]){
                            sb[1].append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(" "));
                            sb[1].append(ansi().fg(Seed.getById(j).getByAnsi()).bg(Ansi.Color.DEFAULT).a(Seed.getById(j).name().charAt(0)));
                            sb[1].append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(" "));
                        }
                    }
                }
                case 2 -> {
                    int count=0;
                    for(int j=0; j<4; j++){
                        if(center[j]){
                            sb[1].append(ansi().fg(Seed.getById(j).getByAnsi()).bg(Ansi.Color.DEFAULT).a(Seed.getById(j).name().charAt(0)));
                            count++;
                            if (count==1) {
                                sb[1].append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(" "));
                            }
                        }
                    }
                }

                case 3 -> {
                    for(int j=0; j<4; j++){
                        if(center[j]){
                            sb[1].append(ansi().fg(Seed.getById(j).getByAnsi()).bg(Ansi.Color.DEFAULT).a(Seed.getById(j).name().charAt(0)));
                        }
                    }
                }
            }

            sb[1].append(ansi().fg(background).bg(background).a("      "));
            sb[1].append(ansi().bg(Ansi.Color.DEFAULT).a(""));

            //Third Line
            if (corners[3] == null || corners[3].getSeed() == null) {
                sb[2].append(ansi().fg(background).bg(background).a("   "));
            } else {
                sb[2].append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
                sb[2].append(ansi().fg(corners[3].getSeed().getByAnsi()).bg(Ansi.Color.DEFAULT).a(corners[3].getSeed().name().substring(0, 1)));
                sb[2].append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
            }
            sb[2].append(ansi().fg(background).bg(background).a("         "));
            if (corners[2] == null || corners[2].getSeed() == null) {
                sb[2].append(ansi().fg(background).bg(background).a("   "));
            } else {
                sb[2].append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
                sb[2].append(ansi().fg(corners[2].getSeed().getByAnsi()).bg(Ansi.Color.DEFAULT).a(corners[2].getSeed().name().substring(0, 1)));
                sb[2].append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
            }
            sb[2].append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(""));

        } else {
            // Retro della carta
            //First Line
            if (cornersBack[0] == null || cornersBack[0].getSeed() == null) {
                sb[0].append(ansi().fg(background).bg(background).a("   "));
            } else {
                sb[0].append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
                sb[0].append(ansi().fg(cornersBack[0].getSeed().getByAnsi()).bg(Ansi.Color.DEFAULT).a(cornersBack[0].getSeed().name().substring(0, 1)));
                sb[0].append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
            }
            sb[0].append(ansi().cursor(0,0).fg(Ansi.Color.DEFAULT).bg(background).a("         "));
            if (cornersBack[1] == null || cornersBack[1].getSeed() == null) {
                sb[0].append(ansi().fg(background).bg(background).a("   "));
            } else {
                sb[0].append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
                sb[0].append(ansi().fg(cornersBack[1].getSeed().getByAnsi()).bg(Ansi.Color.DEFAULT).a(cornersBack[1].getSeed().name().substring(0, 1)));
                sb[0].append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
            }
            sb[0].append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(""));

            //Second Line
            sb[1].append(ansi().fg(background).bg(background).a("               "));
            sb[1].append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(""));

            //Third Line
            if (cornersBack[3] == null || cornersBack[3].getSeed() == null) {
                sb[2].append(ansi().fg(background).bg(background).a("   "));
            } else {
                sb[2].append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
                sb[2].append(ansi().fg(cornersBack[3].getSeed().getByAnsi()).bg(Ansi.Color.DEFAULT).a(cornersBack[3].getSeed().name().substring(0, 1)));
                sb[2].append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
            }
            sb[2].append(ansi().fg(Ansi.Color.DEFAULT).bg(background).a("         "));
            if (cornersBack[2] == null || cornersBack[2].getSeed() == null) {
                sb[2].append(ansi().fg(background).bg(background).a("   "));
            } else {
                sb[2].append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
                sb[2].append(ansi().fg(cornersBack[2].getSeed().getByAnsi()).bg(Ansi.Color.DEFAULT).a(cornersBack[2].getSeed().name().substring(0, 1)));
                sb[2].append(ansi().fg(background).bg(Ansi.Color.DEFAULT).a(" "));
            }
            sb[2].append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(""));
        }
        return sb[line].toString();
    }

}
