package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

import org.fusesource.jansi.Ansi;

public enum Seed {
    GREEN("green", 0), BLUE("blue", 1),
    RED("red", 2), PURPLE("purple", 3), FEATHER("feather", 4),
    POTION("potion", 5), SCROLL("scroll", 6), EMPTY("empty", 7);
    private final String name;
    private final int index;

    Seed(String name, int index) {
        this.name = name;
        this.index = index;
    }
    public String getName() {
        return this.name;
    }

    public static Seed getById(int id) {
        for (Seed s: Seed.values()) {
            if (s.index == id) {
                return s;
            }
        }
        return null;
    }

    public static Seed getByName(String name) {
        for (Seed s: Seed.values()) {
            if (s.name.equals(name)) {
                return s;
            }
        }
        return null;
    }

    public int getId() {
        return index;
    }

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

        }

        return Ansi.Color.DEFAULT;
    }
}