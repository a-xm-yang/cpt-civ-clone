package civilizationclone.GUI;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.scene.canvas.Canvas;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class TitleMenu extends Pane {

    private Rectangle border;
    private int resX, resY;

    //options
    public TitleMenu() throws FileNotFoundException {


        border = new Rectangle(0,0,1200,800);
        border.setFill(new ImagePattern(ImageBuffer.getImage(MiscAsset.TITLE_BACKGROUND),0, 0, 1200, 800, true));

        
    }

    private Pane openTitle() throws FileNotFoundException {

        CivTitle title = new CivTitle("C O L O N I Z A T I O N  I I", 48);
        title.setTranslateX(185);
        title.setTranslateY(230);

        CivTitle subtitle = new CivTitle("A L E X A N D E R   Y A N G ` S", 11);
        subtitle.setTranslateX(421);
        subtitle.setTranslateY(175);

        CivMenuItems word1 = new CivMenuItems("PLAY");
        word1.setTranslateX(435);
        word1.setTranslateY(325);

        CivMenuItems word2 = new CivMenuItems("OPTIONS");
        word2.setTranslateX(435);
        word2.setTranslateY(375);

        Line line = new Line(425, 300, 425, 380);
        line.setStrokeWidth(3);
        line.setStroke(Color.color(1, 1, 1, 0.75));
        line.setEffect(new DropShadow(5, Color.BLACK));

        return null;
    }

    private Pane openPlay() {

        return null;
    }

    private Pane openOption() {

        return null;
    }

    private class PlayerBox extends Pane {

        private Text text;

        public PlayerBox(String name) throws FileNotFoundException {

            ImageView ahmad = new ImageView();
            Image playerImage1 = new Image(new FileInputStream("src/PlayerBoxItems/Ahmad.png"), 50, 50, false, false);
            Image playerImage2 = new Image(new FileInputStream("src/PlayerBoxItems/mao.png"), 50, 50, false, false);
            Image playerImage3 = new Image(new FileInputStream("src/PlayerBoxItems/hitler.png"), 50, 50, false, false);
            Image playerImage4 = new Image(new FileInputStream("src/PlayerBoxItems/kamakazi.png"), 50, 50, false, false);
            Image playerImage5 = new Image(new FileInputStream("src/PlayerBoxItems/stalin.png"), 50, 50, false, false);
            Image setImage = null;

            ImageView sand = new ImageView();
            Image flagImage1 = new Image(new FileInputStream("src/PlayerBoxItems/arabs.png"), 51, 51, false, false);
            Image flagImage2 = new Image(new FileInputStream("src/PlayerBoxItems/china.png"), 51, 51, false, false);
            Image flagImage3 = new Image(new FileInputStream("src/PlayerBoxItems/germany.png"), 51, 51, false, false);
            Image flagImage4 = new Image(new FileInputStream("src/PlayerBoxItems/japan.png"), 51, 51, false, false);
            Image flagImage5 = new Image(new FileInputStream("src/PlayerBoxItems/russia.png"), 51, 51, false, false);
            Image setImage2 = null;

            switch (name) {
                case "AHMAD":
                    setImage = playerImage1;
                    setImage2 = flagImage1;
                    break;

                case "MAO":
                    setImage = playerImage2;
                    setImage2 = flagImage2;
                    break;

                case "STALIN":
                    setImage = playerImage5;
                    setImage2 = flagImage5;
                    break;

                case "HITLER":
                    setImage = playerImage3;
                    setImage2 = flagImage3;
                    break;

                case "HOKAGE":
                    setImage = playerImage4;
                    setImage2 = flagImage4;
                    break;
            }
            ahmad.setImage(setImage);
            ahmad.setTranslateX(-105);
            ahmad.setTranslateY(-35);

            sand.setImage(setImage2);
            sand.setTranslateX(-45);
            sand.setTranslateY(-36);

            Rectangle rect = new Rectangle(-115, -35, 280, 50);
            rect.setFill(Color.STEELBLUE);
            rect.setOpacity(0.75);

            text = new Text(name);
            text.setFont(Font.loadFont(new FileInputStream("src/MenuImages/Penumbra-HalfSerif-Std_35114.ttf"), 25));
            text.setFill(Color.WHITE);
            text.setTranslateX(40);

            getChildren().addAll(rect, text, ahmad, sand);

        }

    }

    private class CivTitle extends Pane {

        private Text text = new Text();

        public CivTitle(String title, int size) throws FileNotFoundException {

            text.setText(title);
            try {
                text.setFont(Font.loadFont(new FileInputStream("src/Assets/Misc/Penumbra-HalfSerif-Std_35114.ttf"), size));
            } catch (FileNotFoundException e) {
                System.out.println("Not found");
                text.setFont(Font.font("Oswald", size));
            }
            text.setFill(Color.WHITE);
            text.setEffect(new DropShadow(30, Color.BLACK));

            getChildren().addAll(text);
        }
    }

    private class CivMenuItems extends Pane {

        private Text text;
        private Effect shadow = new DropShadow(5, Color.BLACK);
        private Effect blur = new BoxBlur(1, 1, 5);

        public CivMenuItems(String words) throws FileNotFoundException {

            Rectangle rect = new Rectangle(-8, -30, 150, 40);
            rect.setStroke(Color.color(1, 1, 1, 0.8));
            rect.setEffect(new GaussianBlur());

            rect.fillProperty().bind(
                    Bindings.when(pressedProperty()).then(Color.color(0, 0, 0, 0.75)).otherwise(Color.color(0, 0, 0, 0.25))
            );

            text = new Text(words);

            text.setFont(Font.loadFont(new FileInputStream("src/Assets/Misc/Penumbra-HalfSerif-Std_35114.ttf"), 25));
            text.setFill(Color.WHITE);

            text.effectProperty().bind(
                    Bindings.when(hoverProperty()).then(shadow).otherwise(blur)
            );

            getChildren().addAll(rect, text);

        }

    }

}
