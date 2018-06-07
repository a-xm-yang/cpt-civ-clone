package civilizationclone.GUI;

import civilizationclone.Tile.*;
import civilizationclone.Unit.MilitaryUnit;
import java.awt.Point;
import java.util.ArrayList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;

public class DisplayTile extends Polygon {

    //member variables
    //<editor-fold>
    //Graphic-related members
    private static Canvas canvas;
    private static GraphicsContext gc;
    private static ColorAdjust shade;
    private static ImagePattern cloudFill;
    private ImagePattern tileFill;
    private Image resourceImage;
    private boolean highlighted = false;
    private static boolean displayColor = false;
    private static boolean displayOutput = false;
    private static boolean displayResourse = true;
    //Data-related members
    private int x;
    private int y;
    private Tile tile;
    private int accessLevel;
    //</editor-fold>

    static final double WIDTH = 102;
    static final double HEIGHT = 82;

    public DisplayTile(Tile tile, int x, int y) {
        //each display tile of hexagon is an object

        //tile reference
        this.tile = tile;
        this.x = x;
        this.y = y;

        accessLevel = 2;

        setStroke(Color.BLACK);

        getPoints().addAll(new Double[]{50.0, 0.0, 100.0, 30.0, 100.0, 80.0, 50.0, 110.0, 0.0, 80.0, 0.0, 30.0});
        tileFill = new ImagePattern(ImageBuffer.getImage(tile));

        if (tile.getResource() != Resource.NONE) {
            resourceImage = ImageBuffer.getImage(tile.getResource());
        }

        setStrokeWidth(3);

        hoverProperty().addListener((observable) -> {
            if (accessLevel > 0) {
                if (isHover()) {
                    setStroke(Color.BISQUE);
                } else {
                    update();
                }
            }
        });
    }

    public void update() {

        //UPDATE ACCORDING TO FOG OF WAR ACCESS
        //Access level 0: Complete Coverage
        //Access level 1: Show terrain, city, and resource
        //Access level 2: Show everything

        if (accessLevel == 0) {
            setFill(cloudFill);
            setEffect(null);
            setStroke(Color.LIGHTGRAY);
        } else {

            setFill(tileFill);
            setStroke(Color.BLACK);

            if (tile.hasCity()) {
                gc.drawImage(ImageBuffer.getCityImage(), 5 + getTranslateX(), 10 + getTranslateY());
            }

            if (resourceImage != null && !tile.hasCity() && displayResourse) {
                gc.drawImage(resourceImage, getTranslateX() + 55, getTranslateY() + 35);
            }

            if (accessLevel == 1) {
                setEffect(shade);
            } else {
                if (tile.hasCity() && tile.getCity().getHealth() != tile.getCity().getMaxHealth()) {
                    gc.setFill(Color.BLACK);

                    gc.fillRect(25 + getTranslateX(), 80 + getTranslateY(), 50, 5);
                    if ((double) (tile.getCity().getHealthPercentage()) >= .6) {
                        gc.setFill(Color.LAWNGREEN);
                    } else if ((double) (tile.getCity().getHealthPercentage()) >= .3) {
                        gc.setFill(Color.YELLOW);
                    } else {
                        gc.setFill(Color.RED);
                    }
                    gc.fillRect(25 + getTranslateX(), 80 + getTranslateY(), 50 * ((double) tile.getCity().getHealthPercentage()), 5);
                }

                //FULL ACCESS
                if (tile.getImprovement() != Improvement.NONE) {
                    gc.drawImage(ImageBuffer.getImage(tile.getImprovement()), 5 + getTranslateX(), 20 + getTranslateY());
                }
                if (tile.hasUnit()) {

                    //Draw Circle to display whos unit it is
                    if (displayColor) {
                        gc.setFill(new Color(tile.getUnit().getPlayer().getColor().getRed(), tile.getUnit().getPlayer().getColor().getGreen(), tile.getUnit().getPlayer().getColor().getBlue(), .4));
                        gc.fillOval(15 + getTranslateX(), 20 + getTranslateY(), 70, 70);
                    }
                    //Draw Unit
                    gc.drawImage(ImageBuffer.getImage(tile.getUnit()), 15 + getTranslateX(), 20 + getTranslateY());

                    //Draw health bar if required
                    if (tile.getUnit() instanceof MilitaryUnit && ((MilitaryUnit) tile.getUnit()).getHealth() != ((MilitaryUnit) tile.getUnit()).getMAX_HEALTH()) {
                        gc.setFill(Color.BLACK);
                        gc.fillRect(25 + getTranslateX(), 80 + getTranslateY(), 50, 5);
                        if ((double) ((MilitaryUnit) tile.getUnit()).getHealthPercentage() >= .6) {
                            gc.setFill(Color.LAWNGREEN);
                        } else if ((double) ((MilitaryUnit) tile.getUnit()).getHealthPercentage() >= .3) {
                            gc.setFill(Color.YELLOW);
                        } else {
                            gc.setFill(Color.RED);
                        }
                        gc.fillRect(25 + getTranslateX(), 80 + getTranslateY(), 50 * ((double) ((MilitaryUnit) tile.getUnit()).getHealthPercentage()), 5);
                    }
                }

                //shows the color of the player that controls it
                if (tile.isControlled()) {
                    if (canvas.getParent() instanceof DisplayMap) {
                        setStroke(tile.getControllingCity().getPlayer().getColor());
                    }
                }

                setEffect(null);
            }

            if (displayOutput) {
                dispayYields(gc);
            }
        }

        //will be changed later
        if (highlighted) {
            setStroke(Color.RED);
        }
    }

    public void dispayYields(GraphicsContext gc) {
        ArrayList<Image> images = ImageBuffer.getYieldIcons(tile);

        for (int i = 0; i < images.size(); i++) {
            gc.drawImage(images.get(i), getTranslateX() + i * 25, getTranslateY() + 8);
        }

    }

    public void setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
    }

    //GETTERS & EQUAL METHODS
    //<editor-fold>
    public void setResourceImage(Image resourceImage) {
        this.resourceImage = resourceImage;
    }

    public int getAccessLevel() {
        return accessLevel;
    }

    public Point getPoint() {
        return new Point(x, y);
    }

    public static double getWIDTH() {
        return WIDTH;
    }

    public static double getHEIGHT() {
        return HEIGHT;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Tile getTile() {
        return tile;
    }

    public static boolean isDisplayColor() {
        return displayColor;
    }

    public static boolean isDisplayOutput() {
        return displayOutput;
    }

    public static boolean isDisplayResourse() {
        return displayResourse;
    }

    public static void setDisplayColor(boolean displayColor) {
        DisplayTile.displayColor = displayColor;
    }

    public static void setDisplayOutput(boolean displayOutput) {
        DisplayTile.displayOutput = displayOutput;
    }

    public static void setDisplayResourse(boolean displayResourse) {
        DisplayTile.displayResourse = displayResourse;
    }

    @Override
    public boolean equals(Object o) {

        if (o instanceof DisplayTile) {
            if (x == ((DisplayTile) o).getX() && y == ((DisplayTile) o).getY()) {
                return true;
            }
            return false;
        }

        if (o instanceof Tile) {
            if (this.getTile() == o) {
                return true;
            }
        }

        return false;

    }

    //Override hashcode for a unique set
    //</editor-fold>
    public static void referenceCanvas(Canvas ref) {
        canvas = ref;
        gc = canvas.getGraphicsContext2D();
    }

    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
    }

    //set fog of war cloud and shade to a static value
    static {
        shade = new ColorAdjust();
        shade.setBrightness(-0.75);

        cloudFill = new ImagePattern(ImageBuffer.getImage(MiscAsset.CLOUD));
    }
}
