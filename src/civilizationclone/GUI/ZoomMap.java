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
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ZoomMap extends Group {

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

    private Player currentPlayer;
    private Tile selectedTile;

    private Set<Tile> visibleTiles;
    private Set<Tile> exploredTiles;

    //size refers to the number of tiles
    public ZoomMap(int mapSize, int resX, int resY, int spareSize, Tile[][] tileMap) {

        super();

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

        //handler for resizing the map
        setOnScroll((ScrollEvent event) -> {
            resize(event);
        });

        //handler for actions (incomplete)
        setOnMouseClicked((MouseEvent event) -> {
            clickEventHandling(event);
        });

        setOnMousePressed(onMousePressedEventHandler);
        setOnMouseDragged(onMouseDraggedEventHandler);

        calculateBounds();
        repaint();
    }

    private void clickEventHandling(MouseEvent e) {

        if (e.getTarget() instanceof DisplayTile) {
            if (((DisplayTile) e.getTarget()).getAccessLevel() != 2) {
                e.consume();
                return;
            }
        } else {
            e.consume();
            return;
        }

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
                UnitMenu u = new UnitMenu(clickedTile.getUnit(), this);
                u.setTranslateX(((DisplayTile) e.getTarget()).getTranslateX());
                u.setTranslateY(((DisplayTile) e.getTarget()).getTranslateY());
                this.getChildren().add(u);
                selectedTile = clickedTile;
            } else if (clickedTile.hasCity() && clickedTile.getCity().getPlayer() == currentPlayer && !clickedTile.hasUnit()) {
                ((GamePane) getParent()).addCityMenu(clickedTile.getCity());
                enableDragging(false);
                selectedTile = clickedTile;
            }
        } else {

            Point clickPoint = ((DisplayTile) e.getTarget()).getPoint();

            //if movement is desired
            if (highlightType == highlightType.MOVEMENT) {
                for (Point p : highlightedTiles) {
                    if (clickPoint.equals(p)) {
                        selectedTile.getUnit().move(p);
                        updateFogOfWar();
                    }
                }
            }

            if (highlightType == highlightType.ATTACK) {
                for (Point p : highlightedTiles) {
                    if (clickPoint.equals(p)) {

                        MilitaryUnit m = (MilitaryUnit) selectedTile.getUnit();

                        if (Unit.getMapRef().getTile(p).hasUnit()) {
                            m.attack(Unit.getMapRef().getTile(p).getUnit());
                        } else {
                            m.siegeAttack(Unit.getMapRef().getTile(p).getCity());
                        }

                        updateFogOfWar();
                    }
                }
            }

            if (highlightType == highlightType.EXPANSION) {
                for (Point p : highlightedTiles) {
                    if (clickPoint.equals(p)) {
                        selectedTile.getCity().addTile(clickedTile);
                        updateFogOfWar();
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

        setTranslateX(leftCap - (u.getY() * DisplayTile.WIDTH * getScaleX()) + resX / 2 * getScaleX());
        setTranslateY((u.getX() * DisplayTile.HEIGHT * getScaleY()) * -1 + resY / 2 * getScaleY());
        adjustPosition();

    }

    public void jumpTo(City c) {

        setTranslateX(leftCap - (c.getPosition().y * DisplayTile.WIDTH * getScaleX()) + resX / 2 * getScaleX());
        setTranslateY((c.getPosition().x * DisplayTile.HEIGHT * getScaleY()) * -1 + resY / 2 * getScaleY());
        adjustPosition();

    }

    public void setCurrentPlayer(Player currentPlayer) {
        //update fog of war according to the player, and center the screen around that player
        this.currentPlayer = currentPlayer;
        updateFogOfWar();

        //reset scaling for new player
        if (currentPlayer.getCityList().size() > 0) {
            jumpTo(currentPlayer.getCityList().get(0));
        } else if (currentPlayer.getUnitList().size() > 0) {
            jumpTo(currentPlayer.getUnitList().get(0));
        }

        adjustPosition();
    }

    private void updateFogOfWar() {

        visibleTiles = Unit.getMapRef().getVisibleTiles(currentPlayer.getAllPositions());
        currentPlayer.addExploredTiles(visibleTiles);
        exploredTiles = currentPlayer.getExploredTiles();

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

        ((GamePane) getParent()).updateMinimap();
        repaint();

    }

    public void activateAttack() {
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
        highlightType = HighlightType.MOVEMENT;
        addHighlightedTiles(selectedTile.getUnit().getMoves());
        repaint();
    }

    public void activateSettle() {
        enableDragging(false);
        getChildren().add(new SettlePrompt((SettlerUnit) selectedTile.getUnit(), resX, resY, getTranslateX(), getTranslateY()));
        repaint();
    }

    public void removeSettlePrompt(SettlePrompt sp) {
        getChildren().remove(sp);
        enableDragging(true);
        updateFogOfWar();
        repaint();
    }

    public void activateHeal() {
        if (selectedTile.getUnit() instanceof MilitaryUnit) {
            ((MilitaryUnit) selectedTile.getUnit()).heal();
        }

        repaint();
    }

    public void activateKill() {
        selectedTile.getUnit().delete();
        updateFogOfWar();
        repaint();
    }

    public void activateDestroy() {
        //fucked up, fix later
        repaint();
    }

    public void activateExpansion() {
        highlightType = HighlightType.EXPANSION;

        //activate possible highlights for expansion
        if (selectedTile != null) {
            addHighlightedTiles(selectedTile.getCity().getPossibleExpansion());
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
    //<editor-fold>
    private void resize(ScrollEvent event) {
        if (event.getDeltaY() == 0) {
            return;
        }

        if (event.getDeltaY() > 0) {
            if (scale < 5) {
                scale++;
            }
        } else {
            if (scale > 1) {
                scale--;
                setTranslateY(getTranslateY() + getSizeY() / 2);
            }
        }

        this.setScaleX(scale);
        this.setScaleY(scale);

        calculateBounds();
        adjustPosition();

    }

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

            setTranslateX(zoomMapX * -1 + resX / 2 - border.getX() / 2 - border.getX() / 2);
            setTranslateY(zoomMapY * -1 + resY / 2 - border.getY() / 2 - border.getY() / 2);

            closeButton = new Circle(280, 120, 20);
            closeButton.setFill(new ImagePattern(ImageBuffer.getImage(MiscAsset.CLOSE_ICON)));
            closeButton.setOnMouseClicked((e) -> {
                e.consume();
                close();
            });

            title = new Text("INPUT CITY NAME");
            title.setFont(Font.font("Times New Roman", 20));
            title.setFill(Color.WHITESMOKE);
            title.setTranslateY(25);
            title.setTranslateX(5);

            textField = new TextField("City");
            textField.setAlignment(Pos.CENTER);
            textField.setFont(Font.font("Times New Roman", 18));
            textField.setPrefColumnCount(12);
            textField.setPrefHeight(20);
            textField.setTranslateX(40);
            textField.setTranslateY(40);
            textField.textProperty().addListener((observable, oldValue, newValue) -> {
                textCheck();
            });

            confirmButton = new Circle(230, 120, 14.5);
            confirmButton.setFill(new ImagePattern(ImageBuffer.getImage(MiscAsset.CONFIRM_ICON)));
            confirmButton.setOnMouseClicked((e) -> {
                e.consume();
                if (canConfirm) {
                    confirm();
                    close();
                }
            });

            getChildren().addAll(border, closeButton, title, confirmButton, textField);

        }

        private void textCheck() {
            if (textField.getCharacters().toString().length() > 0 && textField.getCharacters().toString().length() <= 12) {
                canConfirm = true;
                confirmButton.setOpacity(1);
            } else {
                canConfirm = false;
                confirmButton.setOpacity(0.25);
            }
        }

        private void confirm() {
            selectedUnit.settle(textField.getCharacters().toString());
            if (getParent().getParent() instanceof GamePane) {
                ((GamePane) getParent().getParent()).updateInfo();
            }
        }

        private void close() {
            removeSettlePrompt(this);
        }

    }

}

enum HighlightType {
    MOVEMENT, ATTACK, EXPANSION, NONE;
}
