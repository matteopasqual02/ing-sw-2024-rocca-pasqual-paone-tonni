package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class DrawableDeck {
    private Queue<GoldCard> goldQueue=new LinkedList<>();
    private Queue<ResourceCard> resourceQueue=new LinkedList<>();
    private Queue<ObjectiveCard> objectiveQueue=new LinkedList<>();
    private Queue<StartingCard> startingQueue=new LinkedList<>();

    public DrawableDeck(){
        this.goldQueue = null;
        this.resourceQueue  = null;
        this.objectiveQueue = null;
        this.startingQueue  = null;
    }
    public DrawableDeck(Collection<GoldCard>gc,Collection<ResourceCard>rc,Collection<ObjectiveCard>oc,Collection<StartingCard>sc){
        this.goldQueue.addAll(gc);
        this.resourceQueue.addAll(rc);
        this.objectiveQueue.addAll(oc);
        this.startingQueue.addAll(sc);
    }
    public ResourceCard drawFirstResource()
    {
        return resourceQueue.poll();
    }
    public GoldCard drawFirstGold()
    {
        return goldQueue.poll();
    }
    public ObjectiveCard drawFirstObjective()
    {
        return objectiveQueue.poll();
    }
    public StartingCard drawFirstStarting()
    {
        return startingQueue.poll();
    }

    public void setResourceCard(ResourceCard r){
        resourceQueue.add(r);
    }
    public void setGoldCards(GoldCard g){
        goldQueue.add(g);
    }
    public void setObjectiveCard(ObjectiveCard o){
        objectiveQueue.add(o);
    }
    public void setStartingCard(StartingCard s){
        startingQueue.add(s);
    }
}
