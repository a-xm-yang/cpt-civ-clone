package civilizationclone.GUI;

import civilizationclone.*;
import civilizationclone.Tile.*;
import civilizationclone.Unit.MilitaryUnit;
import civilizationclone.Unit.Unit;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.animation.FadeTransition;
import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public abstract class GamePane extends Pane {

    //A pane class that handles everything that is game-related
    private int resX, resY;
    private DisplayMap displayMap;
    private CityMenu cityMenu;
    private NextTurnPane nextButton;
    private StatusBarPane statusBar;
    private SciencePane sciencePane;
    private Minimap minimap;
    public MediaPlayer mp, dmp, wmp;
    public Media win, loss, background;
    private Stage primaryStage;
    private boolean isMuted;
    private static Effect shadow = new DropShadow(30, Color.WHITE);
    private static Effect noneEffect = null;

    private boolean[] messageSlot;
    private boolean activityLocked;
    private HashMap<String, Boolean> activeMap;

    //game data
    private GameState gameState;

    public GamePane(GameState gameState, int resX, int resY, boolean isMuted, Stage primaryStage) {

        this.gameState = gameState;
        this.primaryStage = primaryStage;
        activeMap = new HashMap<String, Boolean>();

        this.resX = resX;
        this.resY = resY;
        this.setPrefHeight(resY);
        this.setPrefWidth(resX);
        this.isMuted = isMuted;

        messageSlot = new boolean[10];
        activityLocked = false;

        //Loading music 
        background = new Media(getClass().getClassLoader().getResource("Assets/Misc/babayetu.mp3").toExternalForm());
        win = new Media(getClass().getClassLoader().getResource("Assets/Misc/ConquestVictory.mp3").toExternalForm());
        loss = new Media(getClass().getClassLoader().getResource("Assets/Misc/Loss.mp3").toExternalForm());

        mp = new MediaPlayer(background);
        mp.play();
        mp.setOnEndOfMedia(() -> {
            mp.seek(Duration.ZERO);
        });

        if (isMuted) {
            mp.setMute(true);
        }

        //initialize all the elements of the game pane, and add them into the pane
        displayMap = createFalseDisplayMap(gameState.getGameMap().getMap());
        minimap = new Minimap(displayMap, resX, resY);
        nextButton = new NextTurnPane(gameState.getCurrentPlayer(), resX, resY, this);
        statusBar = new StatusBarPane(gameState.getCurrentPlayer(), resX, resY, this);
        sciencePane = new SciencePane(gameState.getCurrentPlayer(), resX, resY, this);

        getChildren().add(displayMap);
        getChildren().add(minimap);
        getChildren().add(nextButton);
        getChildren().add(statusBar);
        getChildren().add(sciencePane);

        displayMap.setCurrentPlayer(gameState.getCurrentPlayer());
        updateInfo();

    }

    public abstract void requestAction(String s);

    public abstract void receiveAction(String s);

    public abstract void nextTurn();

    public abstract void endGameCheck();

    public void updateControllingPlayer() {
        //start the player's turn and set all the element's current info display to this player
        nextButton.setCurrentPlayer(gameState.getCurrentPlayer());
        statusBar.setCurrentPlayer(gameState.getCurrentPlayer());
        sciencePane.setCurrentPlayer(gameState.getCurrentPlayer());
        displayMap.setCurrentPlayer(gameState.getCurrentPlayer());

        updateInfo();
    }

    public void jumpToNextAction() {

        //this method is invoked when a player cannot finish turn, yet clicked next turn
        //this opens up the corresponding action that the player has to take in order to force him/her to take it
        if (gameState.getCurrentPlayer().canEndTurn() == 1) {
            for (Unit u : gameState.getCurrentPlayer().getUnitList()) {
                if (u.canMove()) {

                    //skip to next if it is fortified, which means it doesn't move
                    if (u instanceof MilitaryUnit) {
                        if (((MilitaryUnit) u).isFortified()) {
                            continue;
                        }
                    }

                    //Jump the player to the units that they haven't move yet and force them to make an action
                    displayMap.jumpTo(u);
                    //deletes all the prevoius menus open
                    for (Node n : displayMap.getChildren()) {
                        if (n instanceof UnitMenu) {
                            ((UnitMenu) n).delete();
                            break;
                        }
                    }
                    //add the new map and have tiles ready to move
                    UnitMenu uM = new UnitMenu(u, displayMap);
                    displayMap.setSelectedTile(gameState.getGameMap().getTile(u.getX(), u.getY()));
                    uM.setTranslateX(((displayMap.getLeftCap() - (u.getY() * DisplayTile.WIDTH * getScaleX())) * -1) + (u.getX() % 2 == 0 ? (DisplayTile.getWIDTH() / 2 * getScaleX()) * -1 : 0));
                    uM.setTranslateY(u.getX() * DisplayTile.HEIGHT * getScaleY());
                    displayMap.setUnitMenu(uM);
                    displayMap.getChildren().add(uM);
                    return;
                }
            }
        } else if (gameState.getCurrentPlayer().canEndTurn() == 2 || gameState.getCurrentPlayer().canEndTurn() == 4) {
            for (City c : gameState.getCurrentPlayer().getCityList()) {
                if (!c.canEndTurn() || c.canExpand()) {
                    //Jump to the city position, disable dragging
                    displayMap.jumpTo(c);
                    displayMap.enableDragging(false);
                    //set the selected tile for later usage
                    displayMap.setSelectedTile(gameState.getGameMap().getTile(c.getPosition()));
                    //add a new city map
                    cityMenu = new CityMenu(c, resX, resY, displayMap);
                    getChildren().add(cityMenu);
                    return;
                }
            }
        } else if (gameState.getCurrentPlayer().canEndTurn() == 3) {
            sciencePane.openScienceMenu();
        }

    }

    public void readNotificationFromGame() {
        while (!gameState.isNotificationEmpty()) {
            displayNotification(gameState.readNotification());
        }
    }

    public void displayNotification(String s) {

        int p = 0;
        for (int i = 0; i < messageSlot.length; i++) {
            if (!messageSlot[i]) {
                p = i;
                messageSlot[i] = true;
                break;
            }
        }

        Text text = new Text(s);
        text.setFont(Font.font("Oswald", 26));
        text.setStroke(Color.BLACK);
        text.setStrokeWidth(2);
        text.setFill(Color.BLACK);
        text.setTranslateX(resX * 0.5 - text.getBoundsInLocal().getWidth() * 0.5);
        text.setTranslateY(185 - text.getBoundsInLocal().getHeight() * 0.5 + text.getBoundsInLocal().getHeight() * p);
        text.setMouseTransparent(true);
        getChildren().add(text);

        final int messagePosition = p;

        FadeTransition ft = new FadeTransition(Duration.seconds(7), text);
        ft.setDelay(Duration.seconds(2));
        ft.setFromValue(1.0);
        ft.setToValue(0);
        ft.setOnFinished((e) -> {
            getChildren().remove(text);
            messageSlot[messagePosition] = false;
        });

        ft.play();
    }

    //two methods used to add/remove city menus from the game pane
    public void addCityMenu(City c) {
        cityMenu = new CityMenu(c, resX, resY, displayMap);
        getChildren().add(cityMenu);
    }

    public void removeCityMenu() {
        if (getChildren().contains(cityMenu)) {
            getChildren().remove(cityMenu);
        }

        updateInfo();
        displayMap.enableDragging(true);
    }

    public void updateInfo() {
        //update all information in all displays
        statusBar.updateTexts();
        sciencePane.updateInfo();
        displayMap.updateInfo();
        minimap.update();
        nextButton.updateText();
    }

    private DisplayMap createFalseDisplayMap(Tile[][] original) {

        //creates a copy of the actual tile map, with the sides adjusted according to X-resolution to a copied version for scrolling effect
        //first calculate how much spare is needed on both sides, should be according to scene size
        int spareSize = resX / 100 + 1;

        Tile[][] copy = new Tile[original.length][original[0].length + spareSize * 2];

        //copy the back of the original to the front spare, then front of the original to the back spare
        //first copy the back to front
        for (int x = 0; x < copy.length; x++) {
            for (int y = 0; y < spareSize; y++) {
                copy[x][y] = original[x][original[0].length - spareSize + y];
            }
        }
        //then copy the original chunk
        for (int x = 0; x < copy.length; x++) {
            for (int y = 0; y < original[0].length; y++) {
                copy[x][y + spareSize] = original[x][y];
            }
        }
        //then copy the front of the orignial to the back spare
        for (int x = 0; x < copy.length; x++) {
            for (int y = 0; y < spareSize; y++) {
                copy[x][y + spareSize + copy.length] = original[x][y];
            }
        }

        DisplayMap pane = new DisplayMap(original.length, resX, resY, spareSize, copy, this);

        return pane;

    }

    //GETTER & SETTER
    //<editor-fold>
    public boolean isIsMuted() {
        return isMuted;
    }

    public int getResY() {
        return resY;
    }

    public int getResX() {
        return resX;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public HashMap<String, Boolean> getActiveMap() {
        return activeMap;
    }

    public DisplayMap getDisplayMap() {
        return displayMap;
    }

    public ArrayList<Player> getPlayerList() {
        return gameState.getPlayerList();
    }

    public GameState getGameState() {
        return gameState;
    }

    public MediaPlayer getDmp() {
        return dmp;
    }

    public Media getLoss() {
        return loss;

    }

    public NextTurnPane getNextButton() {
        return nextButton;
    }

    public boolean isActivityLocked() {
        return activityLocked;
    }

    public void setActivityLocked(boolean activityLocked) {
        this.activityLocked = activityLocked;
    }

    public Media getWin() {
        return win;
    }

    public MediaPlayer getWmp() {
        return wmp;
    }

    public StatusBarPane getStatusBar() {
        return statusBar;
    }

    //</editor-fold>
    private void removeDefeatedPrompt(GamePane.DefeatedPrompt dp) {
        getChildren().remove(dp);
    }

    class DefeatedPrompt extends Pane {

        //a prompt that shows up when the player has either won or lost the game
        private Rectangle border;
        private Circle confirmButton;
        private Text title;

        private boolean defeated;

        public DefeatedPrompt(int resX, int resY, boolean defeated) {
            this.defeated = defeated;

            border = new Rectangle(400, 100);
            border.setFill(new ImagePattern(ImageBuffer.getImage(MiscAsset.CITY_OPTION_BACKGROUND), 0, 0, 1, 1, true));
            border.setStrokeWidth(5);
            border.setStroke(Color.BLACK);

            setTranslateX(resX / 2 - border.getWidth() / 2);
            setTranslateY(resY / 2 - border.getHeight() / 2);

            if (defeated) {
                title = new Text("YOU HAVE BEEN DEFEATED");
            } else {
                title = new Text("YOU ARE VICTORIOUS");
            }

            title.setFont(Font.font("OSWALD", 25));
            title.setFill(Color.WHITESMOKE);
            title.setTranslateY(border.getHeight() / 2 - title.getLayoutBounds().getHeight() / 2);
            title.setTranslateX(15);

            confirmButton = new Circle(border.getWidth() - 20, border.getHeight() - 20, 14.5);
            confirmButton.setFill(new ImagePattern(ImageBuffer.getImage(MiscAsset.CONFIRM_ICON)));
            confirmButton.setOnMouseClicked((e) -> {
                e.consume();
                close();
            });
            confirmButton.effectProperty().bind(
                    Bindings.when(confirmButton.hoverProperty()).then(shadow).otherwise(noneEffect)
            );

            getChildren().addAll(border, title, confirmButton);

            if (defeated) {
                statusBar.removeHead(gameState.getCurrentPlayer().getName());
            }
        }

        private void close() {

            removeDefeatedPrompt(this);
            mp.setVolume(1);
            updateControllingPlayer();

            try {
                dmp.pause();
                wmp.pause();
            } catch (Exception e) {
            }

            if (!defeated || (gameState.getPlayerList().size() == 0)) {
                mp.stop();
                primaryStage.setScene(new Scene(new TitleMenu(resX, resY, primaryStage), resX, resY));
            }

        }
    }

}
