package com.athir.uno.ui;

public class DrawMoveItem implements IMoveItem {

    @Override
    public String getLabel() {
        return "Draw a card";
    }

    @Override
    public int getColorID() {
        return -1;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public void accept(IMoveItemVisitor visitor) {
        visitor.visit(this);
    }
}