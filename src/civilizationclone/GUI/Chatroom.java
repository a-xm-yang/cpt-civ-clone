package civilizationclone.GUI;

import java.util.ArrayList;
import javafx.animation.FadeTransition;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class Chatroom extends Pane {

    private int resX, resY;
    private String playerName;
    private boolean isOpen;

    private MultiplayerPane mPane;
    private Rectangle border;
    private Rectangle toggleIcon;
    private TextField textField;
    private TextArea ta;
    private Rectangle sendIcon;

    private static Effect shadowBig = new DropShadow(30, Color.BLACK);
    private static Effect lightBig = new DropShadow(30, Color.WHITE);
    private static Effect noneEffect = null;

    public Chatroom(int resX, int resY, MultiplayerPane mPane) {

        this.isOpen = false;
        this.resX = resX;
        this.resY = resY;
        this.mPane = mPane;

        playerName = mPane.getGameState().getCurrentPlayer().getName();

        border = new Rectangle(400, 300);
        border.setFill(Color.BLACK);
        border.setStrokeWidth(5);
        border.setStrokeType(StrokeType.CENTERED);
        border.setStroke(Color.GREY);
        border.setTranslateY(56);
        border.setStrokeLineCap(StrokeLineCap.ROUND);
        border.setOpacity(0.9);

        toggleIcon = new Rectangle(50, 50);
        toggleIcon.setFill(new ImagePattern(ImageBuffer.getImage(MiscAsset.CHAT_ICON)));
        toggleIcon.setStroke(Color.BLACK);
        toggleIcon.setStrokeWidth(5);
        toggleIcon.setStrokeType(StrokeType.CENTERED);
        toggleIcon.setStrokeLineCap(StrokeLineCap.ROUND);
        toggleIcon.effectProperty().bind(
                Bindings.when(toggleIcon.hoverProperty()).then(shadowBig).otherwise(noneEffect)
        );
        toggleIcon.setOnMouseClicked(e -> {
            toggleOpen();
        });

        textField = new TextField();
        textField.setFont(Font.font("Oswald"));
        textField.setPromptText("Enter message...");
        textField.setPrefColumnCount(1);
        textField.setPrefWidth(330);
        textField.setTranslateX(10);
        textField.setTranslateY(322);
        textField.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                sendMessage();
            }

            e.consume();
        });

        ta = new TextArea();
        ta.setEditable(false);
        ta.setFont(Font.font("Oswald", 13));
        ta.setText("---Welcome to the Chatroom---");
        ta.setWrapText(true);
        ta.setPrefHeight(250);
        ta.setPrefWidth(380);
        ta.setTranslateX(10);
        ta.setTranslateY(65);

        sendIcon = new Rectangle(32, 32);
        sendIcon.setFill(new ImagePattern(ImageBuffer.getImage(MiscAsset.SEND_ICON)));
        sendIcon.setTranslateX(351);
        sendIcon.setTranslateY(317);
        sendIcon.effectProperty().bind(
                Bindings.when(sendIcon.pressedProperty()).then(lightBig).otherwise(noneEffect)
        );
        sendIcon.setOnMouseClicked(e -> {
            sendMessage();
        });

        getChildren().add(toggleIcon);

        setTranslateY(resY / 2 - 100);
        setTranslateX(5);
    }

    private void sendMessage() {
        if (isOpen && !textField.getCharacters().toString().equals("")) {
            String s = playerName + ": " + textField.getCharacters().toString();
            textField.clear();
            receiveMessage(s);
            mPane.requestAction("Chat" + s);
        }
    }

    public void receiveMessage(String s) {
        ta.appendText("\n" + s);
        if (!isOpen) {
            notifyEffect();
        }
    }

    public void notifyEffect() {
        FadeTransition ft = new FadeTransition(Duration.seconds(0.2), toggleIcon);
        ft.setCycleCount(2);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
    }

    private void toggleOpen() {
        if (isOpen) {
            isOpen = false;
            getChildren().remove(border);
            getChildren().remove(textField);
            getChildren().remove(ta);
            getChildren().remove(sendIcon);

        } else {
            isOpen = true;
            getChildren().add(border);
            getChildren().add(textField);
            getChildren().add(ta);
            getChildren().add(sendIcon);
        }
    }

    public boolean isOpen() {
        return isOpen;
    }

}
