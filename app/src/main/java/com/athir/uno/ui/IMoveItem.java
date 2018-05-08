package com.athir.uno.ui;

public interface IMoveItem {

    String getLabel();
    int getColorID();
    boolean isEnabled();

    void accept(IMoveItemVisitor visitor);

}
