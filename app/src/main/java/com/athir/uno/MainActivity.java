package com.athir.uno;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.athir.uno.gamecore.Card;
import com.athir.uno.gamecore.GameState;
import com.athir.uno.ui.DiscardMoveItem;
import com.athir.uno.ui.DrawMoveItem;
import com.athir.uno.ui.IMoveItem;
import com.athir.uno.ui.InvalidMoveItem;
import com.athir.uno.ui.MoveViewAdaptor;
import com.athir.uno.ui.Utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public static final int NUM_PLAYERS = 2;
    public static final int CPU_ID = 1;
    public static final int PLAYER_ID = 0;

    private TextView cpuHandText;
    private TextView playerHandText;

    private TextView drawPileText;
    private TextView discardPileText;

    private GridView handView;
    private MoveViewAdaptor handViewAdaptor;

    private GameState gameState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cpuHandText = findViewById(R.id.cpuHand);
        playerHandText = findViewById(R.id.playerHand);
        drawPileText = findViewById(R.id.drawPile);
        discardPileText = findViewById(R.id.discardPile);
        handView = findViewById(R.id.handView);

        gameState = new GameState(NUM_PLAYERS);

        handViewAdaptor = new MoveViewAdaptor(this, getMoveItems());
        handView.setAdapter(handViewAdaptor);
        handView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            private MoveItemPlayVisitor playVisitor = new MoveItemPlayVisitor(gameState);

            public void onItemClick(AdapterView parent, View v, int position, long id) {
                //Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();
                handViewAdaptor.getItem(position).accept(playVisitor);
                gameState.cpuMove();
                //PLAYER_ID = PLAYER_ID == 1 ? 0 : 1;
                handViewAdaptor.updateMoveItems(getMoveItems());
                updateUI();
            }
        });

        updateUI();
    }

    private List<IMoveItem> getMoveItems() {
        List<Card> hand = gameState.getHand(PLAYER_ID);
        List<IMoveItem> moveItems = new ArrayList<>(hand.size() + 1);

        for (Card card : hand) {
            if (gameState.isValidPlay(card)) {
                moveItems.add(new DiscardMoveItem(card));
            } else {
                moveItems.add(new InvalidMoveItem(card));
            }
        }

        moveItems.add(new DrawMoveItem());

        return moveItems;
    }

    private void updateUI() {
        cpuHandText.setText(String.format(Locale.getDefault(),
                "CPU Hand: %d cards", gameState.getHandSize(CPU_ID)));
        playerHandText.setText(String.format(Locale.getDefault(),
                "Your Hand: %d cards", gameState.getHandSize(PLAYER_ID)));

        drawPileText.setText(String.format(Locale.getDefault(),
                "%d cards\nremaining\nin deck", gameState.getDrawPileSize()));

        Card topCard = gameState.getTopCard();
        discardPileText.setText(topCard.toString());

        int colorId = Utility.cardColorToColorId(topCard.getColor());
        discardPileText.setBackgroundColor(getResources().getColor(colorId));
    }

}