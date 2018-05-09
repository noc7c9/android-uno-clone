package com.athir.uno.gamelogic;

public interface IMove {

    void accept(IMoveVisitor visitor);

    int getPlayer();

}