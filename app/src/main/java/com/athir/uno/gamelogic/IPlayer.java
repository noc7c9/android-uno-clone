package com.athir.uno.gamelogic;

import java.util.List;

public interface IPlayer {

    void updateState(int playerID, Card topDiscardCard, int drawPileSize, List<Integer> handSizes, List<Card> hand);

    void requestMove(Referee referee, List<IMove> moves);

    void notifyGameOver(boolean isWinner);

}