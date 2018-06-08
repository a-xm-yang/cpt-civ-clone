package civilizationclone.GUI;

import civilizationclone.Player;
import javafx.beans.binding.Bindings;
import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class NextTurnPane extends Group {

    private Rectangle rect;
    private Text text;
    private int resX;
    private int resY;
    private Player player;
    private GamePane gamePaneRef;
    private DisplayMap dMapRef;
    private static Effect shadow = new DropShadow(40, Color.WHITE);
    private static Effect noneEffect = null;

    public NextTurnPane(Player player, int resX, int resY, GamePane gamePaneRef) {
        this.text = new Text();
        text.setFont(Font.font("Oswald"));
        this.resX = resX;
        this.resY = resY;
        this.player = player;
        this.rect = new Rectangle(0, 0, 311, 40);
        this.gamePaneRef = gamePaneRef;
        this.dMapRef = gamePaneRef.getDisplayMap();
        this.setTranslateX(resX - 311);
        this.setTranslateY(resY - 316.5 - rect.getHeight());

        text.setFill(Color.BEIGE);
        text.effectProperty().bind(
                Bindings.when(text.hoverProperty()).then(shadow).otherwise(noneEffect)
        );

        updateText();

        rect.setStrokeWidth(5);
        rect.setStroke(Color.BEIGE);
        rect.setStrokeLineJoin(StrokeLineJoin.ROUND);

        getChildren().add(rect);
        getChildren().add(text);

        rect.fillProperty().bind(
                Bindings.when(pressedProperty()).then(Color.color(0.5, 0.5, 0.5, 1)).otherwise(Color.DARKSLATEBLUE)
        );

        setOnMouseClicked((MouseEvent e) -> {
            clickEvent(e);
        });

    }

    public void clickEvent(MouseEvent e) {

        if (!gamePaneRef.isActivityLocked()) {
            if (this.player.canEndTurn() == 0) {
                if (gamePaneRef instanceof MultiplayerPane) {
                    gamePaneRef.setActivityLocked(true);
                }
                gamePaneRef.requestAction("Next" + "/" + player.getName());
            } else {
                gamePaneRef.jumpToNextAction();
            }
        } else {
            gamePaneRef.setActivityLocked(false);
            gamePaneRef.requestAction("Cancel/" + player.getName());
        }

        updateText();
        e.consume();
    }

    public void setCurrentPlayer(Player player) {
        this.player = player;
    }

    public void updateText() {

        //Checks to see if the player can go to the next turn
        //If he can't it instucts him what he needs to do
        if (!gamePaneRef.isActivityLocked()) {
            if (player.canEndTurn() == 2) {
                text.setText("MUST SELECT CITY PROJECT");
            } else if (player.canEndTurn() == 1) {
                text.setText("A UNIT NEEDS ORDERS");
            } else if (player.canEndTurn() == 4) {
                text.setText("A CITY CAN EXPAND");
            } else if (player.canEndTurn() == 3) {
                text.setText("MUST SELECT REASEARCH");
            } else {
                text.setText("NEXT TURN");
            }

        } else {
            text.setText("WAITING FOR OTHERS...");
        }

        text.setFill(Color.WHITE);
        text.setFont(Font.font("Oswald", 20));
        text.setTranslateX((rect.getWidth() - text.getLayoutBounds().getWidth()) / 2);
        text.setTranslateY(text.getLayoutBounds().getHeight() + 2);
    }

    void delete() {
        gamePaneRef.getChildren().remove(this);
    }
}
