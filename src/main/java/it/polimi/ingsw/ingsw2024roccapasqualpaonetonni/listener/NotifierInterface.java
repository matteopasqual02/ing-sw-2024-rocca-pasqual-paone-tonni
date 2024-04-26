package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.PlayingCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.StartingCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.objective.ObjectiveCard;

import java.io.IOException;

public interface NotifierInterface {
    void sendMaxNumPlayersSet(int gameId,int max) throws IOException, ClassNotFoundException;
    void sendAddedPlayer(Player newPlayer);
    void sendPlayerReady(Player p);
    void sendFullGame();
    void sendNameAlreadyInGame();
    void sendPlayerRemoved(Player p);
    void sendNextTurn(Player p);
    void sendLastTurn();
    void sendReconnectedPlayer(String nickname);
    void sendReconnectionImpossible(String nickname);
    void sendDisconnectedPlayer(String nickname);
    void sendDisconnectionImpossible(String nickname);
    void sendStatusSet(GameStatus status);
    void sendStatusSetToLastStatus(GameStatus status);
    void sendLastStatusReset();
    void sendPlayerIsReady(Player p);
    //void tableCreated(GameImmutable model);
    //void playersNotReady(GameImmutable model);
    void sendFirstPlayerSet(Player first);
    void sendDrawableDeckSet(DrawableDeck d);
    void sendBoardDeckSet(BoardDeck bd);

    void sendStartAdded(PlayerBoard board, Player p);
    void sendCardAdded(PlayerBoard board, Player p);
    void sendChoseInvalidPlace(Player p);
    void sendConditionsNotMet(Player p);
    void sendStartingCardDrew(StartingCard start, Player p);
    void sendDrewPersonalGoals(ObjectiveCard[] goals, Player p);
    void sendPersonalGoalChosen(ObjectiveCard goal, Player p);
    void sendCardNotInHand(PlayingCard card, Player p);
    void sendResourceDrawn(PlayingCard card, Player p);
    void sendGoldDrawn(PlayingCard card, Player p);
    void sendDrewFromBoard(PlayingCard card, Player p);
    void sendPlayerIsConnected(Player p);
    void sendPointsIncreased(int points,Player p);
    void sendSeedCountUpdated(int[] seedCount,Player p);
    void sendCardRemovedFromHand(PlayingCard card, Player p);
}
