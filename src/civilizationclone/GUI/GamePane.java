package civilizationclone.GUI;

import civilizationclone.*;
import civilizationclone.Tile.*;
import civilizationclone.Unit.SettlerUnit;
import civilizationclone.Unit.UnitType;
import civilizationclone.Unit.WarriorUnit;
import java.awt.Point;
import java.util.ArrayList;
import javafx.scene.layout.Pane;

public class GamePane extends Pane {

    //A pane class that handles everything that is game-related
    private int resX, resY;
    private ZoomMap zoomMap;
    private CityMenu cityMenu;

    //game data
    private GameMap gameMap;
    private ArrayList<Player> playerList;
    private Player currentPlayer;

    public GamePane(ArrayList<Player> playerList, int resX, int resY) {

        this.playerList = playerList;
        this.resX = resX;
        this.resY = resY;
        this.setPrefHeight(resY);
        this.setPrefWidth(resX);

        gameMap = new GameMap(GameMap.MapSize.SMALL, 400);
        currentPlayer = playerList.get(0);
        //Reset these later to just settler and warrior
        for(Player p: playerList){
            p.addUnit(new SettlerUnit(playerList.get(0), new Point(12, 12)));
            p.addUnit(new WarriorUnit(playerList.get(0), new Point(12, 13)));
        }
      
        //playerList.get(0).addUnit(new BuilderUnit(playerList.get(0).getCityList().get(0)));
        playerList.get(0).startTurn();
        playerList.get(0).addTech(TechType.AGRICULTURE);
        playerList.get(0).setCurrentGold(100);

        zoomMap = createFalseMap(gameMap.getMap());
        zoomMap.setCurrentPlayer(currentPlayer);
        getChildren().add(zoomMap);
    }

    public void addCityMenu(City c) {
        cityMenu = new CityMenu(c, resX, resY, zoomMap);
        getChildren().add(cityMenu);
    }

    public void removeCityMenu() {
        if (getChildren().contains(cityMenu)) {
            getChildren().remove(cityMenu);
        }
        cityMenu = null;

        zoomMap.enableDragging(true);
    }

    private ZoomMap createFalseMap(Tile[][] original) {

        //creates a copy of the actual tile map, with the sides adjusted according to X-resolution to a copied version for scrolling effect
        //first calculate how much spare is needed on both sides, should be according to scene size
        int spareSize = resX / 100 + 1;

        //original = new GameMap(GameMap.MapSize.SMALL, 1).getMap();
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

        ZoomMap pane = new ZoomMap(40, resX, resY, spareSize, copy);

        return pane;

    }

    public ZoomMap getZoomMap() {
        return zoomMap;
    }
}
