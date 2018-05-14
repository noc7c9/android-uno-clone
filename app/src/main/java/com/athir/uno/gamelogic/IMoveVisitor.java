package com.athir.uno.gamelogic;

/**
 * The Visitor pattern interface for IMove objects.
 */
@SuppressWarnings("JavaDoc")
public interface IMoveVisitor {

    void visit(PlayCardMove move);
    void visit(DrawCardMove move);

}