package civilizationclone.GUI;

import civilizationclone.Player;
import civilizationclone.TechType;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class SciencePane extends Pane {

    private int resX, resY;
    private Arc progress;
    private Circle progressBackground;
    private Circle techImage;
    private Rectangle textBackground;
    private Text infoText;

    private GamePane gamePaneRef;
    private Player player;

    public SciencePane(Player player, int resX, int resY, GamePane gamePaneRef) {
        this.player = player;
        this.resX = resX;
        this.resY = resY;
        this.gamePaneRef = gamePaneRef;

        setTranslateY(43);

        progressBackground = new Circle();
        progressBackground.setRadius(70);
        progressBackground.setFill(Color.BLACK);
        progressBackground.setStrokeWidth(5);
        progressBackground.setStroke(Color.BEIGE);
        progressBackground.setTranslateX(70);
        progressBackground.setTranslateY(70);

        techImage = new Circle();
        techImage.setRadius(40);
        techImage.setTranslateX(70);
        techImage.setTranslateY(70);
        techImage.setStrokeWidth(4);
        techImage.setStroke(Color.BEIGE);

        progress = new Arc();
        progress.setType(ArcType.ROUND);
        progress.setTranslateX(70);
        progress.setTranslateY(70);
        progress.setRadiusX(68);
        progress.setRadiusY(68);
        progress.setStartAngle(90);
        progress.setFill(Color.CADETBLUE);

        textBackground = new Rectangle(400, 35);
        textBackground.setTranslateX(110);
        textBackground.setTranslateY(10);
        textBackground.setFill(Color.DARKSLATEBLUE);
        textBackground.setStroke(Color.BEIGE);
        textBackground.setStrokeLineJoin(StrokeLineJoin.ROUND);
        textBackground.setStrokeLineCap(StrokeLineCap.ROUND);
        textBackground.setStrokeWidth(5);

        infoText = new Text();
        infoText.setFont(Font.font("Times New Roman", 18));
        infoText.setFill(Color.BEIGE);
        infoText.setTranslateY(34);
        infoText.setTranslateX(145);

        updateInfo();
        getChildren().addAll(textBackground, progressBackground, progress, techImage, infoText);
    }

    public void setCurrentPlayer(Player player) {
        this.player = player;
        updateInfo();
    }

    public void updateInfo() {
        techImage.setFill(new ImagePattern(ImageBuffer.getImage(player.getResearch())));
        progress.setLength(-360 * ((player.getTechProgress() * 1.0) / player.getResearch().getTechCost()));

        if (player.getResearch() != TechType.NONE) {
            String turnInfo = "";
            if (player.getTechIncome() > 0) {
                turnInfo = "(" + Integer.toString((player.getResearch().getTechCost() - player.getTechProgress()) / player.getTechIncome()) + " turns left)";
            } else {
                turnInfo = "(settle a city to start research)";
            }
            infoText.setText(player.getResearch().name() + " " + turnInfo);
        } else{
            infoText.setText("Select new research!");
        }
    }

}
