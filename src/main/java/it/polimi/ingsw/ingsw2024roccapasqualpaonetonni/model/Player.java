package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

public class Player {
    private String nickname;
    private int colorPlayer;
    private int currentPoints;
    private int goalPoints;
    private int[] countSeed =new int[7];
    private PlayerBoard board;
    private PlayingCard[] hand =new PlayingCard[3];
    private ObjectiveCard goal;
    private ObjectiveCard[] firstGoals=new ObjectiveCard[2];
    private StartingCard startingCard;
    public Player(String name,int color){

        this.nickname=name;
        this.colorPlayer=color;
        this.currentPoints=0;
        this.goalPoints=0;
        this.countSeed=null;
        //this.board= new PlayerBoard();
        this.hand=null;
        this.goal=null;
        this.firstGoals=null;
        this.startingCard=null;

    }
    public void drawGoals(DrawableDeck d){
        firstGoals[0]=d.drawFirstObjective();
        firstGoals[1]=d.drawFirstObjective();
    }
    public void chooseGoal(int choice){
        if(choice==0){
            goal=firstGoals[0];
        }
        else {
            goal=firstGoals[1];
        }
    }
    public void drawStarting(DrawableDeck d){
        startingCard=d.drawFirstStarting();
    }

    public int[] getCountSeed() {
        return countSeed;
    }
   //manca il mettere una carta nella board e poi ripescarla dai drawable deck per sostituire la carta mancante nella mano
}
