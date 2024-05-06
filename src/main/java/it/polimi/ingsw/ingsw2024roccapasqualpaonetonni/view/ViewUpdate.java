package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view;

public interface ViewUpdate {
    void joinLobby(Client client);

    void show_maxNumPlayersSet(int max);
    void show_createdGame(int gameID);
    void show_youJoinedGame(int gameID);
    void show_noAvailableGame();
    void show_addedNewPlayer(String pNickname);
    void show_areYouReady();
}
