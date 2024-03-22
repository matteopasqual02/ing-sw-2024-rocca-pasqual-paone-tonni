package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

public enum Seed {
    GREEN("green", 0), BLUE("blue", 1),
    RED("red", 2), PURPLE("purple", 3), FEATHER("feather", 4),
    POTION("potion", 5), SCROLL("scroll", 6), MIXED("mixed", 7), EMPTY("empty", 8);
    private String name;
    private int index;
    private Seed(String name, int index) {
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
}