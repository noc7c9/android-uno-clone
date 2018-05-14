package com.athir.uno;

import com.athir.uno.gamelogic.ICard;
import com.athir.uno.gamelogic.IMove;

import java.util.List;

/**
 * Represents a player that can play the game via a Referee object.
 */
interface IPlayer {

    /**
     * Called on the player when the state of game changes.
     *
     * @param playerID       this player's ID
     * @param topDiscardCard the top card on the discard pile
     * @param drawPileSize   the number of cards left in the draw pile
     * @param handSizes      the number of cards in the hands of every player
     * @param hand           the cards in this player's hand
     */
    void updateState(int playerID, ICard topDiscardCard, int drawPileSize, List<Integer> handSizes, List<ICard> hand);

    /**
     * Called by the referee when it is the player's turn and they can make a move
     *
     * @param referee the referee object to respond to
     * @param moves   the moves available to the player
     */
    void requestMove(Referee referee, List<IMove> moves);

    /**
     * Called by the referee when the game is over
     *
     * @param isWinner true if this player is the winner
     */
    void notifyGameOver(boolean isWinner);

}