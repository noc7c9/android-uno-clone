package com.athir.uno.gamelogic;

public interface IMoveVisitor {

    void visit(PlayCardMove move);
    void visit(DrawCardMove move);

}