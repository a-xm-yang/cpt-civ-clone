package civilizationclone.GUI;

import civilizationclone.*;
import civilizationclone.Tile.*;
import civilizationclone.Unit.SettlerUnit;
import civilizationclone.Unit.Unit;
import civilizationclone.Unit.WarriorUnit;
import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class GamePane extends Pane {

    //A pane class that handles everything that is game-related
    private int resX, resY;
    private ZoomMap zoomMap;
    private CityMenu cityMenu;
    private NextTurnPane nextButton;
    private StatusBarPane statusBar;
    private SciencePane sciencePane;
    private Minimap minimap;
    private MediaPlayer mp, dmp, wmp;
    private Media win, loss, background;

    //game data
    private GameMap gameMap;
    private ArrayList<Player> playerList;
    private Player currentPlayer;

    public GamePane(GameMap gameMap, ArrayList<Player> playerList, int resX, int resY, boolean isNewGame) {

        //Loading music 
        background = new Media(getClass().getClassLoader().getResource("Assets/Misc/babayetu.mp3").toExternalForm());
        win = new Media(getClass().getClassLoader().getResource("Assets/Misc/ConquestVictory.mp3").toExternalForm());
        loss = new Media(getClass().getClassLoader().getResource("Assets/Misc/loss.mp3").toExternalForm());

        mp = new MediaPlayer(background);
        mp.play();
        mp.setOnEndOfMedia(() -> {
            mp.seek(Duration.ZERO);
        });

        this.gameMap = gameMap;
        this.playerList = playerList;
        this.resX = resX;
        this.resY = resY;
        this.setPrefHeight(resY);
        this.setPrefWidth(resX);

        currentPlayer = playerList.get(0);
        
        //Grant each player initial units if it's a new game
        if (isNewGame) {
            for (Player player : playerList) {
                do {
                    Point p = new Point((int) (Math.random() * gameMap.getSize()), (int) (Math.random() * gameMap.getSize() - 1));
                    if ((gameMap.getTile(p) instanceof Plains || gameMap.getTile(p) instanceof Hills || gameMap.getTile(p) instanceof Desert) && (gameMap.getTile(p.x, p.y + 1) instanceof Plains
                            || gameMap.getTile(p.x, p.y + 1) instanceof Hills || gameMap.getTile(p.x, p.y + 1) instanceof Desert) && !gameMap.getTile(p).hasUnit() && !gameMap.getTile(p.x, p.y + 1).hasUnit()) {
                        player.addUnit(new SettlerUnit(player, p));
                        player.addUnit(new WarriorUnit(player, new Point(p.x, p.y + 1)));
                        //player.startTurn();
                        break;
                    }
                } while (true);
            }
            currentPlayer.startTurn();
        }

        zoomMap = createFalseZoomMap(gameMap.getMap());
        minimap = new Minimap(zoomMap, resX, resY);
        nextButton = new NextTurnPane(currentPlayer, resX, resY, this);
        statusBar = new StatusBarPane(currentPlayer, resX, resY, this);
        sciencePane = new SciencePane(currentPlayer, resX, resY, this);

        getChildren().add(zoomMap);
        getChildren().add(nextButton);
        getChildren().add(statusBar);
        getChildren().add(sciencePane);
        getChildren().add(minimap);

        zoomMap.setCurrentPlayer(currentPlayer);

        setOnMouseClicked((MouseEvent e) -> {
            updateInfo();
        });
    }

    public void nextTurn() {
        if (playerList.indexOf(currentPlayer) == playerList.size() - 1) {
            currentPlayer = playerList.get(0);
        } else {
            currentPlayer = playerList.get(playerList.indexOf(currentPlayer) + 1);
        }

        currentPlayer.startTurn();

        nextButton.setCurrentPlayer(currentPlayer);
        statusBar.setCurrentPlayer(currentPlayer);
        sciencePane.setCurrentPlayer(currentPlayer);
        zoomMap.setCurrentPlayer(currentPlayer);
        
        
        
        if (playerList.size()==1){
            wmp = new MediaPlayer(win);
            wmp.play();
            
            this.getChildren().add(new DefeatedPrompt(resX, resY, false));
        }else if (currentPlayer.isDefeated()){            
            dmp = new MediaPlayer(loss);
            dmp.play();
            
            this.getChildren().add(new DefeatedPrompt(resX, resY, true));
            
        }
    }

    public void jumpToNextAction() {

        if (currentPlayer.canEndTurn() == 1) {
            for (Unit u : currentPlayer.getUnitList()) {
                if (u.canMove()) {

                    //Jump the player to the units that they haven't move yet and force them to make an action
                    zoomMap.jumpTo(u);
                    //deletes all the prevoius menus open
                    for (Node n : zoomMap.getChildren()) {
                        if (n instanceof UnitMenu) {
                            ((UnitMenu) n).delete();
                            break;
                        }
                    }
                    //add the new map and have tiles ready to move
                    UnitMenu uM = new UnitMenu(u, zoomMap);
                    zoomMap.setSelectedTile(gameMap.getTile(u.getX(), u.getY()));
                    uM.setTranslateX(((zoomMap.getLeftCap() - (u.getY() * DisplayTile.WIDTH * getScaleX())) * -1) + (u.getX() % 2 == 0 ? (DisplayTile.getWIDTH() / 2 * getScaleX()) * -1 : 0));
                    uM.setTranslateY(u.getX() * DisplayTile.HEIGHT * getScaleY());
                    zoomMap.setUnitMenu(uM);
                    zoomMap.getChildren().add(uM);
                    return;
                }
            }
        } else if (currentPlayer.canEndTurn() == 2) {
            for (City c : currentPlayer.getCityList()) {
                if (!c.canEndTurn()) {
                    //Jump to the city position, disable dragging
                    zoomMap.jumpTo(c);
                    zoomMap.enableDragging(false);
                    //set the selected tile for later usage
                    zoomMap.setSelectedTile(gameMap.getTile(c.getPosition()));
                    //add a new city map
                    cityMenu = new CityMenu(c, resX, resY, zoomMap);
                    getChildren().add(cityMenu);
                    return;
                }
            }
        }

    }

    public void addCityMenu(City c) {
        cityMenu = new CityMenu(c, resX, resY, zoomMap);
        getChildren().add(cityMenu);
    }

    public void removeCityMenu() {
        if (getChildren().contains(cityMenu)) {
            getChildren().remove(cityMenu);
        }

        updateInfo();
        zoomMap.enableDragging(true);
    }

    public void updateInfo() {
        nextButton.updateText();
        statusBar.updateTexts();
        sciencePane.updateInfo();
    }

    public void updateMinimap() {
        minimap.update(); 
    }

    private ZoomMap createFalseZoomMap(Tile[][] original) {

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

        ZoomMap pane = new ZoomMap(original.length, resX, resY, spareSize, copy);

        return pane;

    }

    public ZoomMap getZoomMap() {
        return zoomMap;
    }

    public ArrayList<Player> getPlayerList() {
        return playerList;
    }
    
    private void removeDefeatedPrompt(GamePane.DefeatedPrompt dp) {
        getChildren().remove(dp);
        
    }
    
    private class DefeatedPrompt extends Pane {

        private Rectangle border;
        private Circle confirmButton;
        private Text title;

        private boolean canConfirm;
        private boolean defeated;

        public DefeatedPrompt(int resX, int resY, boolean defeated) {
            canConfirm = true;
            this.defeated = defeated;

            border = new Rectangle(400, 100);
            border.setFill(new ImagePattern(ImageBuffer.getImage(MiscAsset.CITY_OPTION_BACKGROUND), 0, 0, 1, 1, true));
            border.setStrokeWidth(5);
            border.setStroke(Color.BLACK);

            setTranslateX(resX / 2 - border.getWidth()/2);
            setTranslateY(resY / 2 - border.getHeight()/2);
            
            if(defeated){
                title = new Text("YOU HAVE BEEN DEFEATED");
            }else{
                title = new Text("YOU ARE VICTORIOUS");
            }

            title.setFont(Font.font("OSWALD", 25));
            title.setFill(Color.WHITESMOKE);
            title.setTranslateY(border.getWidth() / 2 - title.getLayoutBounds().getWidth() / 2);
            title.setTranslateX(15);

            confirmButton = new Circle(border.getWidth() - 20, border.getHeight()-20, 14.5);
            confirmButton.setFill(new ImagePattern(ImageBuffer.getImage(MiscAsset.CONFIRM_ICON)));
            confirmButton.setOnMouseClicked((e) -> {
                e.consume();
                close();
                
            });

            getChildren().addAll(border, title, confirmButton);

        }

        private void close() {
            removeDefeatedPrompt(this);
            if(defeated){   
                Player pP = currentPlayer;
                

                playerList.remove(pP);
                statusBar.removeHead(pP);
                nextTurn();
                dmp.pause();
            }else{
                System.out.println("DEWIT");
                wmp.pause();
            }
            
            
        }

    }
    

}
