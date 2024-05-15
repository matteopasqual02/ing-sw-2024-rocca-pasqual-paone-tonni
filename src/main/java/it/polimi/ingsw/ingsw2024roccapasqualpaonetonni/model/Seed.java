package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

import org.fusesource.jansi.Ansi;

/**
 * The enum Seed.
 */
public enum Seed {
    /**
     * Green seed.
     */
    GREEN("green", 0),
    /**
     * Blue seed.
     */
    BLUE("blue", 1),
    /**
     * Red seed.
     */
    RED("red", 2),
    /**
     * Purple seed.
     */
    PURPLE("purple", 3),
    /**
     * Feather seed.
     */
    FEATHER("feather", 4),
    /**
     * Potion seed.
     */
    POTION("potion", 5),
    /**
     * Scroll seed.
     */
    SCROLL("scroll", 6),
    /**
     * Empty seed.
     */
    EMPTY("empty", 7);
    /**
     * The Name.
     */
    private final String name;
    /**
     * The Index.
     */
    private final int index;

    /**
     * Instantiates a new Seed.
     *
     * @param name  the name
     * @param index the index
     */
    Seed(String name, int index) {
        this.name = name;
        this.index = index;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets by id.
     *
     * @param id the id
     * @return the by id
     */
    public static Seed getById(int id) {
        for (Seed s: Seed.values()) {
            if (s.index == id) {
                return s;
            }
        }
        return null;
    }

    /**
     * Gets by name.
     *
     * @param name the name
     * @return the by name
     */
    public static Seed getByName(String name) {
        for (Seed s: Seed.values()) {
            if (s.name.equals(name)) {
                return s;
            }
        }
        return null;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return index;
    }

    /**
     * Get by ansi ansi . color.
     *
     * @return the ansi . color
     */
    public Ansi.Color getByAnsi(){
        switch (this){
            case GREEN -> {
                return Ansi.Color.GREEN;
            }
            case BLUE -> {
                return Ansi.Color.BLUE;
            }
            case RED -> {
                return Ansi.Color.RED;
            }
            case PURPLE -> {
                return Ansi.Color.MAGENTA;
            }
            case FEATHER, POTION, SCROLL -> {
                return Ansi.Color.WHITE;
            }
            default -> {
                return Ansi.Color.DEFAULT;
            }
        }

    }
}