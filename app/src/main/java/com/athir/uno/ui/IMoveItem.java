package com.athir.uno.ui;

import com.athir.uno.gamelogic.IMove;

/**
 * Represents one button in the move view.
 */
public interface IMoveItem {

    /**
     * @return the text label for the button
     */
    String getLabel();

    /**
     * @return the text color of the label
     */
    int getColorID();

    /**
     * @return true if the button should be enabled
     */
    boolean isEnabled();

    /**
     * @return the move this item represents, can be null if there is no move
     */
    IMove getMove();

}
