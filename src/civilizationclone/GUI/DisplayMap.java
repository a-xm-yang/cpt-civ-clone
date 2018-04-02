package civilizationclone.GUI;

import civilizationclone.City;
import civilizationclone.Player;
import civilizationclone.Tile.Tile;
import civilizationclone.Unit.MilitaryUnit;
import civilizationclone.Unit.SettlerUnit;
import civilizationclone.Unit.Unit;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Set;
import javafx.beans.binding.Bindings;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class DisplayMap extends Group {

    //Graphics Related Vars ----
    //<editor-fold>
    private double topCap, bottomCap;
    private double leftCap, rightCap;
    private double orgSceneX, orgSceneY;
    private double orgTranslateX, orgTranslateY;

    private double scale;
    private int mapSize;
    private int resX, resY;
    private int spareSize;
    private double sizeX, sizeY, totalSizeX;
    //</editor-fold>

    private Canvas canvas;
    private ArrayList<DisplayTile> tileList;
    private Point[] highlightedTiles;
    private HighlightType highlightType;
    private UnitMenu unitMenu;

    private GamePane gamePaneRef;

    private Player currentPlayer;
    private Tile selectedTile;

    private static Effect shadow = new DropShadow(30, Color.WHITE);
    private static Effect noneEffect = null;

    //size refers to the number of tiles
    public DisplayMap(int mapSize, int resX, int resY, int spareSize, Tile[][] tileMap, GamePane gamePaneRef) {

        this.gamePaneRef = gamePaneRef;

        highlightType = HighlightType.NONE;
        this.mapSize = mapSize;
        this.resX = resX;
        this.resY = resY;
        this.spareSize = spareSize;

        this.sizeX = mapSize * DisplayTile.getWIDTH();
        this.sizeY = mapSize / 2 * 164 + ((mapSize % 2 == 1) ? 82 : 0);
        this.totalSizeX = sizeX + 2 * getSpareWidth();

        scale = 1;
        tileList = new ArrayList<>();

        canvas = new Canvas(getSizeX() + 2 * (getSpareWidth()), getSizeY());
        DisplayTile.referenceCanvas(canvas);
        canvas.setMouseTransparent(true);

        //set actual objects according to the given falsified tileMap
        //since each display tile has a x-y-coordinate, and the map is a false map to scroll around, we need to adjust the coordinates accoridngly
        double offset = 0;
        for (int i = 0; i < tileMap.length; i++) {
            if (i % 2 == 1) {
                offset = DisplayTile.getWIDTH() / 2;
            } else {
                offset = 0;
            }
            for (int k = 0; k < tileMap[0].length; k++) {
                int fixedY;
                if (k >= spareSize + mapSize) {
                    //if it's the right buffer
                    fixedY = spareSize + mapSize;
                } else if (k >= spareSize) {
                    //if it's middle
                    fixedY = spareSize;
                } else {
                    //if it's left buffer
                    fixedY = (mapSize - spareSize) * -1;
                }
                DisplayTile t = new DisplayTile(tileMap[i][k], i, k - fixedY);
                t.setTranslateY(DisplayTile.getHEIGHT() * i);
                t.setTranslateX(DisplayTile.getWIDTH() * k + offset);
                getChildren().add(t);
                tileList.add(t);
            }
        }

        getChildren().add(canvas);

        //handler for resizing the map (Disabled for now)
        setOnScroll((ScrollEvent event) -> {
            //(event);
        });

        //handler for actions (incomplete)
        setOnMouseClicked((MouseEvent event) -> {
            clickEventHandling(event);
        });

        setOnMousePressed(onMousePressedEventHandler);
        setOnMouseDragged(onMouseDraggedEventHandler);
        setOnMouseReleased(e -> {
            //reset cursor
            setCursor(Cursor.DEFAULT);
        });

        calculateBounds();
        repaint();
    }

    private void clickEventHandling(MouseEvent e) {

        //failsafe mechanism so that you don't end up triggering this click event untentionally or when you're not suposed to
        if (e.getTarget() instanceof DisplayTile) {
            if (((DisplayTile) e.getTarget()).getAccessLevel() != 2) {
                e.consume();
                return;
            }
        } else {
            e.consume();
            return;
        }

        //Remove previous unit menu
        getChildren().remove(unitMenu);
        unitMenu = null;

        //in case it doesn't exactly hit on a tile (which indeed happens)
        Tile clickedTile;
        try {
            clickedTile = ((DisplayTile) e.getTarget()).getTile();
        } catch (Exception exception) {
            return;
        }

        //testing for only movement highlight
        if (highlightType == HighlightType.NONE) {
            if (clickedTile.hasUnit()) {
                unitMenu = new UnitMenu(clickedTile.getUnit(), this);
                unitMenu.setTranslateX(((DisplayTile) e.getTarget()).getTranslateX());
                unitMenu.setTranslateY(((DisplayTile) e.getTarget()).getTranslateY());
                this.getChildren().add(unitMenu);
                selectedTile = clickedTile;
            } else if (clickedTile.hasCity() && clickedTile.getCity().getPlayer() == currentPlayer && !clickedTile.hasUnit()) {
                gamePaneRef.addCityMenu(clickedTile.getCity());
                enableDragging(false);
                selectedTile = clickedTile;
            }
        } else {

            Point clickPoint = ((DisplayTile) e.getTarget()).getPoint();

            //Different types of highlights
            if (highlightType == highlightType.MOVEMENT) {
                for (Point p : highlightedTiles) {
                    if (clickPoint.equals(p)) {
                        Unit unit = selectedTile.getUnit();
                        gamePaneRef.requestAction(unit.getPlayer().getName()
                                + "/" + "Unit"
                                + "/" + unit.hashCode()
                                + "/" + "Move"
                                + "/" + p.getX()
                                + "/" + p.getY());
                    }
                }
            }
            //Attack Highlight
            if (highlightType == highlightType.ATTACK) {
                for (Point p : highlightedTiles) {
                    if (clickPoint.equals(p)) {

                        MilitaryUnit m = (MilitaryUnit) selectedTile.getUnit();

                        gamePaneRef.requestAction(m.getPlayer().getName()
                                + "/" + "Unit"
                                + "/" + m.hashCode()
                                + "/" + "Attack"
                                + "/" + p.getX()
                                + "/" + p.getY());
                    }
                }
            }
            //Expansion Highlight
            if (highlightType == highlightType.EXPANSION) {
                for (Point p : highlightedTiles) {
                    if (clickPoint.equals(p)) {
                        City c = selectedTile.getCity();
                        gamePaneRef.requestAction(c.getPlayer().getName()
                                + "/" + "City"
                                + "/" + c.hashCode()
                                + "/" + "Expand"
                                + "/" + p.getX()
                                + "/" + p.getY());
                    }
                }
            }

            //reset the highlight status
            selectedTile = null;
            highlightType = HighlightType.NONE;
            cleanHighlight();
        }

        //e.consume();
        repaint();
    }

    public void jumpTo(Unit u) {
        //Snaps the screen to the position of the unit
        setTranslateX(leftCap - (u.getY() * DisplayTile.WIDTH * getScaleX()) + resX / 2 * getScaleX());
        setTranslateY((u.getX() * DisplayTile.HEIGHT * getScaleY()) * -1 + resY / 2 * getScaleY());
        adjustPosition();

    }

    public void jumpTo(City c) {
        //Snaps the screen to the position of the city
        setTranslateX(leftCap - (c.getPosition().y * DisplayTile.WIDTH * getScaleX()) + resX / 2 * getScaleX());
        setTranslateY((c.getPosition().x * DisplayTile.HEIGHT * getScaleY()) * -1 + resY / 2 * getScaleY());
        adjustPosition();

    }

    public void setCurrentPlayer(Player currentPlayer) {
        //update fog of war according to the player, and center the screen around that player
        this.currentPlayer = currentPlayer;
//        updateFogOfWar();

        //Jump new player to their location on map
        if (currentPlayer.getCityList().size() > 0) {
            jumpTo(currentPlayer.getCityList().get(0));
        } else if (currentPlayer.getUnitList().size() > 0) {
            jumpTo(currentPlayer.getUnitList().get(0));
        }

        adjustPosition();

        //clean unit menus opened by the last player
        getChildren().remove(unitMenu);
        unitMenu = null;
    }

    public void activateAttack() {
        //Highlights attackable tiles and makes the clickable for an attack
        highlightType = HighlightType.ATTACK;

        MilitaryUnit m = (MilitaryUnit) selectedTile.getUnit();
        ArrayList<Point> combinedList = new ArrayList<Point>();

        for (Point p : m.getAttackable()) {
            combinedList.add(p);
        }

        for (Point p : m.getSiegable()) {
            combinedList.add(p);
        }

        addHighlightedTiles(combinedList.toArray(new Point[combinedList.size()]));
        repaint();
    }

    public void activateMove() {
        //Highlights tiles than can be moved to
        highlightType = HighlightType.MOVEMENT;
        addHighlightedTiles(selectedTile.getUnit().getMoves());
        repaint();
    }

    public void activateSettle() {
        //Settle
        enableDragging(false);
        getChildren().add(new SettlePrompt((SettlerUnit) selectedTile.getUnit(), resX, resY, getTranslateX(), getTranslateY()));
        repaint();
    }

    private void removeSettlePrompt(SettlePrompt sp) {
        getChildren().remove(sp);
        enableDragging(true);
        repaint();
    }

    public void activateFortify() {
        //Removes all movement and fortifies unit
        if (selectedTile.getUnit() instanceof MilitaryUnit) {
            MilitaryUnit m = (MilitaryUnit) selectedTile.getUnit();
            gamePaneRef.requestAction(m.getPlayer().getName()
                    + "/" + "Unit"
                    + "/" + m.hashCode()
                    + "/" + "Fortify");
        }
    }

    public void activateKill() {
        //Kills the unit
        Unit unit = selectedTile.getUnit();
        gamePaneRef.requestAction(unit.getPlayer().getName()
                + "/" + "Unit"
                + "/" + unit.hashCode()
                + "/" + "Kill");
    }

    public void activateExpansion() {
        highlightType = HighlightType.EXPANSION;

        //activate possible highlights for expansion
        if (selectedTile != null) {
            addHighlightedTiles(selectedTile.getCity().getPossibleExpansion());
        }

        repaint();
    }

    public void activateEndTurn() {
        Unit u = selectedTile.getUnit();
        gamePaneRef.requestAction(u.getPlayer().getName()
                + "/" + "Unit"
                + "/" + u.hashCode()
                + "/" + "EndTurn");
    }

    public void activateBuild(String build) {

        Unit selectedUnit = selectedTile.getUnit();

        gamePaneRef.requestAction(selectedUnit.getPlayer().getName()
                + "/" + "Unit"
                + "/" + selectedUnit.hashCode()
                + "/" + "Build"
                + "/" + build);
    }

    public void updateInfo() {
        Set<Tile> visibleTiles = Unit.getMapRef().getVisibleTiles(currentPlayer.getAllPositions());
        Set<Tile> exploredTiles = currentPlayer.getExploredTiles();

        for (DisplayTile dt : tileList) {
            Tile t = dt.getTile();
            if (visibleTiles.contains(t)) {
                dt.setAccessLevel(2);
            } else if (exploredTiles.contains(t)) {
                dt.setAccessLevel(1);
            } else {
                dt.setAccessLevel(0);
            }
        }

        repaint();
    }

    public void repaint() {
        canvas.getGraphicsContext2D().clearRect(0, 0, totalSizeX, sizeY);
        //paint according to fog of war
        for (DisplayTile t : tileList) {
            t.update();
        }
    }

    public void addHighlightedTiles(Point[] possibleOptions) {
        //Adds the highlight for the tiles
        highlightedTiles = possibleOptions;

        for (Point p : possibleOptions) {
            for (DisplayTile t : tileList) {
                if (t.getX() == p.x && t.getY() == p.y) {
                    t.setHighlighted(true);
                }
            }
        }
    }

    public void cleanHighlight() {
        //Removes the highlights from the tiles
        highlightType = HighlightType.NONE;
        for (DisplayTile t : tileList) {
            t.setHighlighted(false);
        }
    }

    public void enableDragging(boolean enable) {
        //disable all listeners
        if (!enable) {
            setOnMousePressed(null);
            setOnMouseDragged(null);
            setOnMouseClicked(null);
        } else {
            setOnMousePressed(onMousePressedEventHandler);
            setOnMouseDragged(onMouseDraggedEventHandler);
            setOnMouseClicked((MouseEvent event) -> {
                clickEventHandling(event);
            });
        }

        repaint();
    }

    //handlers for scrolling ---------------
    //Removed from final game, to be readded eventually
    //<editor-fold>
    private void calculateBounds() {

        //the Y-traslation that would hit the top bottom border of the map
        topCap = (getScale() - 1) * getSizeY() * 0.5;
        bottomCap = getSizeY() * (-0.5 * getScale() - 0.5) + getResY();

        //the X-translation that would hit the boundary between the real map and the fake extension map
        leftCap = (getSizeX() / 2 + getSpareWidth()) * (getScale() - 1) - getSpareWidth() * getScale();
        rightCap = (getSizeX() / 2 + getSpareWidth()) * (getScale() - 1) - (getSpareWidth() + getSizeX()) * getScale();
    }

    private void adjustPosition() {

        //make sure the screen is in bounds after what happened
        if (getTranslateY() > topCap) {
            setTranslateY(topCap);
        } else if (getTranslateY() < bottomCap) {
            setTranslateY(bottomCap);
        }

        if (getTranslateX() >= leftCap + getResX()) {
            setTranslateX(rightCap + getResX());
        } else if (getTranslateX() <= rightCap) {
            setTranslateX(leftCap);
        }
    }
    //</editor-fold>

    //GETTERS AND SETTERS
    //<editor-fold>
    public GamePane getGamePaneRef() {
        return gamePaneRef;
    }

    public void setUnitMenu(UnitMenu unitMenu) {
        this.unitMenu = unitMenu;
    }

    public UnitMenu getUnitMenu() {
        return unitMenu;
    }

    public void setSelectedTile(Tile selectedTile) {
        this.selectedTile = selectedTile;
    }

    public ArrayList<DisplayTile> getTileList() {
        return tileList;
    }

    public int getMapSize() {
        return mapSize;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public HighlightType getHighlightType() {
        return highlightType;
    }

    public void setHighlightType(HighlightType highlightType) {
        this.highlightType = highlightType;
    }

    public double getSizeX() {
        return sizeX;
    }

    public double getSizeY() {
        return sizeY;
    }

    public int getResX() {
        return resX;
    }

    public int getResY() {
        return resY;
    }

    public double getTileWidth() {
        return DisplayTile.WIDTH;
    }

    public double getScale() {
        return scale;
    }

    public double getSpareSize() {
        return spareSize;
    }

    public double getSpareWidth() {
        return spareSize * DisplayTile.getWIDTH() + DisplayTile.getWIDTH() / 2;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public double getTopCap() {
        return topCap;
    }

    public double getBottomCap() {
        return bottomCap;
    }

    public double getLeftCap() {
        return leftCap;
    }

    public double getRightCap() {
        return rightCap;
    }
    //</editor-fold>

    EventHandler<MouseEvent> onMousePressedEventHandler
            = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent t) {
            orgSceneX = t.getSceneX();
            orgSceneY = t.getSceneY();

            orgTranslateX = getTranslateX();
            orgTranslateY = getTranslateY();
        }
    };

    EventHandler<MouseEvent> onMouseDraggedEventHandler
            = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent t) {

            double offsetX = t.getSceneX() - orgSceneX;
            double offsetY = t.getSceneY() - orgSceneY;
            double newTranslateX = orgTranslateX + offsetX;
            double newTranslateY = orgTranslateY + offsetY;

            if (newTranslateY < topCap && newTranslateY > bottomCap) {
                setTranslateY(newTranslateY);
            }

            if (newTranslateX >= leftCap + getResX()) {
                setTranslateX(rightCap + getResX());
            } else if (newTranslateX <= rightCap) {
                setTranslateX(leftCap);
            } else {
                setTranslateX(newTranslateX);
            }

            setCursor(Cursor.MOVE);
        }

    };

    private class SettlePrompt extends Pane {

        private Rectangle border;
        private Circle closeButton, confirmButton;
        private TextField textField;
        private Text title;

        private SettlerUnit selectedUnit;
        private boolean canConfirm;

        public SettlePrompt(SettlerUnit selectedUnit, int resX, int resY, double zoomMapX, double zoomMapY) {
            this.selectedUnit = selectedUnit;
            canConfirm = true;

            border = new Rectangle(300, 150);
            border.setFill(new ImagePattern(ImageBuffer.getImage(MiscAsset.CITY_OPTION_BACKGROUND), 0, 0, 1, 1, true));
            border.setStrokeWidth(5);
            border.setStroke(Color.BLACK);

            setTranslateX(zoomMapX * -1 + resX / 2 - border.getWidth() / 2);
            setTranslateY(zoomMapY * -1 + resY / 2 - border.getHeight() / 2);

            title = new Text("INPUT CITY NAME");
            title.setFont(Font.font("Times New Roman", 20));
            title.setFill(Color.WHITESMOKE);
            title.setTranslateY(25);
            title.setTranslateX(5);

            textField = new TextField(getDefaultText());
            textField.setAlignment(Pos.CENTER);
            textField.setFont(Font.font("Times New Roman", 18));
            textField.setPrefColumnCount(12);
            textField.setPrefHeight(20);
            textField.setTranslateX(40);
            textField.setTranslateY(40);
            textField.textProperty().addListener((observable, oldValue, newValue) -> {
                textCheck();
            });

            confirmButton = new Circle(30, 120, 14.5);
            confirmButton.setFill(new ImagePattern(ImageBuffer.getImage(MiscAsset.CONFIRM_ICON)));
            confirmButton.setOnMouseClicked((e) -> {
                e.consume();
                if (canConfirm) {
                    confirm();
                    close();
                }
            });
            confirmButton.effectProperty().bind(
                    Bindings.when(confirmButton.hoverProperty()).then(shadow).otherwise(noneEffect)
            );

            closeButton = new Circle(80, 120, 20);
            closeButton.setFill(new ImagePattern(ImageBuffer.getImage(MiscAsset.CLOSE_ICON)));
            closeButton.setOnMouseClicked((e) -> {
                e.consume();
                close();
            });
            closeButton.effectProperty().bind(
                    Bindings.when(closeButton.hoverProperty()).then(shadow).otherwise(noneEffect)
            );

            getChildren().addAll(border, closeButton, title, confirmButton, textField);

        }
        
        private String getDefaultText(){
            Player p = selectedUnit.getPlayer();
            String[] defaultNames = p.getLeader().getCityNames();
            
            for (String s: defaultNames){
                boolean chosen = false;
                for (City c: p.getCityList()){
                    if (s.equalsIgnoreCase(c.getName())){
                        chosen = true;
                        break;
                    }
                }
                if (!chosen){
                    return s;
                }
            }
            
            return "City";
        }

        private void textCheck() {
            if (textField.getCharacters().toString().length() > 0 && textField.getCharacters().toString().length() <= 15) {
                canConfirm = true;
                confirmButton.setOpacity(1);
            } else {
                canConfirm = false;
                confirmButton.setOpacity(0.25);
            }
        }

        private void confirm() {
            gamePaneRef.requestAction(selectedUnit.getPlayer().getName()
                    + "/" + "Unit"
                    + "/" + selectedUnit.hashCode()
                    + "/" + "Settle"
                    + "/" + textField.getCharacters().toString());
        }

        private void close() {
            removeSettlePrompt(this);
        }

    }

}

enum HighlightType {
    MOVEMENT, ATTACK, EXPANSION, NONE;
}
