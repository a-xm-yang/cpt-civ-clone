package civilizationclone.GUI;

import civilizationclone.Tile.*;
import java.util.ArrayList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineJoin;

public class Minimap extends Pane {

    private ArrayList<DisplayTile> tiles;
    private Rectangle border;
    private int mapSize;
    private Canvas canvas;
    private GraphicsContext g;
    private ToggleButton colorButton;
    private ToggleButton resourceButton;
    private ToggleButton outputButton;
    private ZoomMap zoomMap;

    public Minimap(ZoomMap zoomMap, int resX, int resY) {

        //requires a reference to initialize tiles
        mapSize = zoomMap.getMapSize();
        this.zoomMap = zoomMap;

        border = new Rectangle(310, 310);
        border.setFill(Color.BEIGE);
        border.setStrokeWidth(8);
        border.setStroke(Color.DARKKHAKI);
        border.setStrokeLineJoin(StrokeLineJoin.ROUND);

        canvas = new Canvas(300, 300);
        g = canvas.getGraphicsContext2D();
        canvas.setTranslateX(5);
        canvas.setTranslateY(5);

        setTranslateX(resX - border.getWidth());
        setTranslateY(resY - border.getHeight());

        //initialize tiles list
        ArrayList<DisplayTile> temp = zoomMap.getTileList();
        tiles = new ArrayList<>();
        
        colorButton = new ToggleButton(Type.COLOR);
        resourceButton = new ToggleButton(Type.RESOURCE);
        outputButton = new ToggleButton(Type.OUTPUT);
        
        colorButton.setTranslateX(-30);
        colorButton.setTranslateY(160);
        
        resourceButton.setTranslateX(-30);
        resourceButton.setTranslateY(220);
        
        outputButton.setTranslateX(-30);
        outputButton.setTranslateY(280);
        

        for (DisplayTile t : temp) {
            if (!tiles.contains(t)) {
                tiles.add(t);
            }
        }

        getChildren().addAll(border, canvas, colorButton, resourceButton, outputButton);
    }

    public void update() {

        g.clearRect(0, 0, 300, 300);

        double height = (canvas.getHeight() / mapSize * 2.0) / 3.0 * 2;
        double width = canvas.getWidth() / (mapSize + 0.5);

        for (DisplayTile dt : tiles) {

            Tile t = dt.getTile();
            int i = dt.getX();
            int k = dt.getY();

            if (dt.getAccessLevel() == 0) {
                g.setFill(Color.BEIGE);
            } else {

                if (t instanceof Ocean) {
                    g.setFill(Color.BLUE);
                } else if (t instanceof Plains) {
                    g.setFill(Color.GREEN);
                } else if (t instanceof Hills) {
                    g.setFill(Color.GRAY);
                } else if (t instanceof Desert) {
                    g.setFill(Color.YELLOW);
                } else {
                    g.setFill(Color.BLACK);
                }
                
                if (t.hasCity()) {
                    g.setFill(Color.CORAL);
                }

                if (dt.getAccessLevel() == 2) {
                    if (t.hasUnit()) {
                        g.setFill(Color.RED);
                    } 
                }
            }

            if (dt.getX() % 2 == 0) {
                g.fillPolygon(new double[]{k * width + width / 2, k * width + width, k * width + width, k * width + width / 2, k * width, k * width}, new double[]{i * (height * 3 / 4), i * (height * 3 / 4) + height / 4, i * (height * 3 / 4) + (height * 3 / 4), i * (height * 3 / 4) + height, i * (height * 3 / 4) + (height * 3 / 4), i * (height * 3 / 4) + height / 4}, 6);
            } else {
                g.fillPolygon(new double[]{k * width + width, k * width + width * 1.5, k * width + width * 1.5, k * width + width, k * width + width / 2, k * width + width / 2}, new double[]{i * (height * 3 / 4), i * (height * 3 / 4) + height / 4, i * (height * 3 / 4) + (height * 3 / 4), i * (height * 3 / 4) + height, i * (height * 3 / 4) + (height * 3 / 4), i * (height * 3 / 4) + height / 4}, 6);
            }
        }

    }
    
    private enum Type {
        RESOURCE, COLOR, OUTPUT;  
    }
    
    
    private class ToggleButton extends Circle{
        Type t;
        Image image;
                
        
        ToggleButton(Type t){
            super(25);
            
            this.t = t;
            
            switch(t){
                case COLOR:
                    image = ImageBuffer.getImage(MiscAsset.HIGHLIGHT_TOGGLE);
                    break;
                case OUTPUT:
                    image = ImageBuffer.getImage(MiscAsset.YIELDS_TOGGLE);
                    break;
                case RESOURCE:
                    image = ImageBuffer.getImage(MiscAsset.RESOURCE_TOGGLE);
                    break;
            }
            
            this.setFill(new ImagePattern(image));
            
            setOnMouseClicked((MouseEvent event) -> {
                clickEventHandling(event);
            });
        }

        private void clickEventHandling(MouseEvent event) {
            switch (t) {
                case COLOR:
                    DisplayTile.setDisplayColor(!DisplayTile.isDisplayColor());
                    break;
                case OUTPUT:
                    DisplayTile.setDisplayOutput(!DisplayTile.isDisplayOutput());
                    break;
                case RESOURCE:
                    DisplayTile.setDisplayResourse(!DisplayTile.isDisplayResourse());
                    break;
                default:
                    break;
            }
            zoomMap.repaint();
            
            
        }
        
        
    
    
    
    }

}
