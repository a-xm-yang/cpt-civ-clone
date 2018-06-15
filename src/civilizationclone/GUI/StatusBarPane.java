package civilizationclone.GUI;

import static civilizationclone.GUI.MiscAsset.GOLD;
import static civilizationclone.GUI.MiscAsset.HAPPY;
import static civilizationclone.GUI.MiscAsset.SCIENCE;
import civilizationclone.Player;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class StatusBarPane extends Group {

    private Rectangle rect;
    private Text goldT;
    private Text sciT;
    private Text happyT;
    private Text turnT;

    private ImageView goldI;
    private ImageView sciI;
    private ImageView happyI;
    private ArrayList<LeaderHead> playerIcon;

    private int resX;
    private int resY;
    private Player player;
    private static GamePane gamePaneRef;

    static ImagePattern readyPattern;
    static ImagePattern waitingPattern;

    public StatusBarPane(Player player, int resX, int resY, GamePane gamePaneRef) {

        readyPattern = new ImagePattern(ImageBuffer.getImage(MiscAsset.READY_ICON));
        waitingPattern = new ImagePattern(ImageBuffer.getImage(MiscAsset.WAITING_ICON));

        goldT = new Text();
        sciT = new Text();
        happyT = new Text();
        turnT = new Text();

        //Init the images
        goldI = new ImageView(ImageBuffer.getImage(GOLD));
        sciI = new ImageView(ImageBuffer.getImage(SCIENCE));
        happyI = new ImageView(ImageBuffer.getImage(HAPPY));

        this.player = player;
        this.resX = resX;
        this.resY = resY;
        this.gamePaneRef = gamePaneRef;
        this.rect = new Rectangle(0, 0, resX + 50, 40);
        rect.setFill(Color.BLACK);

        //Init the heads in the top right corner to show whos turn it is
        initHeads();

        //Set everything to the correct Text color
        updateTexts();
        goldT.setFill(Color.YELLOW);
        sciT.setFill(Color.AQUA);
        happyT.setFill(Color.LAWNGREEN);
        turnT.setFill(Color.WHITE);

        goldT.setFont(Font.font("Oswald", 20));
        sciT.setFont(Font.font("Oswald", 20));
        happyT.setFont(Font.font("Oswald", 20));
        turnT.setFont(Font.font("Oswald", 20));

        //Set the x position of everything depending on the x position of the field to the left of it and its width
        sciT.setTranslateX(45);
        goldT.setTranslateX(sciT.getTranslateX() + sciT.getLayoutBounds().getWidth() + 75);
        happyT.setTranslateX(goldT.getTranslateX() + goldT.getLayoutBounds().getWidth() + 75);
        turnT.setTranslateX(resX - turnT.getLayoutBounds().getWidth());

        sciI.setTranslateX(5);
        goldI.setTranslateX(sciT.getTranslateX() + sciT.getLayoutBounds().getWidth() + 40);
        happyI.setTranslateX(goldT.getTranslateX() + goldT.getLayoutBounds().getWidth() + 40);
        sciI.setTranslateY(7);
        goldI.setTranslateY(7);
        happyI.setTranslateY(7);

        sciT.setTranslateY(sciT.getLayoutBounds().getHeight());
        goldT.setTranslateY(goldT.getLayoutBounds().getHeight());
        happyT.setTranslateY(happyT.getLayoutBounds().getHeight());
        turnT.setTranslateY(turnT.getLayoutBounds().getHeight());

        //Set the y's to 60
        for (Pane i : playerIcon) {
            i.setTranslateY(60);
        }

        getChildren().addAll(rect, sciT, goldT, happyT, turnT, sciI, goldI, happyI);

        updateCurrentHeads();
    }

    public void initHeads() {

        //Created arralist of the images for the heads
        playerIcon = new ArrayList<LeaderHead>();

        for (int i = 0; i < gamePaneRef.getPlayerList().size(); i++) {
            playerIcon.add(new LeaderHead(ImageBuffer.getImage(gamePaneRef.getPlayerList().get(i).getLeader()), gamePaneRef.getPlayerList().get(i).getName()));
        }

        //Set the positions
        arrangeHeads();

        //Add them to the display
        for (Pane i : playerIcon) {
            getChildren().add(i);
        }
    }

    public void arrangeHeads() {

        //Set/correct the positions of the heads
        for (int i = playerIcon.size() - 1; i >= 0; i--) {
            playerIcon.get(i).setTranslateX(resX - (playerIcon.size() - i) * 70);
        }
    }

    public void removeHead(String p) {
        // Remove the head from the list, to be done when a player is killed
        for (LeaderHead i : playerIcon) {
            if (i.name.equals(p)) {
                getChildren().remove(i);
                playerIcon.remove(i);
                break;
            }
        }
        arrangeHeads();
    }

    public void setCurrentPlayer(Player p) {
        player = p;
    }

    public void updateTexts() {

        //Updates the texts, to be done whenever there is a new turn
        goldT.setText(Integer.toString(player.getCurrentGold()) + "(" + Integer.toString(player.getGoldIncome()) + ")");
        sciT.setText(Integer.toString(player.getTechIncome()));
        happyT.setText(Integer.toString(player.getHappiness()));
        turnT.setText(player.getName() + " | Turn: " + player.getTurnNumber() + " | " + Integer.toString(Math.abs(getYear())));
        if (getYear() < 0) {
            turnT.setText(turnT.getText() + "BC");
        } else {
            turnT.setText(turnT.getText() + "AD");
        }

        if (player.getHappiness() >= 0) {
            happyT.setFill(Color.LAWNGREEN);
        } else {
            happyT.setFill(Color.RED);
        }

        turnT.setTranslateX(resX - turnT.getLayoutBounds().getWidth());
        updateCurrentHeads();
    }

    public void updateCurrentHeads() {

        if (gamePaneRef instanceof SinglePlayerPane) {
            //Updates the heads in single player fashion
            for (int i = 0; i < playerIcon.size(); i++) {
                if (player.equals(gamePaneRef.getPlayerList().get(i))) {
                    playerIcon.get(i).setTranslateY(90);
                } else {
                    playerIcon.get(i).setTranslateY(50);
                }
            }
        } else if (gamePaneRef instanceof MultiplayerPane) {
            HashMap activeMap = ((MultiplayerPane) gamePaneRef).getActiveMap();

            for (LeaderHead l : playerIcon) {
                if (activeMap.containsKey(l.name)) {
                    l.setActive((boolean) activeMap.get(l.name));
                }
            }
        }

    }

    private int getYear() {

        //Uses the algorithm to determine what year it is 
        //as the game goes on the turns take less years
        int turns = player.getTurnNumber();
        int year = -4040;

        if (turns > 225) {
            year += (1 * (turns - 225));
            turns = 225;
        }
        if (turns > 175) {
            year += (5 * (turns - 175));
            turns = 175;
        }
        if (turns > 150) {
            year += (10 * (turns - 150));
            turns = 150;
        }
        if (turns > 125) {
            year += (20 * (turns - 125));
            turns = 125;
        }
        year += 40 * turns;
        return year;

    }

    private class LeaderHead extends Pane {

        String name;
        ImageView image;
        Circle circle;

        public LeaderHead(Image image, String name) {
            this.image = new ImageView(image);
            this.name = name;
            setTranslateY(90);

            circle = new Circle(48, 48, 12);
            circle.setFill(Color.TRANSPARENT);

            getChildren().add(this.image);
            getChildren().add(circle);
        }

        void setActive(boolean active) {
            if (active) {
                setTranslateY(90);
                circle.setFill(waitingPattern);
            } else {
                setTranslateY(50);
                circle.setFill(readyPattern);
            }
        }

    }
}
