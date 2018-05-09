package com.athir.uno.ui;

import com.athir.uno.gamelogic.IMove;

public interface IMoveItem {

    String getLabel();
    int getColorID();
    boolean isEnabled();

    IMove getMove();

}
