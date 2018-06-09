package com.athir.uno.gamelogic;

/**
 * Move class for drawing a card.
 */
public class DrawCardMove implements IMove {

    private final int player;

    /**
     * @param player the player to make the move
     */
    DrawCardMove(int player) {
        this.player = player;
    }

    @Override
    public void accept(IMoveVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public int getPlayer() {
        return player;
    }

}
