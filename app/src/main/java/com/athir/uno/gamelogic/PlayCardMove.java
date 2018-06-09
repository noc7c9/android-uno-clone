package com.athir.uno.gamelogic;

/**
 * Move class for playing card from the hand.
 */
public class PlayCardMove implements IMove {

    private int player;
    private ICard card;

    /**
     * @param player the player to make the move
     * @param card the card to be played
     */
    PlayCardMove(int player, ICard card) {
        this.player = player;
        this.card = card;
    }

    @Override
    public void accept(IMoveVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public int getPlayer() {
        return player;
    }

    /**
     * @return the card this move will play
     */
    public ICard getCard() {
        return card;
    }

    @Override
    public String toString() {
        return String.format("PlayCardMove(%s)", card.toString());
    }
}
