package com.athir.uno.gamelogic;

/**
 * Represents a move to be made.
 *
 * Supports the visitor pattern to allow operating on different types of moves.
 */
public interface IMove {

    /**
     * Part of the visitor pattern
     *
     * @param visitor the visitor object to accept
     */
    void accept(IMoveVisitor visitor);

    /**
     * @return the player to make the move
     */
    int getPlayer();

}