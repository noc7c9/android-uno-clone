package com.athir.uno.ui;

public interface IMoveItemVisitor {

    void visit(DiscardMoveItem moveItem);
    void visit(DrawMoveItem moveItem);
    void visit(InvalidMoveItem moveItem);

}